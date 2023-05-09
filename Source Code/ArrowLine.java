import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowLine {

    private final Point2D startPoint;
    private final Point2D endPoint;
    private Line line;
    private Line line2;
    private Line line3;
    private Polygon triangle;
    private int noOfLines;
    private boolean triangleRotate;

    ArrowLine(Point2D startPoint, Point2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    public void draw(Pane pane) {
        createLine();
        createTriangle();
        styleLine(line);
        pane.getChildren().addAll(triangle, line);

        //draw line 2 if needed
        if (this.noOfLines >= 2) {
            styleLine(line2);
            pane.getChildren().add(line2);
        }
        //draw line 3 if needed
        if (this.noOfLines == 3) {
            styleLine(line3);
            pane.getChildren().add(line3);
        }
    }

    private void createLine() {
        // example: start is in top or under the end
        if (-startPoint.getX() + endPoint.getX() < 11 && -startPoint.getX() + endPoint.getX() >= 0) {
            // line 3 : first H line to left
            line3 = new Line(startPoint.getX(), startPoint.getY(), startPoint.getX() - 15, startPoint.getY());

            // line 2 : V line to up or down
            line2 = new Line(startPoint.getX() - 15, startPoint.getY(), startPoint.getX() - 15, endPoint.getY());

            //line  : last line to destination
            line = new Line(startPoint.getX() - 15, endPoint.getY(), endPoint.getX(), endPoint.getY());

            this.noOfLines = 3;
            this.triangleRotate = false;
        }
        // example: connection to unit delay
        else if (endPoint.getY() != startPoint.getY() && endPoint.getX() < startPoint.getX()) {
            // line 2 : V line t oup or down
            line2 = new Line(startPoint.getX(), startPoint.getY(), startPoint.getX(), endPoint.getY());
            // line : last H line to destination
            line = new Line(startPoint.getX(), endPoint.getY(), endPoint.getX(), endPoint.getY());

            this.noOfLines = 2;
            this.triangleRotate = true;
        }
        // just in case if a straight k=line is not enough
        else if (endPoint.getY() != startPoint.getY() && endPoint.getX() > startPoint.getX()) {
            // line 3 : first H line to left
            line3 = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX() - 15, startPoint.getY());
            // line 2 : V line to up or down
            line2 = new Line(endPoint.getX() - 15, startPoint.getY(), endPoint.getX() - 15, endPoint.getY());
            //line  : last line to destination
            line = new Line(endPoint.getX() - 15, endPoint.getY(), endPoint.getX(), endPoint.getY());

            this.noOfLines = 3;
            this.triangleRotate = false;
        }
        //direct line into the end point
        else {
            //line  : direct line to destination
            line = new Line(startPoint.getX(), endPoint.getY(), endPoint.getX(), endPoint.getY());

            this.noOfLines = 1;
            this.triangleRotate = false;
        }
    }

    private void styleLine(Line l) {
        l.setStroke(Color.BLUE);
        l.setStrokeWidth(0.9);
        l.setFill(Color.BLACK);
    }

    private void createTriangle() {
        triangle = new Polygon();
        double endX = endPoint.getX();
        double endY = endPoint.getY();
        triangle.setFill(Color.BLACK);
        triangle.setStroke(Color.BLUE);

        if (this.triangleRotate) {
            triangle.getPoints().addAll(endX + 8, endY - 6, endX, endY, endX + 8, endY + 6);
            return;
        }

        triangle.getPoints().addAll(endX - 8, endY + 6, endX, endY, endX - 8, endY - 6);
    }

}

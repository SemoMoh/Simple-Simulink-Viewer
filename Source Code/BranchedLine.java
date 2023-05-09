import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;

public class BranchedLine {
    private final Point2D branchedPoint;
    private final Point2D startPoint;
    private final ArrayList<Point2D> endPoints;

    public BranchedLine(Point2D branchedPoint, Point2D startPoint, ArrayList<Point2D> endPoints) {
        this.branchedPoint = branchedPoint;
        this.startPoint = startPoint;
        this.endPoints = endPoints;
    }

    public void draw(Pane pane) {
        //create a direct lone to branching point
        Line lineToBranchedPoint = new Line(startPoint.getX(), startPoint.getY(), branchedPoint.getX(), branchedPoint.getY());
        lineToBranchedPoint.setStroke(Color.BLUE);
        lineToBranchedPoint.setStrokeWidth(0.9);
        lineToBranchedPoint.setFill(Color.BLACK);

        //create arrow lines that are connected to the end and draw them
        for (Point2D endPoint : endPoints) {
            new ArrowLine(branchedPoint, endPoint).draw(pane);
        }

        //create a bold dot at the branch point
        Circle branchDot = new Circle(branchedPoint.getX(), branchedPoint.getY(), 2);
        branchDot.setStroke(Color.BLACK);
        branchDot.setFill(Color.BLACK);

        // add previous to pane
        pane.getChildren().addAll(lineToBranchedPoint, branchDot);
    }
}

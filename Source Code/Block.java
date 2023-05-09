import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Block {
    // Data Field

    private final double width = 50;
    private String type;
    private String name;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int id;
    private int ports; // number of input ports
    private Rectangle r1;
    private Text text;
    private Rectangle r2;
    private Image symbolImage;

    public Block() {
        ports =1;
        setRectangle();
    }

    public Block(String type, int id, int ports) {
        this.type = type;
        this.id = id;
        this.ports = ports;
        setName();
        setRectangle();
    }

    private void setName() {
        StringBuffer s = new StringBuffer();
        for (int i = 1; i < type.length(); i++) {
            if (type.charAt(i - 1) > 96 && type.charAt(i) < 96) {

                s.append(" " + type.charAt(i));
            } else {
                s.append(type.charAt(i));
            }
        }
        name = s.toString();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getId() {
        return id;
    }

    public int getPorts() {
        return ports;
    }

    public Rectangle getRectangle() {
        return r1;
    }

    public Rectangle getRectangleShadow() {
        return r2;
    }

    public int getTopLeftX() {
        return left;
    }

    public int getTopLeftY() {
        return top;
    }

    public void setType(String type) {
        this.type = type;
        this.setName();
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPorts(int ports) {
        this.ports = ports;
    }

    public Point2D getTopLeft() {
        Point2D p = new Point2D(left, top);
        return p;
    }

    public void setText() {
        text = new Text(this.getRectangle().getX() + 2, this.getRectangle().getY() + width + 15, this.getName());
        text.setFont(new Font(11));
        text.setStyle(
                "-fx-font: bold 13px Arial; -fx-fill:blue;"
        );
       text.setTextAlignment(TextAlignment.CENTER);

    }

    public void setRectangle() {
        r1 = new Rectangle(this.getTopLeftX(), this.getTopLeftY(), width, width); // width = 150
        r1.setFill(Color.TRANSPARENT);
        r1.setStroke(Color.BLACK);
        r1.setArcWidth(15);
        r1.setArcHeight(15);
        r1.setStrokeWidth(2);
        this.setText();
        r2 = new Rectangle(this.getTopLeftX()-2, this.getTopLeftY()-2, width+4, width+4);
        r2.setFill(Color.TRANSPARENT);
        r2.setStroke(Color.SKYBLUE);
        r2.setArcWidth(15);
        r2.setArcHeight(15);
        r2.setStrokeWidth(3);
    }

    public Text getTextt() {
        return this.text;
    }

    //modifications by Eslam
    public double getWidth() {
        return width;
    }

    //function that returns the position of the needed port
    public Point2D portPosition(int portNo) {
        double x = this.getTopLeftX();
        double y = this.getTopLeftY();
        double distPorts = width / (ports + 1);
        y += portNo * distPorts;

        //fbrk
        if (this.name.contains("Unit Delay")) {
            x += width;
        }
        //end fbrk
        return new Point2D(x, y);
    }
    //End modifications

    public ImageView getSymbolImage(){

        this.symbolImage = new Image("images/"+this.getName()+".png");
        ImageView imageView = new ImageView(this.symbolImage);
        if(this.getName().equals("Sum")){
            imageView.setScaleX(0.15);
            imageView.setScaleY(0.15);
            imageView.setLayoutY(this.top-88);
            imageView.setLayoutX(this.left-88);
        }else {
            imageView.setScaleX(0.42);
            imageView.setScaleY(0.41);
            imageView.setLayoutY(this.top-20);
            imageView.setLayoutX(this.left-20);
        }

        return imageView;
    }
}

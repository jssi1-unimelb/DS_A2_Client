package Main.DrawObjects;

public abstract class Shape implements Drawable {
    public String shapeType;

    public Shape(String shapeType) {
        this.shapeType = shapeType;
    }

}

package Main.DrawObjects;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class FreeDraw extends Shape {
    private final LinkedList<Drawable> shapes;

    public FreeDraw() {
        super("free draw");
        shapes = new LinkedList<>();
    }

    public void addShape(Drawable shape) {
        shapes.add(shape);
    }

    @Override
    public void draw(BufferedImage image) {
        for(Drawable shape: shapes) {
            shape.draw(image);
        }
    }
}

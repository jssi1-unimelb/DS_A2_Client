package DrawObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CircleOnCursor extends Shape implements Drawable {
    private final Coord point;
    private final int size;
    private Color colour;

    public CircleOnCursor(Coord point, Color colour, int size) {
        super("circle");
        this.point = point;
        this.colour = colour;
        this.size = size;
    }

    public void draw(BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        g2D.setStroke(new BasicStroke(size));
        g2D.setColor(colour);
        g2D.fillOval(point.x, point.y, size, size);
        g2D.dispose();
    }
}

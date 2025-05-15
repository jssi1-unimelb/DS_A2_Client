package DrawObjects;

import java.awt.*;

public class CircleOnCursor implements Drawable {
    private final Coord point;
    private final int size;
    private Color colour;

    public CircleOnCursor(Coord point, Color colour, int size) {
        this.point = point;
        this.colour = colour;
        this.size = size;
    }

    public void draw(Graphics2D g2D) {
        g2D.setStroke(new BasicStroke(size));
        g2D.fillOval(point.x, point.y, size, size);
    }
}

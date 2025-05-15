package DrawObjects;

import java.awt.*;

public class Line implements Drawable {
    private final Coord start;
    private final Coord end;
    private final Color colour;
    private final int size;

    public Line(Coord start, Coord end, Color colour, int size) {
        this.start = start;
        this.end = end;
        this.colour = colour;
        this.size = size;
    }

    public void draw(Graphics2D g2D) {
        g2D.setColor(colour);
        g2D.setStroke(new BasicStroke(size));
        g2D.drawLine(start.x, start.y, end.x, end.y);
    }
}

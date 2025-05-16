package DrawObjects;

import java.awt.*;

public class Line extends Shape implements Drawable {
    public final Coord start;
    public final Coord end;
    public final Color colour;
    public final int size;

    public Line(Coord start, Coord end, Color colour, int size) {
        super("line");
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

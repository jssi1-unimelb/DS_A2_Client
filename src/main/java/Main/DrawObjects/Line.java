package Main.DrawObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Line extends Shape {
    private final Coord start;
    private final Coord end;
    private final Color colour;
    private final int size;

    public Line(Coord start, Coord end, Color colour, int size) {
        super("line");
        this.start = start;
        this.end = end;
        this.colour = colour;
        this.size = size;
    }

    public void draw(BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(colour);
        g2D.setStroke(new BasicStroke(size));
        g2D.drawLine(start.x, start.y, end.x, end.y);
        g2D.dispose();
    }
}

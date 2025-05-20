package Main.DrawObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Oval extends Shape {
    private final Coord start;
    private final int width;
    private final int height;
    private final Color colour;
    private final int size;

    public Oval(Coord start, int width, int height, Color colour, int size) {
        super("oval");
        this.start = start;
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.size = size;
    }

    public void draw(BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(colour);
        g2D.setStroke(new BasicStroke(size));
        g2D.drawOval(start.x, start.y, width, height);
        g2D.dispose();
    }
}
package DrawObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Text extends Shape implements Drawable {
    private final Coord start;
    private final String text;
    private final Color colour;
    private final int size;

    public Text(Coord start, String text, Color colour, int size) {
        super("text");
        this.start = start;
        this.text = text;
        this.colour = colour;
        this.size = size;
    }

    public void draw(BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(colour);
        g2D.setFont(new Font("Dialog", Font.PLAIN, size));
        g2D.setStroke(new BasicStroke(size));
        g2D.drawString(text, start.x, start.y);
        g2D.dispose();
    }
}

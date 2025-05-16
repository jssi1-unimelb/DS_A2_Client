package DrawObjects;

import java.awt.*;

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

    public void draw(Graphics2D g2D) {
        g2D.setColor(colour);
        g2D.setFont(new Font("Dialog", Font.PLAIN, size));
        g2D.setStroke(new BasicStroke(size));
        g2D.drawString(text, start.x, start.y);
    }
}

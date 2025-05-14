import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColourPanel extends JPanel implements EventPublisher {
    private ColourListener listener;
    public Color colour;

    public ColourPanel(Color colour) {
        this.colour = colour;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                select();
            }
        });
    }

    public void select() {
        setBorder();
        notifyListener();
    }

    public void setBorder() {
        int size = 4;
        Color borderColour = colour.equals(Color.BLACK) ? Color.WHITE : Color.BLACK;
        Border border = BorderFactory.createMatteBorder(size, size, size, size, borderColour);
        this.setBorder(border);
    }

    public void deselect() {
        this.setBorder(null);
    }

    @Override
    public void addListener(Listener listener) {
        this.listener = (ColourListener) listener;
    }

    @Override
    public void notifyListener() {
        listener.onColourEvent(this);
    }
}

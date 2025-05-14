import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WhiteboardGUI extends JFrame {

    JPanel userInterface;
    DrawingArea drawingArea;
    ToolsArea toolsArea;

    public WhiteboardGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userInterface = new JPanel(new GridBagLayout());
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        userInterface.setBorder(topBorder);

        int size = 600;
        int toolsAreaWidth = 200;
        toolsArea = new ToolsArea(toolsAreaWidth, size);
        drawingArea = new DrawingArea(size, toolsArea);

        userInterface.add(drawingArea);
        userInterface.add(toolsArea);

        this.add(userInterface);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("Whiteboard");
        this.setVisible(true);
        this.setResizable(false);
    }
}

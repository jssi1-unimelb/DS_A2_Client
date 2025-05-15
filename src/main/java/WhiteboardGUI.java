import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WhiteboardGUI extends JFrame {

    JPanel userInterface;
    DrawingArea drawingArea;
    ToolsArea toolsArea;
    ConnectionsArea connectionsArea;

    public WhiteboardGUI(WhiteBoardClient client){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userInterface = new JPanel(new GridBagLayout());
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        userInterface.setBorder(topBorder);

        int size = 600;
        int sidePanelWidth = 200;
        toolsArea = new ToolsArea(sidePanelWidth, size);
        drawingArea = new DrawingArea(size, toolsArea, client);
        connectionsArea = new ConnectionsArea(sidePanelWidth, size, client);

        userInterface.add(connectionsArea);
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

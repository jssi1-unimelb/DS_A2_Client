import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WhiteboardGUI extends JFrame {

    JPanel userInterface;
    DrawingArea drawingArea;
    ToolsArea toolsArea;
    ConnectionsArea connectionsArea;
    ConsoleArea consoleArea;

    public WhiteboardGUI(WhiteBoardClient client){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userInterface = new JPanel(new GridBagLayout());
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        userInterface.setBorder(topBorder);

        GridBagConstraints gbc = new GridBagConstraints();

        int height = 600;
        int whiteboardWidth = 700;
        int rightSidePanelWidth = 200;
        int leftSidePanelWidth = 200;
        toolsArea = new ToolsArea(rightSidePanelWidth, height);
        drawingArea = new DrawingArea(whiteboardWidth, height, toolsArea, client);
        connectionsArea = new ConnectionsArea(leftSidePanelWidth, height, client);

        int consoleHeight = 200;
        consoleArea = new ConsoleArea(rightSidePanelWidth + whiteboardWidth + leftSidePanelWidth, consoleHeight);

        gbc.gridy = 0;
        gbc.gridx = 0;
        userInterface.add(connectionsArea, gbc);
        gbc.gridx = 1;
        userInterface.add(drawingArea, gbc);
        gbc.gridx = 2;
        userInterface.add(toolsArea, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        client.addConsoleUpdateListener(consoleArea);
        client.addUserListUpdateListener(connectionsArea);
        client.addWhiteboardUpdateListener(drawingArea);
        userInterface.add(consoleArea, gbc);

        this.add(userInterface);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("Whiteboard");
        this.setVisible(true);
        this.setResizable(false);
    }
}

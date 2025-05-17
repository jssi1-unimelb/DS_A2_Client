import Responses.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WhiteboardGUI extends JFrame implements JoinRequestListener {

    JPanel userInterface;
    DrawingArea drawingArea;
    ToolsArea toolsArea;
    ConnectionsArea connectionsArea;
    ConsoleArea consoleArea;
    JMenuBar toolBar;
    String role;
    WhiteBoardClient client;
    Popup popup;

    public WhiteboardGUI(WhiteBoardClient client, String role){
        this.client = client;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.role = role;

        // Only show the file menu for the manager
        if(role.equals("manager")) {
            toolBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menu.add(new JMenuItem("New"));
            menu.add(new JMenuItem("Open"));
            menu.add(new JMenuItem("Save"));
            menu.add(new JMenuItem("SaveAs"));
            menu.add(new JMenuItem("Close"));
            toolBar.add(menu);
            this.setJMenuBar(toolBar);
        }

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
        connectionsArea = new ConnectionsArea(leftSidePanelWidth, height, client, role);

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

    @Override
    public void onJoinRequest(User user) {
        String title = "New User Join Request";
        popup = new Popup(title, user, "new user", client);
    }
}

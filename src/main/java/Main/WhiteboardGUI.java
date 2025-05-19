package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class WhiteboardGUI extends JFrame implements JoinRequestListener {

    JPanel userInterface;
    DrawingArea drawingArea;
    ToolsArea toolsArea;
    ConnectionsArea connectionsArea;
    ConsoleArea consoleArea;
    ChatArea chatArea;
    JMenuBar toolBar;
    String role;
    WhiteBoardClient client;
    Popup popup;
    File currentFile = null;
    JFileChooser fileChooser = new JFileChooser();

    public WhiteboardGUI(WhiteBoardClient client, String role){
        this.client = client;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.role = role;

        // Only show the file menu for the manager
        if(role.equals("manager")) {
            toolBar = new JMenuBar();
            JMenu menu = new JMenu("File");

            JMenuItem newItem = new JMenuItem("New");
            JMenuItem openItem = new JMenuItem("Open");
            JMenuItem saveItem = new JMenuItem("Save");
            JMenuItem saveAsItem = new JMenuItem("SaveAs");
            JMenuItem closeItem = new JMenuItem("Close");

            // Only allow saving to png
            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png", "png");
            fileChooser.setFileFilter(pngFilter);

            newItem.addActionListener(e -> {
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }

                currentFile = null; // New whiteboard
                BufferedImage newWhiteboard = new BufferedImage(700, 600, BufferedImage.TYPE_INT_ARGB);
                drawingArea.updateWhiteboard(newWhiteboard);
                client.sendNewWhiteboardRequest(newWhiteboard);
            });
            openItem.addActionListener(e -> {
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }

                int option = fileChooser.showOpenDialog(this);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String type = file.getName().split("\\.")[1].toLowerCase();
                    if(type.equals("png")) {
                        try {
                            BufferedImage newWhiteboard = ImageIO.read(file);
                            if(newWhiteboard.getHeight() == 600 && newWhiteboard.getWidth() == 700) {
                                currentFile = file;
                                drawingArea.updateWhiteboard(newWhiteboard);
                                client.sendNewWhiteboardRequest(newWhiteboard);
                            } else {
                                client.updateConsole("Error: Image is the wrong dimensions, only accepting 700x600");
                            }
                        } catch (IOException ex) {
                            client.updateConsole("Error: Cannot open " + file.getName());
                            throw new RuntimeException(ex);
                        }
                    } else { // Invalid file type
                        client.updateConsole("Invalid file type, only PNG allowed");
                    }
                }
            });
            saveItem.addActionListener(e -> {
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }

                // Save item to existing location
                if(currentFile != null) { // From open file
                    try {
                        ImageIO.write(drawingArea.whiteboard, "png", currentFile);
                    } catch (IOException ex) {
                        client.updateConsole("Error: Unable to save file");
                    }
                } else { // New file
                    saveAsWhiteboard();
                }
            });
            saveAsItem.addActionListener(e -> {
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }

                saveAsWhiteboard();
            });
            closeItem.addActionListener(e -> {
                if(!client.liveConnection) {
                    client.updateConsole("Error: You are not connected to a server");
                    return;
                }
                if(!drawingArea.isOpen) {
                    client.updateConsole("Error: Whiteboard has already been closed");
                }

                // Close image, user shouldn't be able to update the whiteboard
                currentFile = null;
                drawingArea.closeWhiteboard();
                client.sendWhiteboardCloseRequest();
            });

            menu.add(newItem);
            menu.add(openItem);
            menu.add(saveItem);
            menu.add(saveAsItem);
            menu.add(closeItem);
            toolBar.add(menu);
            this.setJMenuBar(toolBar);
        }

        userInterface = new JPanel(new GridBagLayout());
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        userInterface.setBorder(topBorder);

        GridBagConstraints gbc = new GridBagConstraints();

        int height = 600;
        int whiteboardWidth = 700;
        int panelWidth = 200;
        toolsArea = new ToolsArea(panelWidth, height);
        drawingArea = new DrawingArea(whiteboardWidth, height, toolsArea, client);
        connectionsArea = new ConnectionsArea(panelWidth, height, client, role);

        int consoleHeight = 200;
        int consoleWidth = panelWidth*2 + whiteboardWidth;
        consoleArea = new ConsoleArea(consoleWidth, consoleHeight);
        int chatWidth = 300;
        int chatHeight = height + consoleHeight;
        chatArea = new ChatArea(chatWidth, chatHeight, client);

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
        userInterface.add(consoleArea, gbc);

        gbc.gridy = 0;
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 2;
        userInterface.add(chatArea, gbc);

        client.addConsoleUpdateListener(consoleArea);
        client.addUserListUpdateListener(connectionsArea);
        client.addWhiteboardUpdateListener(drawingArea);
        client.addChatListener(chatArea);

        this.add(userInterface);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setTitle("Whiteboard");
        this.setVisible(true);
        this.setResizable(false);
    }

    private void saveAsWhiteboard() {
        // Save item to new location
        int option = fileChooser.showSaveDialog(this);
        if(option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }

            // File already exists, warn the user
            if(file.exists()) {
                int res = JOptionPane.showConfirmDialog(null,
                        "File already exists. Do you want to overwrite?", "File Exists",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(res != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Save
            try {
                ImageIO.write(drawingArea.whiteboard, "png", file);
                currentFile = file;
            } catch (IOException ex) {
                client.updateConsole("Error: Unable to save file");
            }
        }
    }

    @Override
    public void onJoinRequest(User user) {
        String title = "New User Join Request";
        popup = new Popup(title, user, "new user", client);
    }
}

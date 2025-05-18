package Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class ConnectionsArea extends JPanel implements UsersListUpdateListener {
    private JPanel usersPanel;
    private String role;
    private Popup popup;
    private WhiteBoardClient client;

    public ConnectionsArea(int width, int height, WhiteBoardClient client, String role) {
        this.client = client;
        this.role = role;
        this.setPreferredSize(new Dimension(width, height));
        Border rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK);
        this.setBorder(rightBorder);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Align items at top
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 5);
        gbc.weightx = 1;

        JButton connectBtn = new JButton();
        JButton disconnectBtn = new JButton();
        JLabel usersPanelHeader = new JLabel();
        usersPanel = new JPanel(new GridBagLayout());

        gbc.gridy = 1;
        connectBtn.setText("Connect");
        connectBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        connectBtn.setFocusPainted(false);
        connectBtn.addActionListener(e -> {
            client.connectToServer();
        });
        this.add(connectBtn, gbc);

        gbc.gridy = 2;
        disconnectBtn.setText("Disconnect");
        disconnectBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        disconnectBtn.setFocusPainted(false);
        disconnectBtn.addActionListener(e -> {
            client.disconnect();
        });
        this.add(disconnectBtn, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 5, 0, 5);
        usersPanelHeader.setText("Users Online");
        usersPanelHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        this.add(usersPanelHeader, gbc);

        // User List
        gbc.gridy = 4;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 20, 5);
        Border border = BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK);
        usersPanel.setBorder(border);
        usersPanel.setBackground(Color.WHITE);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));

        JScrollPane usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        usersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(usersScrollPane, gbc);
    }

    @Override
    public void updateUsersList(ArrayList<User> users) {
        usersPanel.removeAll(); // Clear users
        for(User user: users) {
            String name = user.username;
            String truncated = name.length() >= 24 ? name.substring(0,24) + "..." : name;
            if(role.equals("manager")) { // Buttons so manager can kick
                JButton userButton = new JButton();
                userButton.setFont(new Font("SansSerif", Font.BOLD, 12));
                userButton.setText(truncated);
                userButton.addActionListener(e -> {
                    String title = "Kick User";
                    popup = new Popup(title, user, "kick", client);
                });
                usersPanel.add(userButton);
            } else { // Users only see names
                JLabel userLabel = new JLabel(truncated);
                userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                usersPanel.add(userLabel);
                usersPanel.add(Box.createVerticalStrut(5));
            }
        }
        revalidate();
        repaint();
    }
}

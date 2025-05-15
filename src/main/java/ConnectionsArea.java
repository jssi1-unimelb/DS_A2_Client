import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConnectionsArea extends JPanel {
    private WhiteBoardClient client;

    public ConnectionsArea(int width, int height, WhiteBoardClient client) {
        this.client = client;
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
        JPanel usersPanel = new JPanel(new GridBagLayout());

        gbc.gridy = 1;
        connectBtn.setText("Connect");
        connectBtn.setFocusPainted(false);
        connectBtn.addActionListener(e -> {
            client.sendConnectionRequest();
        });
        this.add(connectBtn, gbc);

        gbc.gridy = 2;
        disconnectBtn.setText("Disconnect");
        disconnectBtn.setFocusPainted(false);
        disconnectBtn.addActionListener(e -> {
            client.sendDisconnectRequest();
        });
        this.add(disconnectBtn, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 5, 10, 5);
        Border border = BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK);
        usersPanel.setBorder(border);
        usersPanel.setBackground(Color.WHITE);
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));

        // Fake names
        String[] names = {"Jonas", "Jim Job", "Billy Boi", "iPhone 16 Pro Max"};
        for(String name: names) {

            // THIS IS WHERE WE MAKE CHANGES FOR THE MANAGER

            JLabel user = new JLabel(name);
            usersPanel.add(user);
        }

        this.add(usersPanel, gbc);
    }
}

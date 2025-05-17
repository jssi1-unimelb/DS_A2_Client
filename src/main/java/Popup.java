import Responses.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Popup extends JDialog {
    private WhiteBoardClient client;

    public Popup(String title, User user, String type, WhiteBoardClient client) {
        this.client = client;
        this.setModal(true);
        this.setTitle(title);

        JPanel body = new JPanel();
        int size = 20;
        body.setBorder(new EmptyBorder(size, size, size, size));
        body.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Body text
        JLabel bodyText = new JLabel();
        if(type.equals("new user")) {
            bodyText.setText(user.username + " wants to join your whiteboard");
        } else {
            bodyText.setText("Kick user: " + user.username + "?");
        }
        bodyText.setHorizontalAlignment(SwingConstants.CENTER);
        bodyText.setVerticalAlignment(SwingConstants.CENTER);
        bodyText.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,size,0);
        body.add(bodyText, gbc);

        // Option buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        JButton yesBtn = new JButton();
        if(type.equals("new user")) { // New User wants to join
            yesBtn.setText("Allow");
            yesBtn.addActionListener(e -> {
                client.sendUserStatusUpdate(true, user);
                this.dispose();
            });
        } else { // Kick a user
            yesBtn.setText("Yes");
            yesBtn.addActionListener(e -> {
                client.sendKickRequest(user);
                this.dispose();
            });
        }

        yesBtn.setFocusPainted(false);

        JButton noBtn = new JButton();
        if(type.equals("new user")) { // New User wants to join
            noBtn.setText("Deny");
            noBtn.addActionListener(e -> {
                client.sendUserStatusUpdate(false, user);
                this.dispose();
            });
        } else { // Kick a user
            noBtn.setText("No");
            noBtn.addActionListener(e -> {
                // Do nothing
                this.dispose();
            });
        }
        noBtn.setFocusPainted(false);


        buttonsPanel.add(yesBtn);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(noBtn);
        gbc.gridy = 1;
        body.add(buttonsPanel, gbc);
        this.add(body);
        this.setAlwaysOnTop(true);
        this.toFront();
        this.requestFocus();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

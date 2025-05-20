// Jiachen Si 1085839
package Main.GUI;

import Main.ChatItem;
import Main.WhiteboardClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatArea extends JPanel implements ChatListener, KeyListener, ActionListener {
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final JButton submitButton;
    private final WhiteboardClient client;

    public ChatArea(int width, int height, WhiteboardClient client) {
        this.client = client;
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Border leftBorder = BorderFactory.createMatteBorder(0,1,0,0, Color.BLACK);
        this.setBorder(leftBorder);

        JLabel title = new JLabel();
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setText("Chat");
        title.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 10);
        this.add(title, gbc);

        // Chat Output
        outputArea = new JTextArea(20, 50);
        outputArea.setLineWrap(true);
        outputArea.setHighlighter(null);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(Color.WHITE);
        outputArea.setFocusable(false);
        outputArea.setEditable(false);

        // Display the output area in the scroll pane
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        Border fullBorder = BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK);
        scrollPane.setBorder(fullBorder);
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5,10,10,10);
        this.add(scrollPane, gbc);

        // Input area
        JPanel inputArea = new JPanel();
        inputArea.setLayout(new GridBagLayout());
        GridBagConstraints inputGBC = new GridBagConstraints();

        // Input text
        inputField = new JTextField();
        Border outline = BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK);
        Border padding = new EmptyBorder(0, 5, 0, 0);
        Border border = BorderFactory.createCompoundBorder(outline, padding);
        inputField.setBorder(border);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        inputField.addKeyListener(this);
        inputGBC.gridx = 0;
        inputGBC.gridy = 0;
        inputGBC.weighty = 0;
        inputGBC.weightx = 1;
        inputGBC.fill = GridBagConstraints.BOTH;
        inputArea.add(inputField, inputGBC);

        // Submit Btn
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        inputGBC.gridx = 1;
        inputGBC.weightx = 0;
        inputGBC.insets = new Insets(10, 5, 10, 10);
        inputArea.add(submitButton, inputGBC);
        
        // Add input area
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 5);
        this.add(inputArea, gbc);
    }

    @Override
    public void updateChat(ChatItem item) {
        String msg = item.username + ": " + item.msg;
        outputArea.append(msg + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10) { // Enter key pressed
            sendRequest();
        }
    }

    private void sendRequest() {
        String msg = inputField.getText().trim();
        if(!msg.isEmpty()) { // Don't process request if it is empty
            client.sendChatUpdateRequest(msg);
        }
        inputField.setText(""); // Clear input field
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton) {
            sendRequest();
        }
    }
}

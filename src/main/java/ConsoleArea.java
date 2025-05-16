import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConsoleArea extends JPanel implements ConsoleUpdateListener {
    private JTextArea outputArea;

    public ConsoleArea(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Border topBorder = BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK);
        this.setBorder(topBorder);

        JLabel title = new JLabel();
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setText("Console");
        title.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 10);
        this.add(title, gbc);

        // Console Output
        outputArea = new JTextArea(10, 40);
        outputArea.setBackground(Color.WHITE);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setFocusable(false);
        outputArea.setHighlighter(null);

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
    }

    @Override
    public void updateConsole(String msg) {
        outputArea.append(msg + "\n");
        // Auto scroll to bottom
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}

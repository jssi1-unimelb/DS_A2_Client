package Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ToolsArea extends JPanel implements ColourListener, EventPublisher {
    public Integer size;
    public Color colour;
    public String tool;
    private ToolListener listener;
    private final ArrayList<ColourPanel> colourPanels = new ArrayList<>();

    public ToolsArea(int width, int height) {
        // Default settings
        this.colour = Color.BLACK;
        this.tool = "line";

        this.setPreferredSize(new Dimension(width, height));
        Border leftBorder = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK);
        this.setBorder(leftBorder);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH; // Align items at top
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 5);
        gbc.weightx = 1;

        JButton lineBtn = new JButton();
        JButton triangleBtn = new JButton();
        JButton ovalBtn = new JButton();
        JButton rectBtn = new JButton();
        JButton drawBtn = new JButton();
        JButton eraseBtn = new JButton();
        JButton textBtn = new JButton();

        Font font = new Font("Arial", Font.PLAIN, 16);

        // Set font
        lineBtn.setFont(font);
        triangleBtn.setFont(font);
        ovalBtn.setFont(font);
        rectBtn.setFont(font);
        drawBtn.setFont(font);
        eraseBtn.setFont(font);
        textBtn.setFont(font);

        // Remove outline on text when tabbed over
        lineBtn.setFocusPainted(false);
        triangleBtn.setFocusPainted(false);
        ovalBtn.setFocusPainted(false);
        rectBtn.setFocusPainted(false);
        drawBtn.setFocusPainted(false);
        eraseBtn.setFocusPainted(false);
        textBtn.setFocusPainted(false);

        // Line btn
        gbc.gridy = 0;
        lineBtn.setText("Line");
        lineBtn.addActionListener(e -> {
            tool = "line";
            notifyListener();
        });
        this.add(lineBtn, gbc);

        // Triangle btn
        gbc.gridy = 1;
        triangleBtn.setText("Triangle");
        triangleBtn.addActionListener(e -> {
            tool = "triangle";
            notifyListener();
        });
        this.add(triangleBtn, gbc);

        // Oval btn
        gbc.gridy = 2;
        ovalBtn.setText("Oval");
        ovalBtn.addActionListener(e -> {
            tool = "oval";
            notifyListener();
        });
        this.add(ovalBtn, gbc);

        // Rectangle btn
        gbc.gridy = 3;
        rectBtn.setText("Rectangle");
        rectBtn.addActionListener(e -> {
            tool = "rectangle";
            notifyListener();
        });
        this.add(rectBtn, gbc);

        // Draw
        gbc.gridy = 4;
        drawBtn.setText("Draw");
        drawBtn.addActionListener(e -> {
            tool = "draw";
            notifyListener();
        });
        this.add(drawBtn, gbc);

        // Erase
        gbc.gridy = 5;
        eraseBtn.setText("Erase");
        eraseBtn.addActionListener(e -> {
            tool = "erase";
            notifyListener();
        });
        this.add(eraseBtn, gbc);

        // Text
        gbc.gridy = 6;
        textBtn.setText("Text");
        textBtn.addActionListener(e -> {
            tool = "text";
            notifyListener();
        });
        this.add(textBtn, gbc);

        // Size Selector
        JComboBox<Integer> sizeSelector = new JComboBox<>(IntStream.rangeClosed(2, 40).boxed().toArray(Integer[]::new));
        gbc.gridy = 7;
        gbc.weighty = 0;
        this.size = 2;
        sizeSelector.setSelectedItem(0);
        sizeSelector.addActionListener(e -> {
            size = (Integer)sizeSelector.getSelectedItem();
        });
        this.add(sizeSelector, gbc);

        // Colour Selector
        JPanel colourSelector = new JPanel(new GridLayout(4, 4, 0, 0));
        int size = 185;
        colourSelector.setPreferredSize(new Dimension(size, size));

        Color[] colours = {
                new Color(0, 255, 255),
                new Color(0, 0, 0),
                new Color(0, 0, 255),
                new Color(255, 0, 255),
                new Color(128, 128, 128),
                new Color(0, 128, 0),
                new Color(0, 255, 0),
                new Color(128, 0, 0),
                new Color(0, 0, 128),
                new Color(128, 128, 0),
                new Color(128, 0, 128),
                new Color(255, 0, 0),
                new Color(192, 192, 192),
                new Color(0, 128, 128),
                new Color(255, 255, 255),
                new Color(255, 255, 0)
        };
        for(Color colour: colours) {
            ColourPanel colourPanel = new ColourPanel(colour);
            colourPanel.setBackground(colour);
            colourPanel.addListener(this);
            colourPanels.add(colourPanel);
            colourSelector.add(colourPanel);
        }

        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridy = 8;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(colourSelector, gbc);

        // Filler
        JPanel filler = new JPanel();
        gbc.gridy = 9;
        gbc.weighty = 1;
        this.add(filler, gbc);
    }

    @Override
    public void onColourEvent(ColourPanel colourPanel) {
        this.colour = colourPanel.colour;
        colourPanels.forEach(ColourPanel::deselect);
        colourPanel.setBorder();
    }

    @Override
    public void addListener(Listener listener) {
        this.listener = (ToolListener) listener;
    }

    @Override
    public void notifyListener() {
        listener.onToolChange();
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColourSelector extends JPanel implements ColourListener {
    public Color selectedColour;
    private ArrayList<ColourPanel> colourPanels = new ArrayList<ColourPanel>();

    public ColourSelector() {
        this.setLayout(new GridLayout(4, 4, 0, 0));
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
            this.add(colourPanel);
        }
    }

    @Override
    public void onColourEvent(ColourPanel colourPanel) {
        this.selectedColour = colourPanel.colour;
        colourPanels.forEach(ColourPanel::deselect);
        colourPanel.setBorder();
    }
}
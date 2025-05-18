package Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ToolBar extends JMenuBar {
    private JMenu menu;

    public ToolBar() {
        Border border = BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK);
        this.setBorder(border);

        menu = new JMenu("File");
        menu.add(new JMenuItem("New"));
        menu.add(new JMenuItem("Open"));
        menu.add(new JMenuItem("Save"));
        menu.add(new JMenuItem("SaveAs"));
        menu.add(new JMenuItem("Close"));

        this.add(menu);
    }
}

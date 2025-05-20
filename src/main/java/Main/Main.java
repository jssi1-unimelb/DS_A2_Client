package Main;

import Main.GUI.WhiteboardGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String host = args[0];
                int port = Integer.parseInt(args[1]);
                String username = args[2];
                String role = args[3];

                WhiteboardClient client = new WhiteboardClient(host, port, username, role);
                WhiteboardGUI gui = new WhiteboardGUI(client, role);
                client.addJoinListener(gui);
            }
        });

    }
}
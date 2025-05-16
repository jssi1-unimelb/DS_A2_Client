import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String host = args[0];
                Integer port = Integer.parseInt(args[1]);
                String username = args[2];

                WhiteBoardClient client = new WhiteBoardClient(host, port, username);
                WhiteboardGUI gui = new WhiteboardGUI(client);
            }
        });

    }
}
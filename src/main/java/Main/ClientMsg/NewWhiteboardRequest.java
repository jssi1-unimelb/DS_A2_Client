package Main.ClientMsg;

import java.awt.image.BufferedImage;

public class NewWhiteboardRequest extends ClientMsg {
    private BufferedImage whiteboard;

    public NewWhiteboardRequest(BufferedImage whiteboard, String role) {
        super("new whiteboard", role);
        this.whiteboard = whiteboard;
    }
}

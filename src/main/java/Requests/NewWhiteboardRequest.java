package Requests;

import java.awt.image.BufferedImage;

public class NewWhiteboardRequest extends Request{
    private BufferedImage whiteboard;

    public NewWhiteboardRequest(BufferedImage whiteboard, String role) {
        super("new whiteboard", role);
        this.whiteboard = whiteboard;
    }
}

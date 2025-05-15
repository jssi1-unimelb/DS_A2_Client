package Requests;

import java.awt.image.BufferedImage;

public class WhiteboardChangeRequest extends Request {
    BufferedImage whiteboard;

    public WhiteboardChangeRequest(BufferedImage whiteboard) {
        super("whiteboard");
        this.whiteboard = whiteboard;
    }
}

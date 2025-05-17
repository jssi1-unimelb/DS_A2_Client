package Requests;

import DrawObjects.Drawable;

import java.awt.image.BufferedImage;

public class WhiteboardChangeRequest extends Request {
    Drawable obj;

    public WhiteboardChangeRequest(Drawable obj, String role) {
        super("whiteboard", role);
        this.obj = obj;
    }
}

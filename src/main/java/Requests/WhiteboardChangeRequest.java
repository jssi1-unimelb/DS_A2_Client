package Requests;

import DrawObjects.Drawable;

import java.awt.image.BufferedImage;

public class WhiteboardChangeRequest extends Request {
    Drawable obj;

    public WhiteboardChangeRequest(Drawable obj) {
        super("whiteboard");
        this.obj = obj;
    }
}

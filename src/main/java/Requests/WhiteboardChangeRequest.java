package Requests;

import DrawObjects.Drawable;

public class WhiteboardChangeRequest extends Request {
    Drawable obj;

    public WhiteboardChangeRequest(Drawable obj, String role) {
        super("whiteboard update", role);
        this.obj = obj;
    }
}

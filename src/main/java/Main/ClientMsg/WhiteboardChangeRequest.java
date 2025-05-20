package Main.ClientMsg;

import Main.DrawObjects.Drawable;

public class WhiteboardChangeRequest extends ClientMsg {
    Drawable obj;

    public WhiteboardChangeRequest(Drawable obj, String role) {
        super("whiteboard update", role);
        this.obj = obj;
    }
}

package Main.ClientMsg;

public class CloseWhiteboardRequest extends ClientMsg {
    public CloseWhiteboardRequest(String role) {
        super("close whiteboard", role);
    }
}

package Main.Requests;

public class CloseWhiteboardRequest extends Request {
    public CloseWhiteboardRequest(String role) {
        super("close whiteboard", role);
    }
}

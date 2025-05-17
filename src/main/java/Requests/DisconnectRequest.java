package Requests;

public class DisconnectRequest extends Request {
    public DisconnectRequest(String role) {
        super("disconnect", role);
    }
}

package Requests;

public class ConnectionRequest extends Request {
    public String username;

    public ConnectionRequest(String username, String role) {
        super("connection", role);
        this.username = username;
    }
}

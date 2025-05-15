package Requests;

public class ConnectionRequest extends Request {
    public String username;

    public ConnectionRequest(String username) {
        super("connection");
        this.username = username;
    }
}

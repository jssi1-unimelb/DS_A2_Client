package Main.ClientMsg;

public class ConnectionRequest extends ClientMsg {
    public String username;

    public ConnectionRequest(String username, String role) {
        super("connection", role);
        this.username = username;
    }
}

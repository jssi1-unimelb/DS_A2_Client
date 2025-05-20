package Main.ClientMsg;

public class DisconnectRequest extends ClientMsg {
    public DisconnectRequest(String role) {
        super("disconnect", role);
    }
}

package Main.ClientMsg;

public abstract class ClientMsg {
    private final String role;
    private final String type;

    public ClientMsg(String type, String role) {
        this.type = type;
        this.role = role;
    }
}

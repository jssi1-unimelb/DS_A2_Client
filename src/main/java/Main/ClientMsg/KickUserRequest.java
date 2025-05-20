package Main.ClientMsg;

import Main.User;

public class KickUserRequest extends ClientMsg {
    User user;

    public KickUserRequest(User user, String role) {
        super("kick", role);
        this.user = user;
    }
}

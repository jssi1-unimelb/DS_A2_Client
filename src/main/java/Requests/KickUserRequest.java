package Requests;

import Responses.User;

public class KickUserRequest extends Request {
    User user;

    public KickUserRequest(User user, String role) {
        super("kick", role);
        this.user = user;
    }
}

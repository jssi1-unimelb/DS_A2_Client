package Requests;

/*
* THIS REQUEST CAN ONLY BE SENT BY THE MANAGER TYPE / DELETE THIS IN THE CLIENT AFTER WORK IS COMPLETE
*  */

import Responses.User;

public class UserStatusUpdate extends Request {
    public boolean approved;
    public User user;


    public UserStatusUpdate(User user, boolean approved, String role) {
        super("user status", role);
        this.approved = approved;
        this.user = user;
    }
}

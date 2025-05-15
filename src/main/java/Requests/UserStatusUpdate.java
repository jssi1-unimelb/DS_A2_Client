package Requests;

/*
* THIS REQUEST CAN ONLY BE SENT BY THE MANAGER TYPE / DELETE THIS IN THE CLIENT AFTER WORK IS COMPLETE
*  */

public class UserStatusUpdate extends Request {
    public boolean approved;

    public UserStatusUpdate(boolean approved) {
        super("user status");
        this.approved = approved;
    }
}

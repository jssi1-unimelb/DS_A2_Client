import Responses.User;

public interface JoinRequestListener extends Listener{
    void onJoinRequest(User user);
}

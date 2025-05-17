import Responses.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface ClientEventPublisher {
    void addConsoleUpdateListener(ConsoleUpdateListener listener);
    void updateConsole(String msg);
    void addWhiteboardUpdateListener(WhiteboardUpdateListener listener);
    void updateWhiteboard(BufferedImage whiteboard);
    void addUserListUpdateListener(UsersListUpdateListener listener);
    void updateUsersList(ArrayList<User> users);
    void addJoinListener(JoinRequestListener listener);
    void notifyJoinListener(User user);
}

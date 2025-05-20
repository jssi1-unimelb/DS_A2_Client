package Main;

import Main.GUI.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface ClientEventPublisher {
    void addConsoleUpdateListener(ConsoleUpdateListener listener);
    void updateConsole(String msg);
    void addWhiteboardUpdateListener(WhiteboardUpdateListener listener);
    void updateWhiteboard(BufferedImage whiteboard);
    void closeWhiteboard();
    void addUserListUpdateListener(UsersListUpdateListener listener);
    void updateUsersList(ArrayList<User> users);
    void addJoinListener(JoinRequestListener listener);
    void notifyJoinListener(User user);
    void addChatListener(ChatListener listener);
    void updateChat(ChatItem item);
}

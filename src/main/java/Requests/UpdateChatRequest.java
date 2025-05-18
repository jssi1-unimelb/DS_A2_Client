package Requests;


import Main.ChatItem;

public class UpdateChatRequest extends Request {
    ChatItem item;

    public UpdateChatRequest(ChatItem item, String role) {
        super("chat", role);
        this.item = item;
    }
}

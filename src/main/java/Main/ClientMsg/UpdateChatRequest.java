package Main.ClientMsg;


import Main.ChatItem;

public class UpdateChatRequest extends ClientMsg {
    ChatItem item;

    public UpdateChatRequest(ChatItem item, String role) {
        super("chat", role);
        this.item = item;
    }
}

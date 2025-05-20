// Jiachen Si 1085839
package Main;

import Main.Gson.GsonUtil;
import Main.ServerMsg.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerReader extends Thread {
    private WhiteboardClient client;

    public ServerReader(WhiteboardClient client) {
        this.client = client;
    }

    public void run() {
        while(true) {
            try {
                    int length = client.dis.readInt(); // Get length of incoming byte array
                    byte[] reqBytes = new byte[length];
                    client.dis.readFully(reqBytes);    // Read the number of bytes specified by "reqBytes"
                    String res = new String(reqBytes, StandardCharsets.UTF_8); // Convert byte[] to String
                    String[] terms = res.split("\"");
                    int index = Arrays.asList(terms).indexOf("type") + 2;
                    String type = terms[index];
                    switch (type) {
                        case "users update" -> { // User list update
                            UsersUpdate update = GsonUtil.gson.fromJson(res, UsersUpdate.class);
                            client.updateUsersList(update.users);
                        }
                        case "whiteboard update" -> { // Whiteboard update
                            WhiteboardUpdate update = GsonUtil.gson.fromJson(res, WhiteboardUpdate.class);
                            if(update.whiteboardUpdateType.equals("close")) {
                                client.closeWhiteboard();
                            } else {
                                client.updateWhiteboard(update.whiteboard);
                                if(update.whiteboardUpdateType.equals("new")) {
                                    client.updateConsole("New whiteboard received");
                                }
                            }
                        }
                        case "disconnect" -> { // Server closed this connection
                            // Need to convert back to disconnect response so can display custom reasons for the disconnect
                            DisconnectUpdate disconnectUpdate = GsonUtil.gson.fromJson(res, DisconnectUpdate.class);
                            client.updateConsole(disconnectUpdate.msg);
                            client.disconnect(); // Disconnected, clean up everything
                        }
                        case "join request" -> { // User wants to connect to the manager's whiteboard
                            UserJoinRequest joinRequest = GsonUtil.gson.fromJson(res, UserJoinRequest.class);
                            client.notifyJoinListener(joinRequest.user);
                        }
                        case "chat" -> { // Update the chat log
                            ChatUpdate chatRequest = GsonUtil.gson.fromJson(res, ChatUpdate.class);
                            client.updateChat(chatRequest.item);
                        }
                    }
            } catch (IOException e) { // Connection has been closed
                if(e.getMessage() == null) {
                    client.updateConsole("Error: Server is unavailable");
                } else {
                    client.updateConsole("Connection closed");
                }
                client.pauseReader();
            }
        }
    }
}

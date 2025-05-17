import Requests.DisconnectRequest;
import Responses.DisconnectResponse;
import Responses.UserJoinRequest;
import Responses.UsersUpdate;
import Responses.WhiteboardUpdate;

import java.io.IOException;
import java.util.Arrays;

public class ServerReader extends Thread {
    private WhiteBoardClient client;

    public ServerReader(WhiteBoardClient client) {
        this.client = client;
    }

    public void run() {
        while(true) {
            try {
                    String res = client.dis.readUTF();
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
                            client.updateWhiteboard(update.whiteboard);
                        }
                        case "disconnect" -> { // Server closed this connection
                            // Need to convert back to disconnect response so can display custom reasons for the disconnect
                            DisconnectResponse disconnectResponse = GsonUtil.gson.fromJson(res, DisconnectResponse.class);
                            client.updateConsole(disconnectResponse.msg);
                            client.disconnect(); // Disconnected, clean up everything
                        }
                        case "join request" -> { // User wants to connect to the manager's whiteboard
                            UserJoinRequest joinRequest = GsonUtil.gson.fromJson(res, UserJoinRequest.class);
                            client.notifyJoinListener(joinRequest.user);
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

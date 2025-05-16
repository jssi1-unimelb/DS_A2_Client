import Requests.UserStatusUpdate;
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
        try {
            while(true) {
                String res = client.dis.readUTF();
                String[] terms = res.split("\"");
                int index = Arrays.asList(terms).indexOf("type") + 2;
                String type = terms[index];
                switch (type) {
                    case "users update" -> {
                        UsersUpdate update = GsonUtil.gson.fromJson(res, UsersUpdate.class);
                        client.updateUsersList(update.users);
                    }
                    case "whiteboard update" -> {
                        WhiteboardUpdate update = GsonUtil.gson.fromJson(res, WhiteboardUpdate.class);
                        client.updateWhiteboard(update.whiteboard);
                    }
                    case "connection status" -> {

                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            throw new RuntimeException(e);
        }

    }
}

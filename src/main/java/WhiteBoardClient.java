import DrawObjects.Drawable;
import Requests.ConnectionRequest;
import Requests.WhiteboardChangeRequest;
import Responses.User;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WhiteBoardClient implements ClientEventPublisher {
    private Socket client;
    protected DataInputStream dis;
    private DataOutputStream dos;
    private final String host;
    private ServerReader serverReader = null;
    private final int port;
    private String username;
//    private ConsoleUpdateListener listener;
    private ConsoleUpdateListener consoleUpdateListener;
    private WhiteboardUpdateListener whiteboardUpdateListener;
    private UsersListUpdateListener usersListUpdateListener;
    public boolean liveConnection = false;
    //    private ServerReader serverReader = null;

    public WhiteBoardClient(String host , int port, String username) {
        this.host = host;
        this.port = port;
        this.username = username;
    }

    public void connectToServer() {
        // Open your connection to a server, at port 1234
        try {
            client = new Socket(host,port);
            liveConnection = true;

            // Open input and output streams
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());

            if(serverReader == null) { // Hasn't been instantiated yet
                serverReader = new ServerReader(this);
                serverReader.start();
            } else {
                notifyAll(); // Wake up existing server reader
            }

            updateConsole("Connection established, waiting for approval from manager");

            // Send request to join the whiteboard
            ConnectionRequest connect = new ConnectionRequest(username);
            String connectReq = GsonUtil.gson.toJson(connect);
            dos.writeUTF(connectReq);

        } catch(UnknownHostException uhe) { // Tried connecting to a server that doesn't exist
            updateConsole("Error: Specified server does not exist");
            liveConnection = false;
        } catch(IOException ioe) {
            updateConsole("Error: Connection denied, please try again later");
            liveConnection = false;
        }
    }

    public void sendDisconnectRequest() {
        if(liveConnection) {
            try {
                client.close();
                updateConsole("Connection Closed");
            } catch (IOException e) {
                updateConsole("Some shit happened");
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                updateConsole("null shit happened");
                throw new RuntimeException(e);
            }
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    // Send a whiteboard update to the server
    public void sendWhiteboardUpdateRequest(Drawable obj) {
        if(liveConnection) {
            try {
                WhiteboardChangeRequest wbUpdate = new WhiteboardChangeRequest(obj);
                String wbUpdateRequest = GsonUtil.gson.toJson(wbUpdate);
                dos.writeUTF(wbUpdateRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    @Override
    public void addConsoleUpdateListener(ConsoleUpdateListener listener) {
        this.consoleUpdateListener = listener;
    }

    @Override
    public void updateConsole(String msg) {
        consoleUpdateListener.updateConsole(msg);
    }

    @Override
    public void addWhiteboardUpdateListener(WhiteboardUpdateListener listener) {
        this.whiteboardUpdateListener = listener;
    }

    @Override
    public void updateWhiteboard(BufferedImage whiteboard) {
        whiteboardUpdateListener.updateWhiteboard(whiteboard);
    }

    @Override
    public void addUserListUpdateListener(UsersListUpdateListener listener) {
        this.usersListUpdateListener = listener;
    }

    @Override
    public void updateUsersList(ArrayList<User> users) {
        usersListUpdateListener.updateUsersList(users);
    }
}

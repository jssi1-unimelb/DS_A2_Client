// Jiachen Si 1085839
package Main;

import Main.DrawObjects.Drawable;
import Main.GUI.*;
import Main.Gson.GsonUtil;
import Main.ClientMsg.*;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WhiteboardClient implements ClientEventPublisher {
    private Socket client;
    public DataInputStream dis;
    private DataOutputStream dos;
    private final String host;
    private ServerReader serverReader = null;
    private final int port;
    private String username;
    public boolean liveConnection = false;
    private final String role;
    private ConsoleUpdateListener consoleUpdateListener;
    private WhiteboardUpdateListener whiteboardUpdateListener;
    private UsersListUpdateListener usersListUpdateListener;
    private JoinRequestListener joinRequestListener;
    private ChatListener chatListener;


    public WhiteboardClient(String host , int port, String username, String role) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.role = role;
    }

    public void cleanUp() {
        try {
            client.close();
            dis.close();
            dos.close();

            // Clear the UI upon disconnect
            updateWhiteboard(new BufferedImage(700, 600, BufferedImage.TYPE_INT_ARGB));
            updateUsersList(new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void pauseReader() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void connectToServer() {
        if(!liveConnection) {
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

                // Send request to join the whiteboard
                ConnectionRequest connect = new ConnectionRequest(username, role);
                String connectReq = GsonUtil.gson.toJson(connect);
                write(connectReq);

                if(role.equals("user")) {
                    updateConsole("Connection established, waiting for approval from manager");
                } else {
                    updateConsole("Connected to server");
                }

            } catch(UnknownHostException uhe) { // Tried connecting to a server that doesn't exist
                updateConsole("Error: Specified server does not exist");
                liveConnection = false;
            } catch(IOException ioe) {
                updateConsole("Error: Connection denied, please try again later");
                liveConnection = false;
            }
        } else {
            updateConsole("Error: Already connected to server");
        }
    }

    public void disconnect() {
        if(liveConnection) {
            liveConnection = false;
            DisconnectRequest disconnect = new DisconnectRequest(role);
            String disconnectReq = GsonUtil.gson.toJson(disconnect);
            write(disconnectReq);
            cleanUp();
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    // Send a whiteboard update to the server
    public void sendWhiteboardUpdateRequest(Drawable obj) {
        if(liveConnection) {
            WhiteboardChangeRequest wbUpdate = new WhiteboardChangeRequest(obj, role);
            String wbUpdateRequest = GsonUtil.gson.toJson(wbUpdate);
            write(wbUpdateRequest);
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    public void sendNewWhiteboardRequest(BufferedImage whiteboard) {
        if(liveConnection) {
            NewWhiteboardRequest newWB = new NewWhiteboardRequest(whiteboard, role);
            String newWBRequest = GsonUtil.gson.toJson(newWB);
            write(newWBRequest);
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    // Only used by manager to accept/reject new users
    public void sendUserStatusUpdate(boolean approved, User user) {
        if(liveConnection && role.equals("manager")) {
            UserStatusUpdate statusUpdateRequest = new UserStatusUpdate(user, approved, role);
            String req = GsonUtil.gson.toJson(statusUpdateRequest);
            write(req);
        }
    }

    // Only used by manager to kick users
    public void sendKickRequest(User user) {
        if(liveConnection && role.equals("manager")) {
            KickUserRequest kickUserRequest = new KickUserRequest(user, role);
            String req = GsonUtil.gson.toJson(kickUserRequest);
            write(req);
        }
    }

    public void sendWhiteboardCloseRequest() {
        if(liveConnection && role.equals("manager")) {
            CloseWhiteboardRequest close = new CloseWhiteboardRequest(role);
            String closeReq = GsonUtil.gson.toJson(close);
            write(closeReq);
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    public void sendChatUpdateRequest(String msg) {
        if(liveConnection) {
            ChatItem chatItem = new ChatItem(username, msg);
            UpdateChatRequest chat = new UpdateChatRequest(chatItem, role);
            String chatReq = GsonUtil.gson.toJson(chat);
            write(chatReq);
        } else {
            updateConsole("Error: You are not connected to a server");
        }
    }

    // Helper function that converts strings to byte arrays to be sent
    // Required for sending strings larger than the byte limit for UTF
    public void write(String req) {
        try {
            byte[] reqBytes = req.getBytes(StandardCharsets.UTF_8);
            dos.writeInt(reqBytes.length); // Send length of byte array
            dos.write(reqBytes);           // Send the request
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void closeWhiteboard() {
        whiteboardUpdateListener.closeWhiteboard();
    }

    @Override
    public void addUserListUpdateListener(UsersListUpdateListener listener) {
        this.usersListUpdateListener = listener;
    }

    @Override
    public void updateUsersList(ArrayList<User> users) {
        usersListUpdateListener.updateUsersList(users);
    }

    @Override
    public void addJoinListener(JoinRequestListener listener) {
        this.joinRequestListener = listener;
    }

    @Override
    public void notifyJoinListener(User user) {
        joinRequestListener.onJoinRequest(user);
    }

    @Override
    public void addChatListener(ChatListener listener) {
        this.chatListener = listener;
    }

    @Override
    public void updateChat(ChatItem item) {
        chatListener.updateChat(item);
    }
}

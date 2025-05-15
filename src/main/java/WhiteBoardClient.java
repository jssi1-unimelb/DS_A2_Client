import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class WhiteBoardClient {
    private Socket client;
    protected DataInputStream dis;
    private DataOutputStream dos;
    private final String host;
    private ServerReader serverReader = null;
    private final int port;
    private String username;
    private boolean liveConnection = false;
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

            // Upon establishing connection, send username to server
            dos.writeUTF(username);

        } catch(UnknownHostException uhe) { // Tried connecting to a server that doesn't exist
            System.out.println("Error: cannot connect, server does not exist");
            liveConnection = false;
        } catch(IOException ioe) {
            System.out.println("Error: connection refused, please try again later");
            liveConnection = false;
        }
    }

    public void sendConnectionRequest() {
        // Bruh
    }

    public void sendDisconnectRequest() {
        // Crocodilo Bombodilo
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendWhiteboardUpdateRequest() {
        // Tung tung tung tung tung sahuuur
    }
}

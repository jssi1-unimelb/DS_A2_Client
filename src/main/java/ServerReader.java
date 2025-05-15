import java.io.IOException;

public class ServerReader extends Thread {
    private WhiteBoardClient client;

    public ServerReader(WhiteBoardClient client) {
        this.client = client;
    }

    public void run() {
        try {
            while(true) {
                String res = client.dis.readUTF();
                System.out.println(res);

                // Do something with the response
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

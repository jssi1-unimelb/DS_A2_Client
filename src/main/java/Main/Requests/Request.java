package Main.Requests;

import java.awt.image.BufferedImage;

public abstract class Request {
    private String role;
    private String type;

    public Request(String type, String role) {
        this.type = type;
        this.role = role;
    }
}

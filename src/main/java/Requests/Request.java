package Requests;

import java.awt.image.BufferedImage;

public abstract class Request {
    private String role;
    private String type;

    public Request(String type) {
        this.type = type;
        this.role = "client";
    }
}

package Main;

import java.awt.image.BufferedImage;

public interface WhiteboardUpdateListener {
    void updateWhiteboard(BufferedImage whiteboard);
    void closeWhiteboard();
}

// Jiachen Si 1085839
package Main.Gson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GsonUtil {
    public static final Gson gson =
            new GsonBuilder()
                    .registerTypeAdapter(BufferedImage.class, new BufferedImageAdaptor())
                    .registerTypeAdapter(Color.class, new ColourAdaptor())
                    .create();;
 }

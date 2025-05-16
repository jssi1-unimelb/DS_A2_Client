// Jiachen Si 1085839
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GsonUtil {
    public static final Gson gson =
            new GsonBuilder()
                    .registerTypeAdapter(BufferedImage.class, new BufferedImageDeserializer())
                    .registerTypeAdapter(Color.class, new ColourAdaptor())
                    .create();;
 }

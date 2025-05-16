import com.google.gson.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.util.Base64;

public class BufferedImageDeserializer implements JsonDeserializer<BufferedImage> {

    @Override
    public BufferedImage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            // Decode base64 string into a byte array
            byte[] bytes = Base64.getDecoder().decode(jsonElement.getAsString());
            // Create an input stream so the bytes can be read like a file
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            // Reads from the stream and decodes it into a BufferedImage
            return ImageIO.read(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package Main;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class BufferedImageAdaptor extends TypeAdapter<BufferedImage> {

    @Override
    public void write(JsonWriter jsonWriter, BufferedImage bufferedImage) throws IOException {
        // Convert BufferedImage into Base64 string
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // Encodes the buffered image into a png
        ImageIO.write(bufferedImage, "png", os);
        // Convert PNG data into Base64 string
        String base64 = Base64.getEncoder().encodeToString(os.toByteArray());
        // Write the string value to the json output
        jsonWriter.value(base64);
    }

    @Override
    public BufferedImage read(JsonReader jsonReader) throws IOException {
        String str = jsonReader.nextString();
        if (str == null || str.isEmpty()) { // No image
            return null;
        }

        // Decodes base64 string into byte array
        byte[] bytes = Base64.getDecoder().decode(str);
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        // Converts stream into a buffered image
        return ImageIO.read(is);
    }
}

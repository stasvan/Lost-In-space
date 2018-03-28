package utils;

        import javax.imageio.ImageIO;
        import java.awt.image.BufferedImage;
        import java.io.File;
        import java.io.IOException;

public class ResourceLoader {

    private static final String PATH = "res/";

    public static BufferedImage loadImage(String fileName) {

        BufferedImage image = null;

        try {

            image = ImageIO.read(new File(PATH + fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public static String resolve(String resourceName) {
        return PATH + resourceName;
    }

}

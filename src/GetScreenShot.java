import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by chenwenping on 17/3/9.
 */
public class GetScreenShot {

    public static boolean getScreenShot(IDevice device, String filePath) {

        RawImage rawScreen = null;

        try {
            rawScreen = device.getScreenshot();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (rawScreen == null) {
            return false;
        }

        Boolean landScape = false;
        int width2 = landScape ? rawScreen.height : rawScreen.width;
        int height2 = landScape ? rawScreen.width : rawScreen.height;

        BufferedImage image = null;

        if (image == null) {
            image = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
        } else {
            if (image.getHeight() != height2 || image.getWidth() != width2) {
                image = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
            }
        }

        int index = 0;
        int indexInc =  rawScreen.bpp >> 3;

        for (int y = 0; y < rawScreen.height; y ++) {
            for (int x = 0; x < rawScreen.width; x ++, index += indexInc) {
                int value = rawScreen.getARGB(index);
                if (landScape) {
                    image.setRGB(y, rawScreen.width - x - 1, value);
                } else {
                    image.setRGB(x, y, value);
                }
            }
        }

        try {
            ImageIO.write((RenderedImage) image, "PNG", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }
}

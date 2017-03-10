import com.android.ddmlib.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by chenwenping on 17/3/9.
 */
public class DdmsMain {

    /**
     * @param args
     */

    public static void main(String[] args) {
      //  IDevice device;
        AndroidDebugBridge.init(false);

        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge(
                "/Users/chenwenping/Library/Android/sdk/platform-tools/adb", false);
        GetDevices.waitForDevice(bridge);
        IDevice devices[] = bridge.getDevices();

        for (IDevice device : devices) {
            System.out.print("device:" + device);
            takeScreenshot(device);
            device.getSerialNumber();
        }


        //device = devices[0];

    }

    /**
     * @param device
     */
    private static void takeScreenshot(IDevice device) {
        try {
            RawImage rawScreen = device.getScreenshot();
            int width = rawScreen.width;
            int height = rawScreen.height;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            int index = 0;

            int indecInc = rawScreen.bpp >> 3;

            for (int y = 0; y < rawScreen.height; y++) {
                for (int x = 0; x < rawScreen.width; x++, index += indecInc) {
                    int value = rawScreen.getARGB(index);
                    image.setRGB(x, y, value);
                }
            }

            ImageIO.write(image, "PNG", new File("/Users/chenwenping/Downloads/test.png"));

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

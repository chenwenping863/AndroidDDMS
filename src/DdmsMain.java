import com.android.ddmlib.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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

        AndroidDebugBridge.addDeviceChangeListener(new AndroidDebugBridge.IDeviceChangeListener() {
            @Override
            public void deviceConnected(IDevice iDevice) {
                System.out.print("deviceConnected " + "\n");
            }

            @Override
            public void deviceDisconnected(IDevice iDevice) {
                System.out.print("deviceDisconnected " + "\n");

            }

            @Override
            public void deviceChanged(IDevice iDevice, int i) {
                System.out.print("deviceChanged " + "\n");

            }
        });
        AndroidDebugBridge.addClientChangeListener(new AndroidDebugBridge.IClientChangeListener() {
            @Override
            public void clientChanged(Client client, int i) {
                System.out.print("clientChanged " + "\n");

            }
        });
        AndroidDebugBridge.addDebugBridgeChangeListener(new AndroidDebugBridge.IDebugBridgeChangeListener() {
            @Override
            public void bridgeChanged(AndroidDebugBridge androidDebugBridge) {
                System.out.print("bridgeChanged " + "\n");
            }
        });
        GetDevices.waitForDevice(bridge);

        IDevice devices[] = bridge.getDevices();

        for (IDevice device : devices) {
            System.out.print("device:" + device);
            takeScreenshot(device);
            device.getSerialNumber();
        }


        //device = devices[0];

       // webDriverTest();
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

    private static void webDriverTest() {

        //System.setProperty()
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.baidu.com/");

        webDriver.manage().window().maximize();

        WebElement webElement = webDriver.findElement(By.name("wd"));

        webElement.sendKeys("Glen");
        WebElement btn = webDriver.findElement(By.id("su"));
        btn.click();

        webDriver.close();
    }
}

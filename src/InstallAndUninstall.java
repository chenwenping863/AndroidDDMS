import com.android.ddmlib.IDevice;
import com.android.ddmlib.InstallException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by chenwenping on 17/3/9.
 */
public class InstallAndUninstall {

    /**
     * @param path
     * @param device
     * @param args
     */
    public static void install(String path, IDevice device, String args) {

        try {
            device.installPackage(path, true, args);
        } catch (InstallException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param packageName
     * @param device
     */
    public static void unInstall(String packageName, IDevice device) {
        try {
            device.uninstallPackage(packageName);
        } catch (InstallException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param device
     * @return
     */
    public static Map<String, String> getProperties(IDevice device) {
        Map<String, String> properties = new HashMap<>();
        properties = device.getProperties();
        return properties;
    }

    /**
     * @param device
     * @return
     */
    public static Future<Integer> getBatteryLevel(IDevice device) {
        Future<Integer> battery = device.getBattery();
        return battery;
    }

    /**
     * @param device
     * @return
     */
    public static boolean isOnline(IDevice device) {
        return device.isOnline();
    }

    /**
     * @param device
     * @return
     */
    public static boolean isOffline(IDevice device) {
        return device.isOffline();
    }












}

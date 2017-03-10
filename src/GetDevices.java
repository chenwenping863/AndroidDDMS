import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * 首先要找到设备对象，所以先要获得到device[]列表，再从列表中取出所需要的设备对象：
 * Created by chenwenping on 17/3/9.
 */
public class GetDevices {

    public static IDevice getOnLineDevice(String id) {
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("adb", true);
        waitForDevice(bridge);
        IDevice[] devices = bridge.getDevices();
        for (IDevice device : devices) {
            if (device.getSerialNumber().equals(id)) {
                return device;
            }
        }
        return null;
    }

    /**
     * @param bridge
     */
    public static void waitForDevice(AndroidDebugBridge bridge) {
        int count = 0;
        while (!bridge.hasInitialDeviceList()) {
            try {
                Thread.sleep(100);
                count++;
            } catch (InterruptedException ignored) {
            }
            if (count > 300) {
                System.err.print("Time out");
                break;
            }
        }
    }
}

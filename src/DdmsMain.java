import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * Created by chenwenping on 17/3/9.
 */
public class DdmsMain {
    public static void main(String[] args) {
        IDevice device;
        AndroidDebugBridge.init(false);
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge(
                "/Users/chenwenping/Library/Android/sdk/platform-tools/adb", false);
        waitForDevice(bridge);
        IDevice devices[] = bridge.getDevices();
        device = devices[0];
        System.out.print("device:" + device);
    }

    private static void waitForDevice(AndroidDebugBridge bridge) {
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

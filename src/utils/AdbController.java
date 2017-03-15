package utils;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.IDevice;

import java.util.logging.Logger;

/**
 * Created by chenwenping on 17/3/10.
 */
public class AdbController {
   // protected static Logger logger = LoggerFactory.getLogger(AdbController.class);
    protected static AndroidDebugBridge adb = null;


    private static boolean isInit = false;

    public AdbController() {
        init();
    }

    public void init()
    {
        if(!isInit) {
            AndroidDebugBridge.init(true);
            DdmPreferences.setTimeOut(20000);
            //  adb = AndroidDebugBridge.createBridge();    //使用该方式，在没有启动adb时，会提示找不到
            adb = AndroidDebugBridge.createBridge(ConfigurationSettings.ADB_PATH, false);
            isInit = true;
        }
        waitDeviceList(adb);
    }

    private void waitDeviceList(AndroidDebugBridge bridge) {
        int count = 0;
        while (bridge.hasInitialDeviceList() == false) {
            try {
                Thread.sleep(100);
                count++;
            } catch (InterruptedException e) {
            }
            if (count > 100) {
                System.out.println("Fail to Init Device list");
                break;
            }
        }
    }

   /* public void terminate() {
        if(deviceTimer!= null){
            deviceTimer.clearService();
        }
        if(deviceListener!=null)
            AndroidDebugBridge.removeDeviceChangeListener(deviceListener);
        AndroidDebugBridge.terminate();
    }*/

    public IDevice[] getDevices() {
        return adb.getDevices();
    }

   /* public IDevice getCurrentDevice() {
        return deviceListener.mCurrentDevice;
    }*/

    public IDevice getDevice(String serialNum) {
        IDevice[] devices = adb.getDevices();
        int nums = devices.length;
        for (int i = 0; i < nums; i++) {
            String curSerialNum = devices[i].getSerialNumber();
            if (serialNum.equals(curSerialNum))
                return devices[i];
        }
        return null;
    }

    /*public String getCurrentActivity(String monitorPackage){
        SFActivityService sf = new SFActivityService(getCurrentDevice(), monitorPackage);
        sf.executeCmd();
        return sf.getCurActivity();
    }*/

}

package utils;

import com.android.ddmlib.*;

import java.io.IOException;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenwenping on 17/3/10.
 */
public class CpuInfoService extends Observable {//implements AdbShellService {

    private static final String LOG_TAG = "CpuInfoService";

    protected IDevice device;

    private CpuInfoReceiver receiver = null;

    protected String monitorPackage;

    public CpuInfoService(IDevice device,String monitorPackage) {
        this.device = device;
        this.monitorPackage = monitorPackage;
        receiver = new CpuInfoReceiver();           }


    public Float getCpuRatio(){
        Float cpuRatio = receiver.getCpuRatio();
        if(cpuRatio == null || cpuRatio <0 )
            cpuRatio = -1f;
        return cpuRatio;
    }

    public void executeCmd() {

        if(StringUtils.isEmpty(monitorPackage))
            return;

        String cmd = "dumpsys cpuinfo " + monitorPackage;
        try {
            device.executeShellCommand(cmd, receiver);
        } catch (TimeoutException e) {
           // logger.error(LOG_TAG,"TimeoutException");
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
           // logger.error(LOG_TAG,"AdbCommandRejectedException");
            e.printStackTrace();
        } catch (ShellCommandUnresponsiveException e) {
          //  logger.error(LOG_TAG,"ShellCommandUnresponsiveException");
            e.printStackTrace();
        } catch (IOException e) {
            //logger.error(LOG_TAG,"IOException");
            e.printStackTrace();
        }
        Float ratio = getCpuRatio();
        if(ratio >=0.0){
            setChanged();
            notifyObservers(ratio);
        }
    }

    public void stop() {
        receiver.setCanceledFlag(true);
    }

    private class CpuInfoReceiver extends MultiLineReceiver {


        private Pattern CPUINFO_MATCHER;

        private Float mCpuRatio = null;

        protected Boolean isCanceled = false;

        public CpuInfoReceiver() {
            super();
            CPUINFO_MATCHER = Pattern.compile(".*"+ monitorPackage);
        }


        public void setCanceledFlag(Boolean isCanceled) {
            this.isCanceled = isCanceled;
        }

        public Float getCpuRatio() {
            return mCpuRatio;
        }

        public boolean isCancelled() {
            return isCanceled;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                Matcher cpuMatch = CPUINFO_MATCHER.matcher(line);
                if (cpuMatch.find()) {
                    try {
                        mCpuRatio = Float.parseFloat(cpuMatch.group().split("%")[0].trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(String.format("Failed to parse %s as an integer",
                                cpuMatch.group(1)));
                    }
                }
            }
        }

    }

    public void clear() {

    }

}

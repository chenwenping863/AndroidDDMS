package utils;

import com.android.ddmlib.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by chenwenping on 17/3/10.
 */
public class MemInfoService extends Observable {//implements AdbShellService {

    protected IDevice device;

    private MemInfoReceiver receiver = new MemInfoReceiver();
    protected String monitorPackage;

    public MemInfoService(IDevice device,String monitorPackage) {
        this.device = device;
        this.monitorPackage = monitorPackage;

        //this.addObserver(DataManager.getInstance().getMemObserver());
    }

    /**
     * 从Receive中拿到内存数据
     * @return
     */
    public Float getMemInfo(){
        Float memInfo = receiver.getMemInfo();
        if(memInfo == null || memInfo <0 )
            memInfo = -1f;
        return memInfo/1000;
    }

    public Float getDalvInfo(){
        Float dalvInfo = receiver.getDalvInfo();
        if(dalvInfo == null || dalvInfo <0 )
            dalvInfo =-1f;
        return dalvInfo/1000;
    }

    public Float getNativeInfo () {
        Float nativeInfo = receiver.getNativeInfo();
        if (nativeInfo == null || nativeInfo < 0)
            nativeInfo = -1f;

        return nativeInfo / 1000;
    }

    public void executeCmd() {

        if(StringUtils.isEmpty(monitorPackage))
            return;
        String cmd = "dumpsys meminfo " + monitorPackage;
        /*try {
            //device.executeShellCommand(cmd, receiver);
        } catch (TimeoutException e) {
           // logger.error(LOG_TAG,"TimeoutException");
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
            //logger.error(LOG_TAG,"AdbCommandRejectedException");
            e.printStackTrace();
        } catch (ShellCommandUnresponsiveException e) {
            //logger.error(LOG_TAG,"ShellCommandUnresponsiveException");
            e.printStackTrace();
        } catch (IOException e) {
            //logger.error(LOG_TAG,"IOException");
            e.printStackTrace();
        }*/
        Float ratio1 = getMemInfo();
        Float ratio2 = getDalvInfo();
        Float ratio3 = getNativeInfo();
        if(ratio1>0 && ratio2>0 && ratio3 >0){
            setChanged();
           // notifyObservers(new DataManager.MEM_DATA(ratio1,ratio2,ratio3));
        }
    }

    private static final  class MemInfoReceiver extends MultiLineReceiver {
        protected Boolean isCanceled = false;
        public MemInfoReceiver() {
            super();
        }

        public void setCanceledFlag(Boolean isCanceled) {
            this.isCanceled = isCanceled;
        }

        public Float getMemInfo() {
            return memInfo;
        }

        public Float getDalvInfo() {
            return dalvInfo;
        }

        public Float getNativeInfo() {
            return nativeInfo;
        }

        private static final String DALVIK_MATCHER = "Dalvik";
        private static final String NATIVE_MATCHER = "Native";
        private static final String TOTAL_MATCHER = "TOTAL";

        protected Float memInfo = null;
        protected Float dalvInfo = null;
        protected Float nativeInfo = null;

        public boolean isCancelled() {
            return isCanceled;
        }
        private List<String> tem = new ArrayList<String>();
        @Override
        public void processNewLines(String[] lines) {
            for(String line:lines) { //将输出的数据缓存起来
                tem.add(line);
                if(line.contains(TOTAL_MATCHER)) {
                    getMemInfo(tem);
                    tem.clear();
                }
            }
        }

        public void getMemInfo(List<String> lines) {
            //这里使用的arrNative[index] ，index会有不同，原因是不同的版本输出的信息，有的叫Dalvik,有得叫Dalvik Heap
            for (String line : lines) {

                if (line.contains(NATIVE_MATCHER)) {
                    String[] arrNative = line.split("\\s+");
                    if (9 == arrNative.length) {
                        nativeInfo = Float.parseFloat(arrNative[7]);
                    }
                    else if ( 8 == arrNative.length) {
                        nativeInfo = Float.parseFloat(arrNative[6]);
                    }
                    else if ( 7 == arrNative.length) {
                        nativeInfo = Float.parseFloat(arrNative[5]);
                    }
                    continue;
                }
                if(line.contains(DALVIK_MATCHER)){
                    String[] arrDalvik = line.split("\\s+");
                    if (9 == arrDalvik.length) {
                        dalvInfo = Float.parseFloat(arrDalvik[7]);
                    } else if (8 == arrDalvik.length) {
                        dalvInfo = Float.parseFloat(arrDalvik[6]);
                    } else if ( 7 == arrDalvik.length) {
                        dalvInfo = Float.parseFloat(arrDalvik[5]);
                    }
                    continue;
                }
                if(line.contains(TOTAL_MATCHER)){
                    String arrTotal[] = line.split("\\s+");
                    if (8 == arrTotal.length) {
                        memInfo = Float.parseFloat(arrTotal[6]);
                    }else if ( 7 == arrTotal.length) {
                        memInfo = Float.parseFloat(arrTotal[5]);
                    }
                    break;
                }
            }
        }
    }

    public void stop() {
        receiver.setCanceledFlag(true);
    }

    public void clear() {
    }

}

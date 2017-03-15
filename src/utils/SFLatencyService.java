package utils;

import com.android.ddmlib.IDevice;

import java.util.Observable;

/**
 * Created by chenwenping on 17/3/10.
 */
public class SFLatencyService extends Observable {//implements AdbShellService{
    private static String FRAME_LATENCY_CMD = "dumpsys SurfaceFlinger --latency";
    protected IDevice device;
    private String monitorPackage;
    private String windowName;

   // protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String LOG_TAG = "[SFLatencyService]-";

    public SFLatencyService(IDevice device,String monitorPackage) {
        this.device = device;
        this.monitorPackage = monitorPackage;
       // clearSFBuffer();
       // receiver = new LatencyReceiver(this);
        //this.addObserver(DataManager.getInstance().getFpsObserver());
    }

   // private LatencyReceiver receiver = null;

    private Float laterFps;

    public Float getLaterFps() {
        return laterFps;
    }

    public void setLaterFps(Float laterFps) {
        this.laterFps = laterFps;
        if(laterFps>0){
            setChanged();
           // notifyObservers(new DataManager.FPS_DATA(windowName,laterFps));
        }
    }

    /*public String getActivity(){
        SFActivityService sf = new SFActivityService(device,monitorPackage);
        sf.executeCmd();
        return sf.getCurActivity();
    }*/
/*

    public void clearSFBuffer(){
        SFClearService sc = new SFClearService(device);
        sc.executeCmd();
    }

    public void executeCmd() {
        if(StringUtils.isEmpty(monitorPackage))
            return;
        windowName = getActivity();
        String command = FRAME_LATENCY_CMD;

        command = String.format("%s %s", FRAME_LATENCY_CMD, windowName);
        try {
            device.executeShellCommand(command,receiver);
        } catch (TimeoutException e) {
            logger.error(LOG_TAG,"TimeoutException");
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
            logger.error(LOG_TAG,"AdbCommandRejectedException");
            e.printStackTrace();
        } catch (ShellCommandUnresponsiveException e) {
            logger.error(LOG_TAG,"ShellCommandUnresponsiveException");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(LOG_TAG,"IOException");
            e.printStackTrace();
        }
    }


    public void stop() {
    }

    private static final  class LatencyReceiver extends MultiLineReceiver {
        SFLatencyService parent;

        public LatencyReceiver(SFLatencyService parent) {
            this.parent = parent;
        }
        protected Float fps = 0f;
        private static int BUFFER_SIZE = 128;
        private static int BUFFER_NUMBER = 3;

        */
/* An array list which includes the raw buffer information from frame latency tool *//*

        private static List<List<String>> mFrameBufferData = new ArrayList<List<String>>(BUFFER_SIZE);

    */
/* Record the refresh period returned from driver *//*

        //private static long mRefreshPeriod = -1;

        // Symbol of unfinished frame time *//*

        public static final String PENDING_FENCE_TIME = new Long(Long.MAX_VALUE).toString();
        private static final Pattern CHECK_MATCHER = Pattern.compile("^[\\d\\s]+$");

        public boolean isCancelled() {
            return false;
        }

        public void clearBuffer(){
            mFrameBufferData.clear();
        }

        @Override
        public void processNewLines(String[] lines) {
            if(lines.length<2)
                return;
            for(String line:lines){
                Matcher matcher = CHECK_MATCHER.matcher(line);
                if(!matcher.matches()) continue;

                String[] bufferValues = line.split("\\s+");

                if(bufferValues.length==1){
                    if(line.trim().isEmpty())
                        continue;

                    if(mFrameBufferData.isEmpty()) {
                        //mRefreshPeriod = Long.parseLong(line.trim());
                        continue;
                    } else {
                        fps = (float)getFrameRate();
                        parent.setLaterFps(fps);
                        //mRefreshPeriod = Long.parseLong(line.trim());
                        clearBuffer();
                        continue;
                    }
                }

                if(bufferValues.length!=BUFFER_NUMBER)
                    return;

                if (bufferValues[0].trim().compareTo("0") == 0) {
                    continue;
                } else if (bufferValues[1].trim().compareTo(PENDING_FENCE_TIME) == 0 ) {
                    System.out.println(LOG_TAG + "the data contains unfinished frame time");
                    continue;
                }
                List<String> delayArray = Arrays.asList(bufferValues);
                mFrameBufferData.add(delayArray);
            }
        }
*/


        /**
         * Calculate frame rate
         * @return
         */
       /* public static double getFrameRate() {
            int mFrameLatencySampleSize = mFrameBufferData.size() - 1;
            long startTime = Long.parseLong(mFrameBufferData.get(0).get(1));
            long endTime =  Long.parseLong(mFrameBufferData.get(mFrameLatencySampleSize).get(1));
            long totalDuration = endTime - startTime;
            return (double)((mFrameLatencySampleSize - 1) * Math.pow(10, 9))/totalDuration;
        }
    }


    public void clear() {
        if(receiver!=null)
            receiver.clearBuffer();
    }*/
}

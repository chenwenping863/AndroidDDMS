package utils;

import com.android.ddmlib.*;

import java.io.IOException;
import java.util.Observable;

/**
 * Created by chenwenping on 17/3/10.
 */
public class TrafficInfoService extends Observable {//implements AdbShellService {

    protected IDevice device;
    protected String uid = null;

   // private TrafficReceiver receiver;
    private String monitorPackage;

    Integer rcv = null;
    Integer snd = null;
    Integer flow = null;

    private static Integer firstRcv = null;
    private static Integer firstSnd = null;

    public Integer getRcv() {
        return rcv;
    }

    public Integer getSnd() {
        return snd;
    }

    public Integer getFlow() {
        return flow;
    }


    public TrafficInfoService(IDevice device, String monitorPackage) {
        this.device = device;
       // receiver = new TrafficReceiver();
        this.monitorPackage = monitorPackage;
        //this.addObserver(DataManager.getInstance().getFlowObserver());

    }

    /*protected String getUid(){
        UidService service = new UidService(device,monitorPackage);
        service.executeCmd();
        return service.getUid();
    }*/

    /*public void executeCmd(){
        if(StringUtils.isEmpty(monitorPackage))
            return;
        if(uid == null)
            uid = getUid();
        String cmd = String.format("cat /proc/uid_stat/%s/tcp_rcv;cat /proc/uid_stat/%s/tcp_snd", uid,uid);
        try {
            device.executeShellCommand(cmd, receiver);
        } catch (TimeoutException e) {
            //logger.error(LOG_TAG,"TimeoutException");
            e.printStackTrace();
        } catch (AdbCommandRejectedException e) {
           // logger.error(LOG_TAG,"AdbCommandRejectedException");
            e.printStackTrace();
        } catch (ShellCommandUnresponsiveException e) {
          //  logger.error(LOG_TAG,"ShellCommandUnresponsiveException");
            e.printStackTrace();
        } catch (IOException e) {
          //  logger.error(LOG_TAG,"IOException");
            e.printStackTrace();
        }
        notifyData();
    }

    public void notifyData(){
        rcv = receiver.getRcv();
        snd = receiver.getSnd();
        if(rcv <0 || snd<0)
            return;

        if(firstRcv == null) firstRcv = rcv;
        if(firstSnd == null) firstSnd = snd;
        rcv = rcv - firstRcv;
        snd = snd - firstSnd;
        flow = rcv + snd;
        setChanged();
        notifyObservers(new DataManager.FLOW_DATA(flow,snd,rcv));
    }

    public void stop() {
        receiver.setCanceledFlag(true);
        firstRcv = null;
        firstSnd = null;
    }

    private static final  class TrafficReceiver extends MultiLineReceiver {
        private Integer mrcv = null;
        private Integer msnd = null;

        protected Boolean isCanceled = false;

        public void setCanceledFlag(Boolean isCanceled) {
            this.isCanceled = isCanceled;
        }

        public Integer getRcv() {
            if(mrcv == null || mrcv <0)
                mrcv = -1;
            return mrcv;
        }

        public Integer getSnd() {
            if(msnd == null || msnd <0)
                msnd = -1;
            return msnd;
        }
        public boolean isCancelled() {
            return isCanceled;
        }

        @Override
        public void processNewLines(String[] lines) {
            //System.out.println(lines[0]);
            if(lines.length<2) return;
            try{
                mrcv = Integer.parseInt(lines[0].trim())/1000;
                msnd = Integer.parseInt(lines[1].trim())/1000;
            } catch (NumberFormatException e) {
               // System.out.println(LOG_TAG + String.format(":Failed to parse %s to traffic",
                //        lines[0]+lines[1]));
            }
        }
    }

    public void clear() {
        firstRcv = null;
        firstSnd = null;
    }*/

}

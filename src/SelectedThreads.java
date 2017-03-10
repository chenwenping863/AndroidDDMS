import com.android.ddmlib.Client;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ThreadInfo;

/**
 * Created by chenwenping on 17/3/10.
 */
public class SelectedThreads {

    private  final IDevice device;

    public SelectedThreads(IDevice device) {
        this.device = device;
    }

    public void getListThreads() {
        Client runningApp = device.getClient("com.android.calemdar");
        ThreadInfo[] threadInfos = runningApp.getClientData().getThreads();

        for (ThreadInfo threadInfo : threadInfos) {
            System.out.print(threadInfo.getThreadName() + " at " + threadInfo.getStatus());
        }
    }
}

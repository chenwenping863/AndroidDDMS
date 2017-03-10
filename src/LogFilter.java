import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatFilter;
import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;

import java.util.List;

/**
 * Created by chenwenping on 17/3/10.
 */
public class LogFilter {

    private final IDevice device;

    public LogFilter(IDevice device) {
        this.device = device;
    }

    public void getLog() {
        final LogCatFilter filter = new LogCatFilter("chenwen", "TAG", "", "", "", Log.LogLevel.WARN);

        final LogCatListener logCatListener = new LogCatListener() {
            @Override
            public void log(List<LogCatMessage> list) {
                for (LogCatMessage logCatMessage : list) {
                    if (filter.matches(logCatMessage)) {
                        System.out.print(logCatMessage);
                    }
                }
            }
        };


    }




}

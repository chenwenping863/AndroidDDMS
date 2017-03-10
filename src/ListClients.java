import com.android.ddmlib.Client;
import com.android.ddmlib.ClientData;
import com.android.ddmlib.IDevice;

/**
 * Created by chenwenping on 17/3/10.
 */
public class ListClients {

    private final IDevice device;

    public ListClients(IDevice device) {
        this.device = device;
    }

    public void getRunClientsList() {
        Client[] clients = device.getClients();

        for (Client client : clients) {
            ClientData clientData = client.getClientData();
            System.out.print(clientData.getClientDescription() + " " + clientData.getPid());
        }
    }

}

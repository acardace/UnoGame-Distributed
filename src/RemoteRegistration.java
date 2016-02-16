import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by michele on 16/02/16.
 */
public interface RemoteRegistration extends Remote {
    public PlayersRegistrationData playerRegistration(String addr) throws RemoteException, NotBoundException;
    public void playerReady(int playerID) throws RemoteException;
}

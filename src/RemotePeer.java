import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePeer extends Remote{
    void getGameToken() throws RemoteException;
    void getFTToken(FaultToleranceToken ftToken) throws RemoteException;
}

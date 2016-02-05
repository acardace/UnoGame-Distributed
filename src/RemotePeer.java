import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePeer extends Remote{
    void getGameToken(GameToken gameToken) throws RemoteException;
    void getFTToken(FaultToleranceToken fttToken) throws RemoteException;
}

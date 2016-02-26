import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RemotePeer extends Remote{
    void getGameToken() throws RemoteException;
    void getFTToken() throws RemoteException;
    int getID() throws RemoteException;
    int isAlive(int ringSize) throws RemoteException;
    void reconfigureRing(ArrayList<Integer> crashedPeers) throws RemoteException;
    boolean hasGToken() throws RemoteException;
    void addPlayers(HashMap<Integer, String> peers) throws RemoteException;
}

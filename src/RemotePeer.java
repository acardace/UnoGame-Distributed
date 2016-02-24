import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemotePeer extends Remote{
    void getGameToken() throws RemoteException;
    void getFTToken() throws RemoteException;
    int getID() throws RemoteException;
    int isAlive(int ringSize) throws RemoteException;
    void reconfigureRing(ArrayList<Integer> crashedPeers) throws RemoteException;
    boolean hasGToken() throws RemoteException;
    void getGlobalState(int sender, int hand_cnt, ArrayList<UnoDeck.UnoCardInDeck> removedCards) throws RemoteException;
    void setGlobalState(int sender, int hand_cnt, ArrayList<UnoDeck.UnoCardInDeck> removedCards) throws RemoteException;
}

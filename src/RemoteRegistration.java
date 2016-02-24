import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by michele on 16/02/16.
 */
public interface RemoteRegistration extends Remote {
    // first function return the new player id
    public int getNewPlayerID() throws RemoteException;

    // function to register new player in the registration server
    public PlayerReady[] playerRegistration(int id, String playerAddr) throws RemoteException, NotBoundException;

    // returns an array with all players id
    public int[] getPlayersID() throws RemoteException;

    // set player status to ready, lock waiting for game start
    public void playerReady(int playerID) throws RemoteException;
}

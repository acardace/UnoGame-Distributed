import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by michele on 16/02/16.
 */
public class GameRegistration implements RemoteRegistration {
    private static final String RMI_REG_OBJ_NAME = "RegistrationService";
    private static final String RMI_OBJ_NAME = "RemotePeer";
    private static final int MAX_PLAYERS = 8;

    private HashMap<Integer, PlayerReady> playersHashMap;
    private int playersReadyCounter;
    private int playersCounter;

    public GameRegistration() {
        playersReadyCounter = 0;
        playersCounter = 0;
        playersHashMap = new HashMap<>();
    }

    private int newPlayerID() {
        if(playersCounter < MAX_PLAYERS)
            playersCounter++;
        else
            playersCounter = -1;

        return playersCounter;
    }

    private PlayersRegistrationData registerPlayer(String addr) throws  RemoteException, NotBoundException {
        // insert player in "play room"
        Registry registry = LocateRegistry.getRegistry(addr);
        RemotePeer remotePeer = (RemotePeer) registry.lookup(RMI_OBJ_NAME);
        PlayersRegistrationData newPlayerDataOut = null;
        int newPlayerID = newPlayerID();

        if(newPlayerID > 0) {
            playersHashMap.put(newPlayerID, new PlayerReady(remotePeer, false));

            Integer[] playersId = (Integer[]) playersHashMap.keySet().toArray();
            int[] playersIntID = new int[playersId.length];

            for(int i=0;i<playersId.length;i++) {
                if (playersId != null)
                    playersIntID[i] = playersId[i].intValue();
            }

            newPlayerDataOut = new PlayersRegistrationData(newPlayerID, playersIntID);
        }
        else {
            newPlayerDataOut = new PlayersRegistrationData(newPlayerID, null);
        }

        // todo send broadcast notice to other players
        return newPlayerDataOut;
    }

    @Override
    public PlayersRegistrationData playerRegistration(String addr) throws RemoteException, NotBoundException {
        PlayersRegistrationData newPlayerDataOut = registerPlayer(addr);

        return newPlayerDataOut;
    }

    @Override
    public void playerReady(int playerID) throws RemoteException {
        
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try{
            GameRegistration registrationService = new GameRegistration();
            RemoteRegistration stub = (RemoteRegistration) UnicastRemoteObject.exportObject(registrationService, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(RMI_REG_OBJ_NAME, stub);

            System.out.println(RMI_REG_OBJ_NAME+" bound");
        }
        catch (Exception e) {
            System.err.println("Registration service Exception:");
            e.printStackTrace();
        }

    }
}

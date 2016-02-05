import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GamePeer implements RemotePeer {
    private boolean hasGameToken;
    private boolean hasFTToken;
    private int ID;
    private ArrayList<GamePeer> gamePeers = null;
    private static final String RMI_OBJ_NAME = "RemotePeer";

    public GamePeer(int id){
        this.ID = id;
        hasGameToken = false;
        gamePeers = new ArrayList<>();
        initRMIServer();
    }

    public GamePeer(int id, boolean hasGameToken, boolean hasFTToken){
        this.ID = id;
        this.hasGameToken = hasGameToken;
        this.hasFTToken = hasFTToken;
        gamePeers = new ArrayList<>();
        initRMIServer();
    }

    private void initRMIServer(){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            RemotePeer stub = (RemotePeer) UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(RMI_OBJ_NAME, stub);
            System.out.println(RMI_OBJ_NAME+" bound");
        } catch (Exception e) {
            System.err.println(RMI_OBJ_NAME+":");
            e.printStackTrace();
        }
    }

    public void addRemotePeer(String addr){
        try {
            Registry registry = LocateRegistry.getRegistry(addr);
            RemotePeer remotePeer = (RemotePeer) registry.lookup(RMI_OBJ_NAME);
            gamePeers.add( (GamePeer) remotePeer);
        } catch (Exception e) {
            System.err.println("addRemotePeer exception:");
            e.printStackTrace();
        }
    }

    public void getGameToken(){
        hasGameToken = true;
        System.out.println("ID: "+this.ID+" Game token received!");
    }

    public void getFTToken(FaultToleranceToken ftToken){
        hasFTToken = true;
    }


}

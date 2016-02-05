import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;

public class GamePeer implements RemotePeer{
    private boolean hasGameToken;
    private boolean hasFTToken;
    private int ID;
    public HashMap<Integer, RemotePeer> remotePeerHashMap;
    private static final String RMI_OBJ_NAME = "RemotePeer";

    public GamePeer(int id){
        this.ID = id;
        hasGameToken = false;
        remotePeerHashMap = new HashMap<>();
        initRMIServer();
        //init fault tolerance thread

    }

    public GamePeer(int id, boolean hasGameToken, boolean hasFTToken){
        this.ID = id;
        this.hasGameToken = hasGameToken;
        this.hasFTToken = hasFTToken;
        remotePeerHashMap = new HashMap<>();
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
            remotePeerHashMap.put(remotePeer.getID(), remotePeer);
        } catch (Exception e) {
            System.err.println("addRemotePeer exception:");
            e.printStackTrace();
        }
    }

    public void sendGameToken(int peerID) throws RemoteException{
        if(hasGameToken){
            hasGameToken = false;
            remotePeerHashMap.get(peerID).getGameToken();
        }
    }

    public void passFTToken(){
        if(hasFTToken){
            hasFTToken = false;
            int nextPeer = getNextInRing();
            if(nextPeer != -1) {
                try {
                    remotePeerHashMap.get(nextPeer).getFTToken();
                }catch (RemoteException e){
                    System.out.println("No other processes present in the ring");
                }
            }
            else
                System.out.println("No other processes present in the ring");
        }
    }

    //RemotePeer Interface implementation
    public int getID(){
        return this.ID;
    }

    public void getGameToken(){
        hasGameToken = true;
        System.out.println("ID: "+this.ID+" Game token received!");
    }

    public void getFTToken(){
        hasFTToken = true;
        try{
            //Simulating processing of token
            Thread.sleep(500);
            passFTToken();
            System.out.println("Token received");
        }catch (InterruptedException e){System.out.println("Sleep interrupted");};
    }

    //get the next peerID
    //if it return -1 it means there are no neighbours
    public int getNextInRing(){
        int peersNumber = remotePeerHashMap.size();
        for(int i = getID()+UnoRules.getDirection(); i != getID(); i = (i+UnoRules.getDirection()) % peersNumber ){
            if(remotePeerHashMap.containsKey(i))
                return i;
        }
        return -1;
    }


}

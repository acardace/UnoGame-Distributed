import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GamePeer implements RemotePeer{
    public HashMap<Integer, RemotePeer> remotePeerHashMap;
    public ReentrantLock ftTokenRecvLock;
    public boolean ftTokenRecv;

    private boolean hasGameToken;
    private boolean hasFTToken;
    private int ID;
    private Timer ftTimer;
    private FTTokenPasserThread ftTokenPasserThread;

    private static final String RMI_OBJ_NAME = "RemotePeer";
    private static final int FT_TIMEOUT = 3000; //in ms
    private static final int FT_RING_DIRECTION = 1;

    public GamePeer(int id){
        this.ID = id;
        hasGameToken = false;
        remotePeerHashMap = new HashMap<>();
        initRMIServer();
        initFT();
    }

    public GamePeer(int id, boolean hasGameToken, boolean hasFTToken){
        this.ID = id;
        this.hasGameToken = hasGameToken;
        this.hasFTToken = hasFTToken;
        remotePeerHashMap = new HashMap<>();
        initRMIServer();
        initFT();
    }

    private void initFT(){
        ftTimer = new Timer();
        ftTokenPasserThread = new FTTokenPasserThread();
        ftTokenRecvLock = new ReentrantLock();
        ftTokenRecv = false;
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

    public void startFTTokenPassing(){
        ftTokenPasserThread.start();
    }

    public void addRemotePeer(String addr) throws NotBoundException, RemoteException{
        Registry registry = LocateRegistry.getRegistry(addr);
        RemotePeer remotePeer = (RemotePeer) registry.lookup(RMI_OBJ_NAME);
        remotePeerHashMap.put(remotePeer.getID(), remotePeer);
    }

    public void sendGameToken(int peerID) throws RemoteException{
        if(hasGameToken){
            hasGameToken = false;
            remotePeerHashMap.get(peerID).getGameToken();
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
        ftTokenRecvLock.lock();
        ftTokenRecv = true;
        ftTokenRecvLock.unlock();
        try {
            ftTokenPasserThread.lock.lock();
            hasFTToken = true;
            ftTokenPasserThread.recvdFTToken.signal();
        }finally {
            ftTokenPasserThread.lock.unlock();
        }
        System.out.println("Token received");
    }

    //get the next peerID the ring
    //if it return -1 it means there are no neighbours
    public int getNextInRing(int direction){
        int peersNumber = remotePeerHashMap.size();
        for(int i = getID()+direction; i != getID(); i = (i+direction) % peersNumber ){
            if(remotePeerHashMap.containsKey(i))
                return i;
        }
        return -1;
    }

    public class FaultToleranceThread extends TimerTask{

         public void run(){
             ftTokenRecvLock.lock();
             System.out.println("Timeout Elapsed");
             if(ftTokenRecv) {
                 //internal ACK
                 ftTokenRecv = false;
                 ftTokenRecvLock.unlock();
                 System.out.println("FT TOKEN OK");
             }
             else {
                 ftTokenRecvLock.unlock();
                 System.out.println("FT_Thread: Failure detected, token not received in "+FT_TIMEOUT+"ms");
             }
         }
    }

    public class FTTokenPasserThread extends Thread{
        public ReentrantLock lock;
        public Condition recvdFTToken;

        public FTTokenPasserThread(){
            lock = new ReentrantLock();
            recvdFTToken = lock.newCondition();
        }

        public void run(){
            while(true){
                try {
                    lock.lock();
                    while (!hasFTToken)
                        recvdFTToken.await();
                    //DO PASSING
                    try{
                        //Simulating processing of token
                        Thread.sleep(1000);
                    }catch (InterruptedException e){System.out.println("Sleep interrupted");}
                    passFTToken();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }

        private void passFTToken(){
            hasFTToken = false;
            int nextPeer = getNextInRing(FT_RING_DIRECTION);
            while(nextPeer != -1 ){
                try {
                    //Pass token
                    remotePeerHashMap.get(nextPeer).getFTToken();
                    System.out.println("Token Sent");
                    //Launch Fault tolerance timeout
                    ftTimer.schedule(new FaultToleranceThread(), FT_TIMEOUT);
                    break;
                }catch (RemoteException e){
                    System.out.println("Communication failed, removing host from hashMap");
                    remotePeerHashMap.remove(nextPeer);
                }
                nextPeer = getNextInRing(FT_RING_DIRECTION);
            }
        }
    }

}

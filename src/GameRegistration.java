import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GameRegistration implements RemoteRegistration {
    private static final String RMI_REG_OBJ_NAME = "RegistrationService";
    private static final String RMI_OBJ_NAME = "RemotePeer";
    private static final int MAX_PLAYERS = 8;
    private static final int MIN_START_PLAYERS = 3;
    private static final int START_GAME_TIMEOUT = 300000; // milliseconds -> 5 minuti

    private HashMap<Integer, PlayerReady> playersHashMap;
    private int playersReadyCounter;
    private int playersCounter;
    private Timer startGameTimer;

    public ReentrantLock syncPlayersLock;
    public Condition syncPlayersCondition;

    public GameRegistration() {
        syncPlayersLock = new ReentrantLock();
        syncPlayersCondition = syncPlayersLock.newCondition();

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

    private void setGameStartTimer(int timeout) {
        if(startGameTimer == null) {
            startGameTimer = new Timer();
            startGameTimer.schedule(new startGameThread(), timeout);
        }
    }

    private void waitGameStart() {
        while(true) {
            try {
                syncPlayersLock.lock();
                syncPlayersCondition.await();
            } catch (InterruptedException e) {
                // do nothing
            } finally {
                syncPlayersLock.unlock();
            }
        }
    }

    private void gameStart() {
        announcePlayers();
        syncPlayersLock.lock();
        syncPlayersCondition.signalAll();
        syncPlayersLock.unlock();
        System.out.println("Game started");
    }

    //broadcast to all the players the complete list
    private void announcePlayers(){
        HashMap<Integer, String> allPlayers = new HashMap<>();
        for (Integer key: playersHashMap.keySet())
            allPlayers.put(key, playersHashMap.get(key).addr);

        for (Integer key: playersHashMap.keySet()) {
            try {
                HashMap<Integer, String> players = (HashMap<Integer,String>) allPlayers.clone();
                players.remove(key);
                playersHashMap.get(key).playerRef.addPlayers(players);
            } catch (RemoteException e) {
                System.err.println("announcePlayers(): Failed communications with player at " + playersHashMap.get(key).addr);
                e.printStackTrace();
            }
        }
    }

    private ArrayList<String> registerPlayer(int id, String playerAddr) throws  RemoteException, NotBoundException {
        // insert player in "play room"
        Registry registry = LocateRegistry.getRegistry(playerAddr);
        RemotePeer remotePeer = (RemotePeer) registry.lookup(RMI_OBJ_NAME);

        ArrayList<String> playersAll = new ArrayList<>();
        for (Integer key: playersHashMap.keySet()){
            playersAll.add(playersHashMap.get(key).addr);
        }

        if(id > 0) {
            playersHashMap.put(id, new PlayerReady(playerAddr, remotePeer, false));
        }

        return playersAll;
    }

    @Override
    public int getNewPlayerID() throws RemoteException {
        return newPlayerID();
    }

    @Override
    public int[] getPlayersID() throws RemoteException {
        Integer[] playersId = (Integer[]) playersHashMap.keySet().toArray();
        int[] playersIntID = new int[playersId.length];

        for(int i=0;i<playersId.length;i++) {
            if (playersId != null)
                playersIntID[i] = playersId[i].intValue();
        }

        return playersIntID;
    }

    @Override
    public ArrayList<String> playerRegistration(int id, String playerAddr) throws RemoteException, NotBoundException {
        return registerPlayer(id, playerAddr);
    }

    @Override
    public void playerReady(int playerID) throws RemoteException {
        playersHashMap.get(playerID).playerReady = true;
        playersReadyCounter++;

        if(playersReadyCounter >= MIN_START_PLAYERS && playersReadyCounter == playersCounter)
            gameStart();
        else {
            if(playersReadyCounter >= MIN_START_PLAYERS)
                setGameStartTimer(START_GAME_TIMEOUT);
            System.out.println("Player "+playerID+" waiting...");
            waitGameStart();
        }
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

    public class startGameThread extends TimerTask {
        public void run() {
            // thread che esegue allo scadere del timeout settato dopo il ready del 3th giocatore
            gameStart();
        }
    }
}

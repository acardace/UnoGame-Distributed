package unogame.server;

import unogame.peer.RemotePeer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.logging.Logger;
import java.util.concurrent.locks.ReentrantLock;

public class GameRegistration implements RemoteRegistration {
    private final static Logger LOGGER = Logger.getLogger(GameRegistration.class.getName());
    private static final String RMI_REG_OBJ_NAME = "RegistrationService";
    private static final String RMI_OBJ_NAME = "RemotePeer";
    private static final int RMI_PORT = 1099;
    public static final int MAX_PLAYERS = 4;
    private static final int MIN_START_PLAYERS = 4;
    private static final int START_GAME_TIMEOUT = 300000; // milliseconds -> 5 mins
    private static final int ERROR = -1;

    private HashMap<Integer, PlayerReady> playersHashMap;
    private int playersReadyCounter;
    private int playersCounter;
    private Timer startGameTimer;
    private Random randomGenerator;

    public ReentrantLock playersReadyLock;
    public ReentrantLock playersCounterLock;
    public ReentrantLock playersHashMapLock;
    public ReentrantLock startGameTimerLock;

    public ReentrantLock syncPlayersLock;
    public Condition syncPlayersCondition;

    public GameRegistration() {
        syncPlayersLock = new ReentrantLock();
        playersReadyLock = new ReentrantLock();
        playersCounterLock = new ReentrantLock();
        playersHashMapLock = new ReentrantLock();
        startGameTimerLock = new ReentrantLock();
        syncPlayersCondition = syncPlayersLock.newCondition();

        playersReadyCounter = 0;
        playersCounter = -1;
        playersHashMap = new HashMap<>();
        randomGenerator = new Random();
    }

    private int newPlayerID() {
        playersCounterLock.lock();
        try {
            if(playersCounter < MAX_PLAYERS)
                playersCounter++;
            else
                playersCounter = ERROR;
        }
        finally {
            playersCounterLock.unlock();
        }

        return playersCounter;
    }

    private void setGameStartTimer(int timeout) {
        startGameTimerLock.lock();

        try{
            if(startGameTimer == null) {
                startGameTimer = new Timer();
                startGameTimer.schedule(new startGameThread(), timeout);
            }
        }
        finally {
            startGameTimerLock.unlock();
        }
    }

    private void waitGameStart() {
        try {
            syncPlayersLock.lock();
            syncPlayersCondition.await();
        } catch (InterruptedException e) {
            // do nothing
        } finally {
            syncPlayersLock.unlock();
        }
    }

    private void gameStart() {
        announcePlayers();
        syncPlayersLock.lock();
        syncPlayersCondition.signalAll();
        syncPlayersLock.unlock();
        LOGGER.info("Game started");
    }

    //broadcast to all the players the complete list
    private void announcePlayers(){
        HashMap<Integer, String> allPlayers = new HashMap<>();

        playersHashMapLock.lock();

        try {
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
        finally {
            playersHashMapLock.unlock();
        }
    }

    private ArrayList<String> registerPlayer(int id, String playerAddr) throws  RemoteException, NotBoundException {
        // insert player in "play room"
        Registry registry = LocateRegistry.getRegistry(playerAddr);
        RemotePeer remotePeer = (RemotePeer) registry.lookup(RMI_OBJ_NAME);
        ArrayList<String> playersAll = new ArrayList<>();

        playersHashMapLock.lock();

        try {
            for (Integer key: playersHashMap.keySet()){
                playersAll.add(playersHashMap.get(key).addr);
            }
            playersHashMap.put(id, new PlayerReady(playerAddr, remotePeer, false));
        }
        finally {
            playersHashMapLock.unlock();
        }

        return playersAll;
    }

    @Override
    public int getNewPlayerID() throws RemoteException {
        return newPlayerID();
    }

    @Override
    public int generateSeed() throws RemoteException {
        return randomGenerator.nextInt();
    }

    @Override
    public int[] getPlayersID() throws RemoteException {
        Integer[] playersId = null;

        playersHashMapLock.lock();
        try{
            playersId = (Integer[]) playersHashMap.keySet().toArray();
        }
        finally {
            playersHashMapLock.unlock();
        }


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
        playersHashMapLock.lock();

        try{
            playersHashMap.get(playerID).playerReady = true;
        }
        finally {
            playersHashMapLock.unlock();
        }

        playersReadyLock.lock();
        playersCounterLock.lock();

        playersReadyCounter++;

        if(playersReadyCounter >= MIN_START_PLAYERS && playersReadyCounter == (playersCounter+1))
            gameStart();
        else {
            if(playersReadyCounter >= MIN_START_PLAYERS)
                setGameStartTimer(START_GAME_TIMEOUT);
            LOGGER.info("Player "+playerID+" waiting...");
            playersCounterLock.unlock();
            playersReadyLock.unlock();
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
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.rebind(RMI_REG_OBJ_NAME, stub);
            LOGGER.info(RMI_REG_OBJ_NAME+" bound");
        }
        catch (Exception e) {
            LOGGER.warning("Registration service Exception:");
            e.printStackTrace();
        }

    }

    public class startGameThread extends TimerTask {
        public void run() {
            gameStart();
        }
    }
}

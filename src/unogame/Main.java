package unogame;

import java.net.Inet4Address;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import unogame.game.UnoDeck;
import unogame.game.UnoPlayer;
import unogame.gui.StartFrame;
import unogame.peer.GamePeer;
import unogame.server.RemoteRegistration;

import unogame.gui.Table;

//REAL MAIN
public class Main {
    private static final String REGISTRATION_SERVICE = "RegistrationService";

    public static void main(String[] args) {
        ReentrantLock startFrameLock = new ReentrantLock();
        Condition startFrameCondition = startFrameLock.newCondition();
        RemoteRegistration regService = null;
        Table gameTable;

        // start
        if (args.length < 1){
            System.err.println("no command line argument for the server address");
            System.exit(1);
        }

        String serverAddr = args[0];
        int myPlayerID = -1;
        GamePeer p1;

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serverAddr);
            regService = (RemoteRegistration) registry.lookup(REGISTRATION_SERVICE);
            myPlayerID = regService.getNewPlayerID();
            int seed = regService.generateSeed();

            //GUITest peerID remotePeer
            if (myPlayerID == 1)
                p1 = new GamePeer(myPlayerID, true, true, new UnoPlayer(), new UnoDeck(seed));
            else
                p1 = new GamePeer(myPlayerID, false, false, new UnoPlayer(), new UnoDeck(seed));

            ArrayList<String> allActualPlayers = regService.playerRegistration(myPlayerID, Inet4Address.getLocalHost().getCanonicalHostName());

            for (String playerAddr : allActualPlayers) {
                p1.addRemotePeer(playerAddr);
            }

            // start new startFrame
            StartFrame startGUIFrame = new StartFrame(myPlayerID);
            startGUIFrame.setStartTrigger(startFrameLock, startFrameCondition);
            // declare ready and wait
            startFrameLock.lock();
            startFrameCondition.await();
            startFrameLock.unlock();
            //dispose Frame
            startGUIFrame.setVisible(false);
            startGUIFrame.dispose();
            //lock at server gathering point
            System.out.println("Waiting for other players...");
            regService.playerReady(myPlayerID);
            p1.startFTTokenPassing();
            //From here on we start the Uno Game as we know it
            gameTable = new Table();
        } catch (Exception e) {
            System.err.println(REGISTRATION_SERVICE + " exception:");
            e.printStackTrace();
        }
    }

}
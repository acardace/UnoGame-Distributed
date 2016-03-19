import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

//TESTING
public class GUI{
    private static final String REGISTRATION_SERVICE = "RegistrationService";

    public static void main(String[] args) {
        RemoteRegistration regService = null;

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

            //Main peerID remotePeer
            if (myPlayerID == 1)
                //TODO creare seme da madare al deck e tenere traccia del player e del deck....forse
                p1 = new GamePeer(myPlayerID, true, true, new UnoPlayer(), new UnoDeck(2));
            else
                p1 = new GamePeer(myPlayerID, false, false, new UnoPlayer(), new UnoDeck(2));

            ArrayList<String> allActualPlayers = regService.playerRegistration(myPlayerID, Inet4Address.getLocalHost().getCanonicalHostName());

            for (String playerAddr : allActualPlayers) {
                p1.addRemotePeer(playerAddr);
            }

            // todo send broadcast to all others players

            // declare ready and wait
            //regService.playerReady(myPlayerID);
            //p1.startFTTokenPassing();
        } catch (Exception e) {
            System.err.println(REGISTRATION_SERVICE + " exception:");
            e.printStackTrace();
        }

        // start GUI with play button
        StartFrame startGUIFrame = new StartFrame(regService, myPlayerID);
    }

}
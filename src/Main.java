import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

//TESTING
public class Main implements ActionListener {
    private static final String REGISTRATION_SERVICE = "RegistrationService";
    private RemoteRegistration regService;
    private GamePeer p1;
    private int myPlayerID = -1;
    private String serverAddr;


    public Main(String[] args) {
        // start

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serverAddr);
            RemoteRegistration regService = (RemoteRegistration) registry.lookup(REGISTRATION_SERVICE);
            myPlayerID = regService.getNewPlayerID();

            //Main peerID remotePeer
            if(myPlayerID == 1)
                p1 = new GamePeer(myPlayerID, true , true);
            else
                p1 = new GamePeer(myPlayerID, false , false);

            ArrayList<String> allActualPlayers = regService.playerRegistration(myPlayerID, Inet4Address.getLocalHost().getCanonicalHostName());

            for(String playerAddr: allActualPlayers) {
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
    }

    public void actionPerformed(ActionEvent e) {
        try {
            this.regService.playerReady(myPlayerID);
            p1.startFTTokenPassing();
        } catch (Exception exception) {

        }
    }

    public static void main(String[] args){
        Main mainObj = new Main(args);
        // start GUI with play button
        StartFrame startGUIFrame = new StartFrame(mainObj);
    }
}
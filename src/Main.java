import java.net.Inet4Address;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

//TESTING
public class Main {
    private static final String REGISTRATION_SERVICE = "RegistrationService";

    public static void main(String[] args){
        int myPlayerID = -1;
        String serverAddr = args[0];
        GamePeer p1;
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

                System.out.println(playerAddr + " added!");
                p1.startFTTokenPassing();
            }

            // todo send broadcast to all others players

            // declare ready and wait
            regService.playerReady(myPlayerID);

        } catch (Exception e) {
            System.err.println(REGISTRATION_SERVICE + " exception:");
            e.printStackTrace();
        }
        ////




        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();
        System.out.println("Adding remote peer "+args[1]);
        try {

        }catch (Exception e){
            System.out.println("Connection refused");
        }*/
    }
}
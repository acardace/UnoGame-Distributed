import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

//TESTING
public class Main {
    private static final String REGISTRATION_SERVICE = "RegistryService";

    public static void main(String[] args){
        int myPlayerID = -1;
        String addr = "XXX ADDRESS DA METTERE QUELLO VERO";
        // start

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry();
            RemoteRegistration regService = (RemoteRegistration) registry.lookup(REGISTRATION_SERVICE);
            myPlayerID = regService.getNewPlayerID();

            //Main peerID remotePeer
            GamePeer p1 = new GamePeer(myPlayerID, true , true);

            PlayerReady[] allActualPlayers = regService.playerRegistration(myPlayerID, addr);

            for(int i=0;i<allActualPlayers.length;i++) {
                String playerAddr = allActualPlayers[i].addr;
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
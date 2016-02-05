import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Timer;

//TESTING
public class Main {
    public static void main(String[] args){
        GamePeer p1 = new GamePeer(Integer.parseInt(args[0]), ( (Integer.parseInt(args[0]) == 0 ) ? true : false), false);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();
        System.out.println("Adding remote peer "+args[1]);
        p1.addRemotePeer(args[1]);
        System.out.println(args[1]+" added!");
        try {
            System.out.println("Remote Peer ID: " + p1.remotePeers.get(0).getID());
            Thread.sleep(3000);
            p1.sendGameToken(0);
            System.out.println("No token no more");
        }catch (RemoteException e){e.printStackTrace();}
        catch (InterruptedException e){e.printStackTrace();}
    }
}

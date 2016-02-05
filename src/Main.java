import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Scanner;

//TESTING
public class Main {
    public static void main(String[] args){
        GamePeer p1 = new GamePeer(Integer.parseInt(args[0]),
                ( (Integer.parseInt(args[0]) == 0 ) ? true : false), ( (Integer.parseInt(args[0]) == 0 ) ? true : false));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();
        System.out.println("Adding remote peer "+args[1]);
        p1.addRemotePeer(args[1]);
        System.out.println(args[1]+" added!");
        try {
            Iterator<Integer> iterator = p1.remotePeerHashMap.keySet().iterator();
            int remoteID = p1.remotePeerHashMap.get(iterator.next()).getID();
            System.out.println("Remote Peer ID: "+remoteID);
            Thread.sleep(3000);
            p1.sendGameToken(remoteID);
            System.out.println("No token no more");
            System.out.println("Next in ring: "+p1.remotePeerHashMap.get(p1.getNextInRing()).getID());
            p1.passFTToken();
        }catch (RemoteException e){e.printStackTrace();}
        catch (InterruptedException e){e.printStackTrace();}
    }
}

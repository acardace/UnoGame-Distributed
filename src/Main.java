import java.util.Scanner;

//TESTING
public class Main {
    public static void main(String[] args){
        //Main peerID remotePeer
        GamePeer p1 = new GamePeer(Integer.parseInt(args[0]),
                ( (Integer.parseInt(args[0]) == 0 ) ? true : false), ( (Integer.parseInt(args[0]) == 0 ) ? true : false));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();
        System.out.println("Adding remote peer "+args[1]);
        try {
            p1.addRemotePeer(args[1]);
            System.out.println(args[1] + " added!");
            p1.startFTTokenPassing();
        }catch (Exception e){
            System.out.println("Connection refused");
        }
    }
}

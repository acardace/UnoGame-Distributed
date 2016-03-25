package unogame;

import unogame.game.*;
import unogame.game.Number;
import unogame.gui.Table;

//TESTING
public class Main {
    public static void main(String[] args){
        //Main peerID remotePeer
//        UnoPlayer player = new UnoPlayer();
//        long seed = 5;
//        int arg = Integer.parseInt(args[0]);
//        UnoDeck deck = new UnoDeck(seed);
//        GamePeer p1 = new GamePeer(arg, arg == 1 , arg == 1, player, deck );
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Input something to go on");
//        scanner.next();
        Table table=new Table();
        UnoPlayer player = new UnoPlayer();
        UnoDeck deck = new UnoDeck(5);
        player.drawInitialHand(deck);
        for (UnoCard card: player.getHand())
            table.addCard(card);
        table.removeCard(player.getHand().get(0));

//        try {
//            p1.addRemotePeer(args[1]);
//            p1.startFTTokenPassing();
//            if (!player.getHasInitialHand() && p1.getID()==1){
//                p1.initGT();
//                p1.initialHand();
//            }
//        }catch (Exception e){
//            System.out.println("Connection refused");
//        }
    }
}
package unogame;

import unogame.game.*;
import unogame.game.Number;
import unogame.gui.GUITable;
import unogame.peer.GamePeer;

import java.util.Scanner;

//TESTING
public class GUITest {
    public static void main(String[] args){
        if (args.length < 2){
            System.err.println("usage: <appname> <playerID> <remote-addr1> <remote-addr2>");
            System.exit(1);
        }
        String player1Addr = args[1];
        String player2Addr = null;
        if( args.length == 3)
            player2Addr = args[2];
        //GUITest peerID remotePeer
        UnoPlayer player = new UnoPlayer();
        long seed = 37;
        int arg = Integer.parseInt(args[0]);
        UnoDeck deck = new UnoDeck(seed);
        UnoRules.setDeckInUse(deck);
        GamePeer p1 = new GamePeer(arg, arg == 0 , arg == 0, player, deck );

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();

        try{
            p1.addRemotePeer(player1Addr);
            if(player2Addr != null){
                p1.addRemotePeer(player2Addr);
            }
            p1.startFTTokenPassing();
        }catch(Exception e){}

        GUITable guiTable = new GUITable(p1);
        guiTable.initGame();
        System.out.println("Game Started");
    }
}
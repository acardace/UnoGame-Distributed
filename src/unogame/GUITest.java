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
            System.err.println("usage: <appname> <playerID> <remote-addr>");
            System.exit(1);
        }
        String playerAddr = args[1];
        //GUITest peerID remotePeer
        UnoPlayer player = new UnoPlayer();
        long seed = 23;
        int arg = Integer.parseInt(args[0]);
        UnoDeck deck = new UnoDeck(seed);
        UnoRules.setDeckInUse(deck);
        GamePeer p1 = new GamePeer(arg, arg == 1 , arg == 1, player, deck );

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input something to go on");
        scanner.next();

        try{
            p1.addRemotePeer(playerAddr);
            p1.startFTTokenPassing();
        }catch(Exception e){}


        GUITable guiTable = new GUITable(p1);
        guiTable.initGame();
        System.out.println("Game Started");
    }
}
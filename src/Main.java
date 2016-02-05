import java.util.ArrayList;
import java.util.Scanner;


//TESTING
public class Main {
    public static void main(String[] args){
        UnoPlayer p1 = new UnoPlayer();
        UnoDeck deck = new UnoDeck();
        UnoRules.setDeckInUse(deck);

        p1.drawInitialHand(deck);
        System.out.println("My hand");
        p1.listHand();
        System.out.println();
        System.out.format("\nlast Discard pile card\n");
        System.out.format("%s\n------------------------\n", deck.getLastDiscardedCard().getCardID());
        ArrayList<UnoCard> plays = UnoRules.availablePlays(p1.getHand());
        int i = 0;
        for(UnoCard card: plays){
            System.out.format("%d - %s\n", i, card.getCardID());
            i++;
        }
        System.out.println("Play a card (int)");
        Scanner scanner = new Scanner(System.in);
        int playIndex = scanner.nextInt();
        p1.playCard(plays.get(playIndex), deck);
        System.out.println("My hand");
        p1.listHand();
        System.out.println();
        System.out.format("\nlast Discard pile card\n");
        System.out.format("%s\n------------------------\n", deck.getLastDiscardedCard().getCardID());
        plays = UnoRules.availablePlays(p1.getHand());
        i = 0;
        for(UnoCard card: plays){
            System.out.format("%d - %s\n", i, card.getCardID());
            i++;
        }

    }
}

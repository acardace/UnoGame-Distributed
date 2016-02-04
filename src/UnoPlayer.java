import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;

    public void drawInitialHand(UnoDeck deck){
        hand = deck.drawHand();
    }

    //Debugging purposes
    public void listHand(){
        for(UnoCard card : hand){
            System.out.println(card.getCardID());
        }
    }
}

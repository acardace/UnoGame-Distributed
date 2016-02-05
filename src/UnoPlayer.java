import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;

    public ArrayList<UnoCard> getHand() {
        return hand;
    }

    public void drawInitialHand(UnoDeck deck){
        hand = deck.drawHand();
    }

    public boolean playCard(UnoCard card, UnoDeck deck){
        if(UnoRules.isPlayable(card)){
            deck.setLastDiscardedCard(card);
            hand.remove(card);
            return true;
        }
        return false;
    }

    //Debugging purposes
    public void listHand(){
        for(UnoCard card : hand){
            System.out.println(card.getCardID());
        }
    }
}

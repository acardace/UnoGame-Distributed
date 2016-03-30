package unogame.game;

import unogame.peer.GamePeer;

import java.rmi.RemoteException;
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
        //TODO add plus card counter and direction
        if(UnoRules.isPlayable(card)){
            if(UnoRules.isChangingDirection(card))
                UnoRules.changeDirection();
            deck.setLastDiscardedCard(card);
            hand.remove(card);
            return true;
        }

        return false;
    }

    public void getCardfromDeck(UnoDeck deck){
        UnoCard card=deck.drawCard();
        hand.add(card);
    }

    public void emptyHand(){
        this.hand.clear();
    }

    //Debugging purposes
    public void listHand(){
        for(UnoCard card : hand){
            System.out.println(card.getCardID());
        }
    }
}

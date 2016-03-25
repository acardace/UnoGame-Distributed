package unogame.game;

import unogame.peer.GamePeer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;
    private boolean hasInitialHand = false;
    public ArrayList<UnoCard> getHand() {
        return hand;
    }

    public void drawInitialHand(UnoDeck deck){
        hand = deck.drawHand();
        this.hasInitialHand=true;
    }

    public boolean playCard(UnoCard card, UnoDeck deck){
        if(UnoRules.isPlayable(card)){
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

    public void PlayHand(GamePeer gamePeer){
        try {
            gamePeer.sendGameToken(); //e il mazzo etc
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean getHasInitialHand(){return hasInitialHand;}

    public void setHasInitialHand(boolean hasOrNot){ this.hasInitialHand=hasOrNot;}

    //Debugging purposes
    public void listHand(){
        for(UnoCard card : hand){
            System.out.println(card.getCardID());
        }
    }
}

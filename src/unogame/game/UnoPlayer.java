package unogame.game;

import unogame.peer.GamePeer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;
    private boolean recvSpecial = false;
    private int cardsToPick = 0; //when you recv a plus card, the count is here
    private boolean playedCard = false; //the player has played a card in this turn

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

    public boolean hasRecvSpecial() {
        return recvSpecial;
    }

    public void setRecvSpecial(boolean recvSpecial) {
        this.recvSpecial = recvSpecial;
    }

    public int getCardsToPick() {
        return cardsToPick;
    }

    public void setCardsToPick(int cardsToPick) {
        this.cardsToPick = cardsToPick;
    }

    public UnoCard getCardfromDeck(UnoDeck deck){
        UnoCard card=deck.drawCard();
        hand.add(card);
        return card;
    }

    public boolean hasPlayedCard() {
        return playedCard;
    }

    public void setPlayedCard(boolean playedCard) {
        this.playedCard = playedCard;
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

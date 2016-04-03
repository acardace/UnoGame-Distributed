package unogame.game;

import java.util.ArrayList;

public class UnoPlayer {
    private ArrayList<UnoCard> hand;
    private boolean recvSpecial = false;
    private int cardsToPick = 0; //when you recv a plus card, the count is here
    private boolean playedCard = false; //the player has played a card in this turn
    private Color selectedColor = null;
    private int playerAboutToWin = -1;

    public ArrayList<UnoCard> getHand() {
        return hand;
    }

    public void drawInitialHand(UnoDeck deck){
        hand = deck.drawHand();
    }

    public void playCard(UnoCard card, UnoDeck deck){
        if(UnoRules.isChangingDirection(card))
            UnoRules.changeDirection();
        deck.setLastDiscardedCard(card);
        hand.remove(card);
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

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void emptyHand(){
        this.hand.clear();
    }

    public int getPlayerAboutToWin() {
        return playerAboutToWin;
    }

    public void setPlayerAboutToWin(int playerAboutToWin) {
        this.playerAboutToWin = playerAboutToWin;
    }

    //Debugging purposes
    public void listHand(){
        for(UnoCard card : hand){
            System.out.println(card.getCardID());
        }
    }
}

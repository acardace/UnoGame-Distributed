package unogame.game;

import java.util.ArrayList;

/**
 * Static class which dictates how it works around here
 * if you want to play you've got to stick with what's below here
 */
public class UnoRules {
    private static final int FORWARD = 1;
    private static final int BACKWARDS = -1;

    private static UnoDeck deckInUse;
    private static Color currentColor = Color.BLACK;
    private static Number currentNumber;
    private static SpecialType currentType;
    private static int direction = 1; //1 = FORWARD or  -1 = BACKWARDS

    public static Color getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(Color currentColor) {
        UnoRules.currentColor = currentColor;
    }

    public static int getDirection() {
        return direction;
    }

    public static void setDirection(int direction) {
        UnoRules.direction = direction;
    }

    public static UnoDeck getDeckInUse() {
        return deckInUse;
    }

    public static void setDeckInUse(UnoDeck deckInUse) {
        UnoRules.deckInUse = deckInUse;
    }

    public static Number getCurrentNumber() {
        return currentNumber;
    }

    public static void setCurrentNumber(Number currentNumber) {
        UnoRules.currentNumber = currentNumber;
    }

    public static ArrayList<UnoCard> availablePlays(ArrayList<UnoCard> hand){
        updateStatus();
        ArrayList<UnoCard> playableCards = new ArrayList<>(hand.size());
        for(UnoCard card : hand){
            if (isPlayable(card))
                playableCards.add(card);
        }
        return playableCards;
    }

    public static void changeDirection(){
        if( direction == FORWARD)
            direction = BACKWARDS;
        else
            direction = FORWARD;
    }

    public static boolean isPlayable(UnoCard card){
        updateStatus();
        if(currentColor.equals(Color.BLACK)){
            return true;
        }
        if (card.getColor().equals(currentColor)
                || ( card.getNumber().equals(currentNumber) && !currentNumber.equals(Number.NONE)) ){
            return true;
        }
        if (card.getType().equals(SpecialType.CHANGECOLOUR) ||
                card.getType().equals(SpecialType.PLUS4)) {
            return true;
        }
        if (deckInUse.getLastDiscardedCard().isSpecial() && card.isSpecial()
                && card.getType().equals(currentType) ){
                return true;
        }
        return false;
    }

    public static boolean isChangingDirection(UnoCard card){
        return card.getType() == SpecialType.REVERSE;
    }

    public static boolean isSpecialPlayable(ArrayList<UnoCard> cards){
        updateStatus();
        if( currentType == SpecialType.PLUS2){
            for(UnoCard card: cards){
                if (card.getType() == currentType)
                    return true;
            }
        }else if(currentType == SpecialType.PLUS4){
            for(UnoCard card: cards){
                if (card.getType() == currentType)
                    return true;
            }
        }
        return false;
    }

    public static boolean hasSomethingPlayable(ArrayList<UnoCard> cards){
        for(UnoCard card: cards){
            if (isPlayable(card))
                return true;
        }
        return false;
    }

    private static void updateStatus(){
        if( deckInUse.getLastDiscardedCard().getColor() != Color.BLACK)
            currentColor = deckInUse.getLastDiscardedCard().getColor();
        currentNumber = deckInUse.getLastDiscardedCard().getNumber();
        currentType = deckInUse.getLastDiscardedCard().getType();
    }
}

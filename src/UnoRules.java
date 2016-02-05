import java.util.ArrayList;

/**
 * Static class which dictates how it works around here
 * if you want to play you've got to stick with what's below here
 */
public class UnoRules {
    private static UnoDeck deckInUse;
    private static Color currentColor;
    private static Number currentNumber;
    private static SpecialType currentType;
    private static String direction; //can be FORWARD or BACKWARDS
    private static final String FORWARD = "FORWARD";
    private static final String BACKWARDS = "BACKWARDS";

    public static Color getCurrentColor() {
        return currentColor;
    }

    public static void setCurrentColor(Color currentColor) {
        UnoRules.currentColor = currentColor;
    }

    public static String getDirection() {
        return direction;
    }

    public static void setDirection(String direction) {
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

    public static boolean isPlayable(UnoCard card){
        updateStatus(); //redundant?? depends on how we're going to use it once we implement the GUI
        //this only happens when the first discarded card is a special one
        if(currentColor.equals(Color.BLACK)){
            return true;
        }
        if (card.getColor().equals(currentColor)
                || card.getNumber().equals(currentNumber)){
            return true;
        }
        if (card.getType().equals(SpecialType.CHANGECOLOUR) ||
                card.getType().equals(SpecialType.PLUS4))
            return true;
        if (deckInUse.getLastDiscardedCard().isSpecial() && card.isSpecial()
                && card.getType().equals(currentType) ){
                return true;
        }
        return false;
    }

    private static void updateStatus(){
        currentColor = deckInUse.getLastDiscardedCard().getColor();
        currentNumber = deckInUse.getLastDiscardedCard().getNumber();
        currentType = deckInUse.getLastDiscardedCard().getType();
    }
}

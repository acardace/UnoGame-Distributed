import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Class representing a Uno Deck
 */
public class UnoDeck implements Serializable{
    public static final int UNOCARDS_NUM = 108;
    private static final int INITIAL_HAND = 7;
    private static final int INITAL_HAND_CAPACITY = 10;
    public ArrayList<UnoCardInDeck> pickedCard= new ArrayList<UnoCardInDeck>();

    //The unoCard object and how many of it there are in the deck
    private HashMap<String, UnoCardInDeck> cards;
    private UnoCard lastDiscardedCard;

    //Creation of a standard Uno Deck
    public UnoDeck(){
        //UNOCARDS_NUM+2 because we set the initial capacity of the hash table
        //to 108 (Uno number of cards) + 2 as to prevent the rehashing of
        //the structure when the structure reaches full capacity
        //the load factor is 1.0
        cards = new HashMap<>(UNOCARDS_NUM+2, (float) 1.0);
        for( Color c: Color.values()){
            if(c != Color.BLACK) {
                for (Number n : Number.values()) {
                    if (n != Number.NONE) {
                        if (n != Number.ZERO) {
                            UnoCardInDeck card = new UnoCardInDeck(c, n, 2);
                            cards.put(card.getCardID(), card);
                        } else {
                            UnoCardInDeck card = new UnoCardInDeck(c, n, 1);
                            cards.put(card.getCardID(), card);
                        }
                    }
                }
                //add the plus2 cards, two for each color
                UnoCardInDeck plusTwoCard = new UnoCardInDeck(c, SpecialType.PLUS2, 2);
                cards.put(plusTwoCard.getCardID(), plusTwoCard);
                //add "reverse" and "skip" cards
                UnoCardInDeck reverseCard = new UnoCardInDeck(c, SpecialType.REVERSE, 2);
                cards.put(reverseCard.getCardID(), reverseCard);
                UnoCardInDeck skipCard = new UnoCardInDeck(c, SpecialType.SKIP, 2);
                cards.put(skipCard.getCardID(), skipCard);
            }
        }
        //add plus4 and change colour cards
        UnoCardInDeck changeColourCard = new UnoCardInDeck(Color.BLACK, SpecialType.CHANGECOLOUR, 4);
        UnoCardInDeck plusFourCard = new UnoCardInDeck(Color.BLACK, SpecialType.PLUS4, 4);
        cards.put(changeColourCard.getCardID(), changeColourCard);
        cards.put(plusFourCard.getCardID(), plusFourCard);
        lastDiscardedCard = drawCard();
        System.out.println(cards.size());
    }

    public UnoCard getLastDiscardedCard(){
        return lastDiscardedCard;
    }

    public void setLastDiscardedCard(UnoCard lastDiscardedCard) {
        this.lastDiscardedCard = lastDiscardedCard;
    }

    public UnoCard drawCard(){
        //randomly draw a card
        Random generator = new Random();
        List<String> keys = new ArrayList<>(cards.keySet());
        String randomKey = keys.get(generator.nextInt(keys.size()));
        UnoCardInDeck randomCard = cards.get(randomKey);
        //update howMany value in the deck
        randomCard.setHowMany(randomCard.getHowMany()-1);
       // cards.put(randomCard.getCardID(), randomCard);
        pickedCard.add(randomCard);
        if(randomCard.getHowMany()<1){
            cards.remove(randomCard.getCardID());
        }
        return randomCard;

    }

    public ArrayList<UnoCard> drawHand(){
        ArrayList<UnoCard> hand = new ArrayList<>(INITAL_HAND_CAPACITY);
        for(int i=0; i<INITIAL_HAND; i++)
            hand.add(drawCard());
        return hand;
    }

    public void resetPickedCard(){
        this.pickedCard.clear();
    };

    public void removeCardFromDeck(ArrayList<UnoCardInDeck> justPicked){
        for (int i = 0; i < justPicked.size(); i++) {
            justPicked.get(i).setHowMany(justPicked.get(i).getHowMany()-1);
            if(justPicked.get(i).getHowMany()<1){
                cards.remove(justPicked.get(i).getCardID());
            }
        }
        System.out.println(cards.size());
    }

    //For debugging purposes
    public void listCards(){
        Collection<UnoCardInDeck> cardArray = cards.values();
        Iterator<UnoCardInDeck> iterator = cardArray.iterator();
        while(iterator.hasNext()){
            UnoCardInDeck card = iterator.next();
            System.out.format("%s %s\n", card.getCardID(), card.getHowMany());
        }
    }


    public class UnoCardInDeck extends UnoCard implements Serializable{
        private int howMany;

        public UnoCardInDeck(Color color, Number number, int howMany){
            super(color, number);
            this.howMany = howMany;
        }

        public UnoCardInDeck(Color color, SpecialType type, int howMany){
            super(color, type);
            this.howMany = howMany;
        }

        public int getHowMany() {
            return howMany;
        }

        public void setHowMany(int howMany) {
            this.howMany = howMany;
        }
    }
}

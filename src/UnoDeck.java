import java.io.Serializable;
import java.util.*;

/**
 * Class representing a Uno Deck
 */
public class UnoDeck implements Serializable{
    public static final int UNOCARDS_NUM = 108;
    private static final int INITIAL_HAND = 7;
    private static final int INITAL_HAND_CAPACITY = 10;
    private int howManyPicked=0;

    //The unoCard object and how many of it there are in the deck
    private Stack<UnoCard> stackDeck;
    private Stack<UnoCard> stackDiscardDeck;

    //Creation of a standard Uno Deck
    public UnoDeck(long seed){
        //UNOCARDS_NUM+2 because we set the initial capacity of the hash table
        //to 108 (Uno number of cards) + 2 as to prevent the rehashing of
        //the structure when the structure reaches full capacity
        //the load factor is 1.0
        stackDiscardDeck= new Stack();
        stackDeck = new Stack<>();
        for( Color c: Color.values()){
            if(c != Color.BLACK) {
                for (Number n : Number.values()) {
                    if (n != Number.NONE) {
                        UnoCard card = new UnoCard(c, n);
                        if (n != Number.ZERO) {
                            stackDeck.push(card);
                            stackDeck.push(card);
                        } else {
                            stackDeck.push(card);
                        }
                    }
                }
                //add the plus2 cards, two for each color
                UnoCard plusTwoCard = new UnoCard(c, SpecialType.PLUS2);
                stackDeck.push(plusTwoCard);
                stackDeck.push(plusTwoCard);
                //add "reverse" and "skip" cards
                UnoCard reverseCard = new UnoCard(c, SpecialType.REVERSE);
                stackDeck.push(reverseCard);
                stackDeck.push(reverseCard);
                UnoCard skipCard = new UnoCard(c, SpecialType.SKIP);
                stackDeck.push(skipCard);
                stackDeck.push(skipCard);
            }
        }
        //add plus4 and change colour cards
        UnoCard changeColourCard = new UnoCard(Color.BLACK, SpecialType.CHANGECOLOUR);
        UnoCard plusFourCard = new UnoCard(Color.BLACK, SpecialType.PLUS4);
        stackDeck.push(plusFourCard);
        stackDeck.push(plusFourCard);
        stackDeck.push(plusFourCard);
        stackDeck.push(plusFourCard);
        stackDeck.push(changeColourCard);
        stackDeck.push(changeColourCard);
        stackDeck.push(changeColourCard);
        stackDeck.push(changeColourCard);
        stackDiscardDeck.push(drawCard());
        this.howManyPicked=0;
        System.out.println(stackDeck.size());
        Collections.shuffle(stackDeck,new Random(seed));

    }

    public UnoCard getLastDiscardedCard(){
        return stackDiscardDeck.peek();
    }

    public void setLastDiscardedCard(UnoCard lastDiscardedCard) {
        stackDiscardDeck.push(lastDiscardedCard);
    }

    public UnoCard drawCard(){
        //regenerate deck if empty
        if(stackDeck.size()==0){
            SwapAndShakeDeck();
        }
        howManyPicked++;
        UnoCard picked= stackDeck.pop();
        return picked;

    }

    public ArrayList<UnoCard> drawHand(){
        ArrayList<UnoCard> hand = new ArrayList<>(INITAL_HAND_CAPACITY);
        for(int i=0; i<INITIAL_HAND; i++)
            hand.add(drawCard());
        return hand;
    }

    public void setHowManyPicked(int n){
        this.howManyPicked=n;
    }

    public int getHowManyPicked(){return this.howManyPicked;}

    public void removeCardFromDeck(int n){
        for (int i = 0; i < n; i++) {
            if(stackDeck.size()==0){
                SwapAndShakeDeck();
            }
            stackDeck.pop();
        }
        System.out.println("DeckSize AfterSync:"+stackDeck.size());
    }

    private void SwapAndShakeDeck(){
        Stack<UnoCard> tmp =new Stack<>();
        while (stackDiscardDeck.size()!=0){
            tmp.push(stackDiscardDeck.pop());
        }
        while (tmp.size()!=0){
            stackDeck.push(tmp.pop());
        }
        stackDiscardDeck.push(stackDeck.pop());
    }

}

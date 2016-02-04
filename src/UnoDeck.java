import java.util.HashMap;

/**
 * Class representing a Uno Deck
 */
public class UnoDeck {
    //The unoCard object and how many of it there are in the deck
    private HashMap<String, UnoCardInDeck> cards;

    //Creation of a standard Uno Deck
    public UnoDeck(){
        for( Color c: Color.values()){
            for(Number n: Number.values()){
                if(n != Number.NONE) {
                    if(n != Number.ZERO) {
                        UnoCardInDeck card = new UnoCardInDeck(c, n, 2);
                        cards.put(card.getCardID(), card);
                    } else {
                        UnoCardInDeck card = new UnoCardInDeck(c, n, 1);
                        cards.put(card.getCardID(), card);
                    }
                }
            }
            for(int i=0; i<2; i++){

            }
        }
        //add "reverse" and "skip" cards

    }

    public class UnoCardInDeck extends UnoCard{
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

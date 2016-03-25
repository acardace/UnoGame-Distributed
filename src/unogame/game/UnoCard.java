package unogame.game;

/**
 * A Uno Card
 */

public class UnoCard {
    private Color color;
    private Number number;
    private boolean special;
    private SpecialType type;

    public UnoCard(){

    }

    public UnoCard(Color color, Number number){
        this.color = color;
        this.number = number;
        this.special = false;
        this.type = SpecialType.NONE;
    }

    public UnoCard(Color color, SpecialType type){
        this.color = color;
        this.type = type;
        this.special = true;
        this.number = Number.NONE;
    }

    public boolean isSpecial(){
        return this.special;
    }

    public Color getColor() {
        return color;
    }

    public Number getNumber() {
        return number;
    }

    public SpecialType getType() {
        return type;
    }

    public String getCardID(){
        return color.toString()+"_"+number.toString()+"_"+type.toString();
    }

    public static String cardID(Color color, Number number){
        return color.toString()+"_"+number.toString()+"_"+ SpecialType.NONE.toString();
    }

    public static String cardID(Color color, SpecialType type){
        return color.toString()+"_"+ Number.NONE.toString()+"_"+type.toString();
    }
}

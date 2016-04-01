package unogame.game;

import java.io.IOException;
import java.io.Serializable;

/**
 * A Uno Card
 */

public class UnoCard implements Serializable{
    private Color color;
    private Number number;
    private boolean special;
    private SpecialType type;

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

    //Serialization implementation
    private void writeObject(java.io.ObjectOutputStream out){
        try{
            out.defaultWriteObject();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream in){
        try{
            in.defaultReadObject();
        }catch(ClassNotFoundException|IOException e){
            e.printStackTrace();
        }
    }

    private void readObjectNoData(){

    }

    public boolean isSpecial(){
        return this.special;
    }

    public boolean isPlus() {
        return type == SpecialType.PLUS2 || type == SpecialType.PLUS4;
    }

    public boolean isSamePlus(UnoCard card){
        return card.getType() == this.type;
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

package auxilary;

import cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dmter on 11/28/2017.
 */
public class CardCombo {

    private String name;
    private int value;
    private int numCardsNeeded;
    private List<Card> definition;
    private List<Card> playersCards;

    public CardCombo(String name, int value, int numCardsNeeded, ArrayList<Card> definition) {
        this.name = name;
        this.value = value;
        this.numCardsNeeded = numCardsNeeded;
        this.definition = definition;
        playersCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNumCardsNeeded() {
        return numCardsNeeded;
    }

    public void setPlayersCards(ArrayList playersCards) {
        this.playersCards = playersCards;
    }

    public List<Card> getDefinition() {
        return definition;
    }

    public void setDefinition(ArrayList<Card> definition) {
        this.definition = definition;
    }

    public void addPlayersCard(Card c) {
        List<Card> cards = getPlayersCards();
        cards.add(c);
        setPlayersCards((ArrayList<Card>) cards);
    }

    public List<Card> getPlayersCards() {
        return playersCards;
    }

    public List<Card> getCardsNeededForCombo() {
        List<Card> defCards = getDefinition();
        List<Card> hasCards = getPlayersCards();

        List<Card> needed = new ArrayList<>();

        for(Card c: defCards){
            if(!hasCards.contains(c)){
                needed.add(c);
            }
        }
        return needed;
    }

    public String toString(){
        return getName();
    }

}
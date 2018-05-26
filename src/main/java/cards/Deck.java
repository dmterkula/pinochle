package cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dmter on 11/23/2017.
 */
public class Deck {

    private ArrayList<Card> cards;

    public Deck(){
        initializeDeck();
        shuffle();
    }

    public void initializeDeck(){
        cards = new ArrayList<Card>();
        String suit = "";
        for(int i = 0; i < 4; i ++){
            if(i == 0){
                suit = "HEARTS";
            }
            else if(i == 1){
                suit = "DIAMONDS";
            }
            else if(i == 2){
                suit = "CLUBS";
            }
            else{
                suit = "SPADES";
            }

            cards.add(new Card(suit, "NINE")); cards.add(new Card(suit, "NINE"));
            cards.add(new Card(suit, "JACK"));  cards.add(new Card(suit, "JACK"));
            cards.add(new Card(suit, "QUEEN"));  cards.add(new Card(suit, "QUEEN"));
            cards.add(new Card(suit, "KING"));  cards.add(new Card(suit, "KING"));
            cards.add(new Card(suit, "TEN"));  cards.add(new Card(suit, "TEN"));
            cards.add(new Card(suit, "ACE"));  cards.add(new Card(suit, "ACE"));
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String toString(){
        return cards.toString();
    }
}

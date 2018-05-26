package cards;

/**
 * Created by dmter on 11/23/2017.
 */
public class Card {
    private String rank;
    private String suit;
    private boolean isPoint;
    private int relativeRank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        relativeRank = getRelativeRank();
        if(relativeRank>3){
            isPoint = true;
        }
        else {
            isPoint = false;
        }
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public String toString(){
        return rank + " of " + suit;
    }

    public int getRelativeRank(){
        int ranking = 0;
        if(rank.equals("NINE")){
            ranking = 1;
        }
        else if(rank.equals("JACK")){
            ranking = 2;
        }
        else if(rank.equals("QUEEN")){
            ranking = 3;
        }
        else if(rank.equals("KING")){
            ranking = 4;
        }
        else if(rank.equals("TEN")){
            ranking = 5;
        }
        else{ // ACE
            ranking = 6;
        }
        return ranking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!getRank().equals(card.getRank())) return false;
        return getSuit().equals(card.getSuit());
    }

}

package game;

import cards.Card;
import cards.Deck;
import players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmter on 11/24/2017.
 */
public class Hand {

    private ArrayList<Player> players;
    private int[] score;
    private int handCounter;
    private Deck deck;
    private List<Card> cat;
    private static final int NUM_PLAYERS = 4;
    private boolean[] didPass = new boolean [NUM_PLAYERS];

    private int scoreDiff;

    public Hand(ArrayList<Player> players, int[] score, int handCounter, Deck deck) {
        this.players = players;
        this.score = score;
        this.handCounter = handCounter;
        this.deck = deck;
        cat = new ArrayList<>();
        didPass[0] = false; didPass[1] = false; didPass[2] = false; didPass[3] = false;
    }

    public Hand(ArrayList<Player> players, int scoreDiff, int handCounter, Deck deck) {
        this.players = players;
        this.handCounter = handCounter;
        if (handCounter == 1) {
            this.scoreDiff = 0;
        } else {
            this.scoreDiff = scoreDiff;
        }
        this.deck = deck;
        deck.shuffle();
    }

    public int getScoreDiff() {
        return scoreDiff;
    }

    public void setScoreDiff(int s) {
        scoreDiff = s;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public int getHandCounter() {
        return handCounter;
    }

    public void setHandCounter(int handCounter) {
        this.handCounter = handCounter;
    }

    public int[] play() {
        deck.shuffle();
        deal();
        commenceBidding();
        return score;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void deal() {
        List<Card> hand1 = deck.getCards().subList(0, 11);
        players.get(0).setHand(hand1);
        List<Card> hand2 = deck.getCards().subList(11, 22);
        players.get(1).setHand(hand2);
        List<Card> hand3 = deck.getCards().subList(22, 33);
        players.get(2).setHand(hand3);
        List<Card> hand4 = deck.getCards().subList(33, 44);
        players.get(3).setHand(hand4);
        cat = deck.getCards().subList(44, 48);

    }

    public List<Card> getCat() {
        return cat;
    }

    public void setCat(List<Card> cat) {
        this.cat = cat;
    }

    public void commenceBidding() {
        int bidderIndex = findFirstBidder();
        Player bidder = players.get(bidderIndex);
        int currentBid = 20;

        while(getNumPassed() > 1){
            if(didPass[bidderIndex] == true){ // if they passed, then skip

            }
            else{ // decide if they bid or not

            }
            boolean didBid = bidder.bidOrNot();
            if(didBid == false){ // then passed, update bids
                didPass[bidderIndex] = true; // if they didn't bid, they passed
            }

            bidderIndex = updateBidderIndex(bidderIndex);

        }
    }

    public int findFirstBidder() {
        int firstBidderIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isDealer()) {
                if (i == NUM_PLAYERS-1) {
                    firstBidderIndex = 0;
                } else {
                    firstBidderIndex = i+1;
                }
            }
        }
        return firstBidderIndex;
    }

    private int getNumPassed(){
        int numPassed = 0;
        for(int i =0; i < didPass.length; i++){
            if(didPass[i] == true){
                numPassed++;
            }
        }
        return numPassed;
    }

    private int updateBidderIndex(int bidderIndex){
        if(bidderIndex == NUM_PLAYERS-1){
            bidderIndex = 0;
        }
        else{
            bidderIndex ++;
        }
        return bidderIndex;
    }

}

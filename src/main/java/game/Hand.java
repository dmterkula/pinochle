package game;

import auxilary.MeldCounter;
import cards.Card;
import cards.Deck;
import players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private int[] playerBidStates = new int[NUM_PLAYERS];
    private int[] bidCount = new int[NUM_PLAYERS];
    private String trump;

    private int scoreDiff;

    public Hand(ArrayList<Player> players, int[] score, int handCounter, Deck deck) {
        this.players = players;
        this.score = score;
        this.handCounter = handCounter;
        this.deck = deck;
        cat = new ArrayList<>();
        playerBidStates[0] = -1;
        playerBidStates[1] = -1;
        playerBidStates[2] = -1;
        playerBidStates[3] = -1;
        bidCount[0] = 0;
        bidCount[1] = 0;
        bidCount[2] = 0;
        bidCount[3] = 0;
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
        System.out.println("The 1st bidder is " + bidder.getName());
        int counter = 0;
        while (getNumPassed() < NUM_PLAYERS-1) {
            counter ++;
            bidder = players.get(bidderIndex);
            if (playerBidStates[bidderIndex] == 0) { // if they passed, then skip
                bidderIndex = updateBidderIndex(bidderIndex);
                continue;
            } else { // decide if they bid or not
                System.out.println(bidder.getName() + " is the bidder. " + " To bid, must wager atleast " + (currentBid+1));
                int partner = getPartnerIndex(bidderIndex);
                int partnerBidCount =  bidCount[partner];
                boolean partnerHasPassed;
                boolean givenMeldBid = false;
                if(playerBidStates[partner] == 0){
                    partnerHasPassed = true;
                }
                else{
                    partnerHasPassed = false;
                    if(playerBidStates[partner] == 2){
                        givenMeldBid = true;
                    }
                }
                int bidChoice;
                if(bidder.isHuman()){
                    bidChoice = getHumanBid();
                }
                else {
                    bidder.pickTrump();
                    bidChoice = bidder.bidOrNot(currentBid, partnerBidCount, partnerHasPassed, givenMeldBid);
                }

                playerBidStates[bidderIndex] = bidChoice;
                if (bidChoice == 0) {
                    // pass, nothing to update
                } else if (bidChoice == 1) {
                    // bid
                    bidCount[bidderIndex] = bidCount[bidderIndex] + 1; // updateBidCount
                } else { // meldBid
                    bidCount[bidderIndex] = bidCount[bidderIndex] + 1; // updateBidCount
                }

            }
            currentBid += playerBidStates[bidderIndex]; // adds one if they bid, adds two for meld bid.
            if(playerBidStates[bidderIndex] == 0){
                System.out.println(bidder.getName() + " passed ");
            }
            else{
                System.out.println(bidder.getName() + " bid " + currentBid);
            }

            bidderIndex = updateBidderIndex(bidderIndex);

        }

        if(counter <= 3){
            System.out.println(players.get(findBidWinner()).getName() + " was dropped, only have to make 20!");
        }
        else {
            System.out.println(players.get(findBidWinner()).getName() + " won the bid at " + currentBid);
        }



    }

    public int findFirstBidder() {
        int firstBidderIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isDealer()) {
                if (i == NUM_PLAYERS - 1) {
                    firstBidderIndex = 0;
                } else {
                    firstBidderIndex = i + 1;
                }
            }
        }
        return firstBidderIndex;
    }

    private int getNumPassed() {
        int numPassed = 0;
        for (int i = 0; i < playerBidStates.length; i++) {
            if (playerBidStates[i] == 0) {
                numPassed++;
            }
        }
        return numPassed;
    }

    private int updateBidderIndex(int bidderIndex) {
        if (bidderIndex == NUM_PLAYERS - 1) {
            bidderIndex = 0;
        } else {
            bidderIndex++;
        }
        return bidderIndex;
    }

    private int getPartnerIndex(int i) {
        int partnerIndex;
        if (i == 0 || i == 1) {
            partnerIndex = i + 2;
        } else { // i = 2 or 3;
            partnerIndex = i - 2;
        }

        return partnerIndex;
    }

    private int getHumanBid(){
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        int choice = 0;
        while (!isValid) {
            choice = in.nextInt();
            if(choice == 0 || choice == 1 || choice ==2){
                isValid = true;
            }
        }
        return choice;
    }

    private int findBidWinner(){
        int winnerIndex = 0;
        for(int i = 0; i < NUM_PLAYERS; i++){
            if(playerBidStates[i] != 0){
                winnerIndex = i;
            }
        }
        return winnerIndex;
    }

    public void postBidPlay(){
        int bidWinnerIndex = findBidWinner();
        Player bidWinner = players.get(bidWinnerIndex);
        displayCat();
        bidWinner.getHand().addAll(cat);
        if(bidWinner.isHuman()){
            trump = getHumanTrump();
        }
        else {
            trump = bidWinner.pickTrump();
        }

        System.out.println(bidWinner.getName() + " picked " + trump + " as trump");
        System.out.print(bidWinner.getName() + "'s meld combinations: ");
        MeldCounter mc = new MeldCounter(bidWinner.getHand());
        int meld = mc.countMeldFromTrump(trump);
        if(mc.getNineCounter() > 0) {
            System.out.println(bidWinner.getName() + " also has " + mc.getNineCounter() + " nines");
        }
        System.out.println(bidWinner.getName() + " has " + meld);

    }

    public void displayCat(){
        System.out.println("In the cat there is: ");
        for(Card c: cat){
            System.out.print(c + ", ");
        }
        System.out.println();
    }

    private String getHumanTrump(){
        Scanner in = new Scanner(System.in);
        boolean validTrump = false;
        while (!validTrump) {
            System.out.println("Name trump: (HEARTS, DIAMONDS, CLUBS, SPADES)");
            trump = in.next();
            if(trump.equalsIgnoreCase("HEARTS") || trump.equalsIgnoreCase("DIAMONDS") || trump.equalsIgnoreCase("CLUBS")
                    || trump.equalsIgnoreCase("SPADES")){
                validTrump = true;
            }
        }

        return trump;
    }


}

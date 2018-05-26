package game;

import cards.Deck;
import players.Player;

import java.util.ArrayList;

/**
 * Created by dmter on 11/23/2017.
 */
public class Game {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private int handCounter;
    private int [] score;
    private Deck deck;
    private ArrayList<Player> players;

    public Game(Player player1, Player player2, Player player3, Player player4){
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        handCounter = 0;
        player1.setDealer(true);
        score = new int[]{0,0};
        this.deck = new Deck();
        players = new ArrayList<Player>();
        players.add(player1); players.add(player2); players.add(player3); players.add(player4);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public Player getPlayer4() {
        return player4;
    }

    public void setPlayer4(Player player4) {
        this.player4 = player4;
    }

    public int getHandCounter() {
        return handCounter;
    }

    public void setHandCounter(int handCounter) {
        this.handCounter = handCounter;
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public Deck getDeck() {
        return deck;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int [] playGame(){
        while(handCounter < 4){
            if(handCounter > 0){
                players.get(handCounter-1).setDealer(false);
            }
            players.get(handCounter).setDealer(true);
            Hand hand = new Hand(players, score, handCounter, deck);
            score = hand.play();
            handCounter ++;
        }
        return score;
    }
}

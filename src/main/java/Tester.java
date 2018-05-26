import cards.Card;
import game.Game;
import game.Hand;
import players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dmter on 11/24/2017.
 */
public class Tester {

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    public static void main(String [] args){
        readInPlayer();
        Game game = new Game(player1, player2, player3, player4);
        Hand hand =  new Hand(game.getPlayers(), game.getScore(), 0,game.getDeck());
        hand.deal();
        for(Player p: hand.getPlayers()){
            p.sortHand();
            System.out.println(p.getName() + ": " + p.getHand());
        }
        System.out.println("Cat: " + hand.getCat());

        for(Player p : game.getPlayers()){
            System.out.println(p. getName() + " has " + p.countMeld() + " meld");
        }

        String trump = userInputTrump();
        player1.setTrump(trump);
        // String aiTrump = player1.pickTrump();
        //System.out.println("AI recommends you pick " + aiTrump + " as trump");
        double estimatedPointsTaken = player1.estimatePointsTaken(player1.getHand(), trump);
        //double aiEstimatedPointsTaken = player1.estimatePointsTaken(player1.getHand(), aiTrump);
        System.out.println("estimated points taken with your choice of " + trump + " as trump = " + estimatedPointsTaken);
        // System.out.println("estimated points taken AI choice of " + aiTrump + " as trump = " + aiEstimatedPointsTaken);
        System.out.println("meld after trump = " + player1.getMeldCounter().countMeldFromTrump(trump));

        System.out.println("Bid? = " + player1.bidOrNot(21, 0, true, false));


    }

    public static void readInPlayer(){
        System.out.println("Please enter your name");
        Scanner in = new Scanner(System.in);
        String name = in.next();
        player1 = new Player(name);
        for(int i = 0; i < 3; i++){
            if(i%2 == 0){
                System.out.println("What should opposing player be called?");
            }
            else{
                System.out.println("What should your teammate be called?");
            }
            String newName = in.next();
            if(i == 0){
                player2 = new Player(newName);
            }
            else if(i == 1){
                player3 = new Player(newName);
            }
            else{
                player4 = new Player(newName);
            }

        }


    }

    public static String userInputTrump(){
        String trump = "";
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

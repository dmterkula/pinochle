import cards.Card;
import cards.Deck;
import filehandlers.CSVWriter;
import game.Game;
import game.Hand;
import players.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dmter on 12/21/2017.
 */
public class HandSimulation {

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    private static Hand hand;
    private static String fileName = "data.csv";
    private static String absPath = "C:\\Users\\dmter\\Desktop\\pinochle\\src\\main\\resources\\data.csv";
    private static String absPathCards = "C:\\Users\\dmter\\Desktop\\pinochle\\src\\main\\resources\\cards.txt";


    public static void main(String [] args) throws IOException{
        boolean quit = false;
        Scanner in = new Scanner(System.in);
        while(!quit){
            System.out.println("Continue? [y or anything else] ");
            String choice = in.next();
            if(choice.equals("y")){
                createPlayers();
                createHand();
                Object[] inputs = gatherData();
                writeInputsToFile(inputs);
                writeCardsToFile(hand.getPlayers().get(0).getHand());
            }
            else{ // quit
                System.out.println("you quit, thanks for training me");
                quit = true;
            }

        }

    }

    public static void createPlayers(){
        player1 = new Player("You");
        player2 = new Player("AI1");
        player3 = new Player("AI2");
        player4 = new Player("AI3");
    }

    public static void createHand(){
        ArrayList<Player> players = new ArrayList<>(); players.add(player1); players.add(player2); players.add(player3);
        players.add(player4);
        int scoreDiff = ThreadLocalRandom.current().nextInt(0, 41); // score diff of 0 to 40.
        double plusOrMinus = Math.random();
        if(plusOrMinus > .5){
            scoreDiff *= -1;
        }
        int handCounter = ThreadLocalRandom.current().nextInt(1, 5); // hand counter 1 to 4
        Deck deck = new Deck();
        hand = new Hand(players, scoreDiff, handCounter, deck);
        hand.deal();
    }

    public static Object[] gatherData(){
        int meld = hand.getPlayers().get(0).countMeld();
        boolean bid;
        int lastBid = generateLastBid();
        boolean partnerBid = false;

        double doesPartnerBid =  Math.random();
        if(lastBid >= 21) {
            if (doesPartnerBid < .5) {
                partnerBid = true;
            }
        }
        int partnerBidCount = 0;
        if(partnerBid){
            partnerBidCount = 1;
        }
        boolean partnerMeldBid = false;
        if(partnerBid){
            double meldBidChance = Math.random();
            if(meldBidChance <.05){
                partnerMeldBid = true;
            }
        }

        boolean partnerHasPassed = true;
        if(!partnerMeldBid) {
            if (partnerBid) {
                double passProb = Math.random();
                if (lastBid <= 24) {
                    if (passProb <=.5) {
                        partnerHasPassed = false;
                    }
                } else if (lastBid <= 27) {
                    if (passProb < 11/16) {
                        partnerHasPassed = false;
                    }
                } else {
                    if (passProb <= 10/16) {
                        partnerHasPassed = false;
                    }
                }
            }
        }
        Scanner in = new Scanner(System.in);
        hand.getPlayers().get(0).sortHand();
        System.out.println(hand.getPlayers().get(0).getHand());
        System.out.println("score diff is: " + hand.getScoreDiff() + ". this is hand number " + hand.getHandCounter() + " hand");
        System.out.println("you have " + meld + " meld. Current Bid is: " + lastBid);
        System.out.println("you partner has bid: " + partnerBid + " partner meldBid: " + partnerMeldBid);
        System.out.println("partner has passed: " + partnerHasPassed);
        String trump = "";
        boolean validInput = false;
        while(!validInput){
            System.out.println("What is your trump?");
            trump = in.next();
            if(trump.equalsIgnoreCase("hearts") || trump.equalsIgnoreCase("diamonds")||
                    trump.equalsIgnoreCase("clubs") || trump.equalsIgnoreCase("spades")){
                validInput = true;
            }
            else{
                System.out.println("please enter valid trump suit (hearts, diamonds, spades, clubs");
            }
        }

        hand.getPlayers().get(0).setTrump(trump);
        int meldAfterTrump = hand.getPlayers().get(0).getMeldCounter().countMeldFromTrump(trump);
        System.out.println("meld after trump: " + meldAfterTrump);
        double handStrength = player1.estimatePointsTaken(hand.getPlayers().get(0).getHand(), trump);
        boolean aiBidOrNot = player1.bidOrNot(lastBid, partnerBidCount, partnerHasPassed, partnerMeldBid);

        Object[] inputs = new Object[10];
        inputs[0] = meldAfterTrump; inputs[1] = handStrength; inputs [2] = aiBidOrNot; inputs[3] = partnerBid; inputs[4] = partnerMeldBid;
        inputs[5] = partnerHasPassed; inputs[6] = hand.getHandCounter(); inputs [7] = hand.getScoreDiff(); inputs[8] = lastBid; inputs[9] = null;

        return inputs;

    }

    public static int generateLastBid(){
        double rand = Math.random();
        int lastBid = 21;
        if(rand > .15){
             lastBid = ThreadLocalRandom.current().nextInt(21, 25);
        }
        else if( rand > .07){
            lastBid = ThreadLocalRandom.current().nextInt(25, 28);
        }
        else if(rand > .03){
             lastBid = ThreadLocalRandom.current().nextInt(28, 31);
        }
        else{
            lastBid = ThreadLocalRandom.current().nextInt(31, 35);
        }

        return lastBid;
    }

    public static void writeInputsToFile(Object[] inputs) throws IOException{
        Scanner in = new Scanner(System.in);
        System.out.println("Bid or Pass? pass [0]   bid[1]    meldBid[2]");
        boolean validInput =  false;
        int choice = 0;
        String pick = "";
        while(!validInput){
            choice = in.nextInt();
            if(choice == 0 || choice == 1 || choice == 2){
                validInput = true;
            }
            else{
                System.out.println("please enter valid input");
            }
            if(validInput){
                if(choice == 0){
                    pick = "pass";
                }
                else if(choice == 1){
                    pick = "bid";
                }
                else{
                    pick = "meld bid";
                }
            }

        }
        inputs[9] = pick;

        CSVWriter writer = new CSVWriter(fileName);
        writer.writeToCSV(inputs);

    }

    public static void writeCardsToFile(List<Card> hand){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            int counter = 0;
            fw = new FileWriter(absPathCards, true);
            bw = new BufferedWriter(fw);
            String val = "";
            for(Card c: hand){

                val = c.toString();
                bw.write(val);
                if(counter != hand.size()-1){
                    bw.write(",");
                }
               counter++;
            }

            bw.newLine();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }

}

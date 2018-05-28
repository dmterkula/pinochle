package players;

import auxilary.CardCombo;
import auxilary.MeldCounter;
import cards.Card;

import java.util.*;

/**
 * Created by dmter on 11/23/2017.
 */
public class Player {
    private String name;
    private static int idCounter = 0;
    private int id;
    private int teammateId;
    private List<Card> hand;
    private boolean isDealer;
    private MeldCounter meldCounter;
    private double handStrength = 0.0;
    private String trump;
    private boolean isHuman;

    public Player(String name, boolean isHuman) {
        id = idCounter;
        this.name = name;
        hand = new ArrayList<Card>();
        isDealer = false;
        idCounter ++;
        teammateId = determineTeammate(id);
        meldCounter = new MeldCounter(hand);
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeammateId() {
        return teammateId;
    }

    public void setTeammateId(int teammateId) {
        this.teammateId = teammateId;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    public boolean isHuman(){
        return isHuman;
    }

    public int determineTeammate(int id){
        int teammateId = 0;
        if(id > 1){
            if(id == 2){
                teammateId = 0;
            }
            else{
                teammateId = 1;
            }
        }
        else{
            if(id == 0){
                teammateId = 2;
            }
            else{
                teammateId = 3;
            }
        }
        return teammateId;
    }

    public ArrayList<Card> findTrump(String trump){
        ArrayList<Card> playersTrump = new ArrayList<Card>();
        for(Card c : hand){
            if(c.getSuit().equalsIgnoreCase(trump)){
                playersTrump.add(c);
            }
        }
        return  playersTrump;
    }

    public void sortHand(){
        hand.sort(Comparator.comparing(Card::getSuit).thenComparing(Card::getRelativeRank).reversed());
    }

    public double getHandStrength() {
        return handStrength;
    }

    public void setHandStrength(double handStrength) {
        this.handStrength = handStrength;
    }

    public String getTrump() {
        return trump;
    }

    public void setTrump(String trump) {
        this.trump = trump;
    }

    public int countMeld(){
        meldCounter = new MeldCounter(getHand());
        return meldCounter.getMeld();
    }

    public int countAces(){
        int count = 0;
        for(Card c: hand){
            if(c.getRank().equalsIgnoreCase("ACE")){
                count ++;
            }
        }
        return count;
    }

    public String pickTrump(){ // need to calculate meld diff for each suit based on trump;
        String trump = "";

        double heartsPoints = estimatePointsTaken(getHand(), "HEARTS");
        double diamondPoints = estimatePointsTaken(getHand(), "DIAMONDS");
        double clubsPoints = estimatePointsTaken(getHand(), "CLUBS");
        double spadesPoints = estimatePointsTaken(getHand(), "SPADES");

        int heartsMeldAdded = meldCounter.countMeldFromTrump("HEARTS");
        int diamondsMeldAdded = meldCounter.countMeldFromTrump("DIAMONDS");
        int clubsMeldAdded = meldCounter.countMeldFromTrump("CLUBS");
        int spadesMeldAdded = meldCounter.countMeldFromTrump("SPADES");

        heartsPoints += heartsMeldAdded;
        diamondPoints += diamondsMeldAdded;
        clubsPoints += clubsMeldAdded;
        spadesPoints += spadesMeldAdded;

        if((heartsPoints >= diamondPoints) && (heartsPoints >= clubsPoints) && (heartsPoints >= spadesPoints)){
            trump = "HEARTS";
        }
        else if((diamondPoints >= heartsPoints)  && (diamondPoints >= clubsPoints) && (diamondPoints >= spadesPoints)){
            trump = "DIAMONDS";
        }
        else if((clubsPoints >= heartsPoints) && (clubsPoints >= diamondPoints) && (clubsPoints >= spadesPoints)){
            trump = "CLUBS";
        }
        else{
            trump = "SPADES";
        }
       setTrump(trump);
        return trump;
    }

    public double estimatePointsTaken(List<Card> hand, String trump){
        double estimatedPoints = 0.0;
        int numInSuit;

        int numHearts = meldCounter.getHearts().size();
        int numSpades = meldCounter.getSpades().size();
        int numDiamonds = meldCounter.getDiamonds().size();
        int numClubs = meldCounter.getClubs().size();

        HashMap<Card, Double> pointEval = new HashMap<Card, Double>();

        for(Card c: hand){
            if(c.getSuit().equalsIgnoreCase("HEARTS")){
                numInSuit = numHearts;
            }
            else if(c.getSuit().equalsIgnoreCase("DIAMONDS")){
                numInSuit = numDiamonds;
            }
            else if(c.getSuit().equalsIgnoreCase("CLUBS")){
                numInSuit = numClubs;
            }
            else{
                numInSuit = numSpades;
            }
            boolean isTrump = false;
            if(c.getSuit().equalsIgnoreCase(trump)){
                isTrump = true;
            }
            double val = estimateCardValueAdded(c, isTrump, numInSuit);
            pointEval.put(c, val);
            estimatedPoints += val;

        }

        //System.out.println(Arrays.asList(pointEval));

        if(estimatedPoints > getHandStrength()){
            setHandStrength(estimatedPoints);
        }
        return  estimatedPoints;
    }

    public double estimateCardValueAdded(Card c, boolean isTrump, int numInSuit){
        double estimateValueAdded = 0.0;

        if(isTrump) {
            if (c.getRank().equalsIgnoreCase("ACE")) {
                estimateValueAdded = 2.5;
                if (numInSuit >= 5) {
                    estimateValueAdded = 3.0; //last trick likely
                }
            } else if (c.getRank().equalsIgnoreCase("TEN")) {
                estimateValueAdded = 2.0; // most likely brings in its own point and another one
                if (numInSuit >= 5) {
                    estimateValueAdded = 2.5; // just as good as an ace
                }
            } else if (c.getRank().equalsIgnoreCase("KING")) {
                estimateValueAdded = 2.0; //
            }
            else{ // treat all non point trump the same.
                estimateValueAdded = 1.0;
                if(numInSuit >= 5){
                    estimateValueAdded = 1.5;
                }
            }
        }

        else{// not trump
            if(c.getRank().equalsIgnoreCase("ACE")){
                estimateValueAdded = 2.0; // can possibly expect to get point from partner or opposing player
            }
            else if(c.getRank().equalsIgnoreCase("TEN")){
                estimateValueAdded = 1.25; // may take trick on can through on parner's ace or trick
            }
            else if(c.getRank().equalsIgnoreCase("KING")){
                estimateValueAdded = .5; // half the time your partner may get it or may rarely get trick on its own.
            }
            else{
                estimateValueAdded = 0.0;
            }


        }
        return estimateValueAdded;
    }

    public MeldCounter getMeldCounter() {
        return meldCounter;
    }

    public int bidOrNot(int lastBid, int partnerBidCount, boolean partnerHasPassed, boolean givenMeldBid){

        int bidChoice = 0;
        int nextBid = lastBid ++;
        double currentHandValue = getHandStrength() + getMeldCounter().countMeldFromTrump(getTrump() + pointsGainedByBurying());
        double pointEstimateIncludingPartner = currentHandValue;
        double oddsOfGettingNecessaryMeld;

        // factor in partner behavior

        if (partnerBidCount > 0 && givenMeldBid) { // meld bid given
            pointEstimateIncludingPartner += 10; // expect partner to have 10 meld;
        } else if (partnerBidCount > 0 && !givenMeldBid) { // bid but, no meld bid
            pointEstimateIncludingPartner += 2.5; // assume they will contribute 4 points b/c they bid on something for a reason.
        }
        else { // have not bid
            pointEstimateIncludingPartner += 1.5;// assume they have two meld
        }

        //System.out.println("handStrength: " + getHandStrength() + "  meldCountAfterTrump: " + getMeldCounter().countMeldFromTrump(trump) + "  points gained by burying: " + pointsGainedByBurying());
        //System.out.println("estimated points after trump, hand strength, partner points, and burying potential: " + pointEstimateIncludingPartner);

        if(pointEstimateIncludingPartner >= nextBid){
             bidChoice = 1;
             oddsOfGettingNecessaryMeld = 1.0;
        }
        else {// need help from cat, evaluate odds
             oddsOfGettingNecessaryMeld = calculateOddsOfGettingXMeld(nextBid-pointEstimateIncludingPartner);
            if(oddsOfGettingNecessaryMeld >=.5){
                bidChoice = 1;
            }
        }

        // check for meld bid scenario
        // cant check score or hand count here to influence meld bid decision
        if(getMeldCounter().getMeld() >= 10 && oddsOfGettingNecessaryMeld < .5 &&  !partnerHasPassed && !givenMeldBid ){ // could be edited to allow a meld bid to be
                                                                                    // to be given back
            bidChoice = 2;
        }



        return bidChoice;
    }

    public double pointsGainedByBurying() {
        double pointsAdded = 0.0;
        List<Card> trumpCards = findTrump(trump);
        List<List<Card>> cards = sortHandByShortestSuit();
        if (cards.get(0).size() == 0) { // have a shorted suit, high incentive to bid
            if (trumpCards.size() > 5) { // and have lots of trump
                pointsAdded += 5; // get all but one card in that suit
            }
            else if(trumpCards.size() == 5){
                pointsAdded += 4;
            }
            else if(trumpCards.size() == 4){
                pointsAdded += 3;
            }

        }
        else if(cards.get(0).size() == 1){ // have almost shorted suit
            if (trumpCards.size() > 5) { // and have lots of trump
                pointsAdded += 4; // get all but one card in that suit
            }
            else if(trumpCards.size() == 5){
                pointsAdded += 3;
            }
            else if(trumpCards.size() == 4){
                pointsAdded += 1.5;
            }
        }
        return pointsAdded;
    }

    public List<List<Card>> sortHandByShortestSuit(){
        List<List<Card>> cardsInHand = new ArrayList<>();
        cardsInHand.add(meldCounter.getHearts());
        cardsInHand.add(meldCounter.getClubs());
        cardsInHand.add(meldCounter.getDiamonds());
        cardsInHand.add(meldCounter.getSpades());
        //hand.sort(Comparator.comparing(List::size));
        for(int i = 0; i < cardsInHand.size()-1; i++){
            int minIndex = i;
            for(int j = i+1; j < cardsInHand.size(); j++){
                if(cardsInHand.get(j).size() < cardsInHand.get(minIndex).size()){
                    minIndex = j;
                }
            }
            List<Card> temp = cardsInHand.get(minIndex);
            cardsInHand.set(minIndex, cardsInHand.get(i));
            cardsInHand.set(i, temp);
        }

        return cardsInHand;

    }


    public double calculateOddsOfGettingXMeld(double neededMeld){
        // need to calculate all possible outcomes/permutations of meld possibilites. could get marriage and something which widens window
        // what would push you over the needed meld. maybe just do any two combinations to

        double oddsOfMakingit = 0.0;
        double needOneCardChance = ((1.0/37) + (1.0/36) + (1.0/35) + (1.0/34)) *2;
        double needTwoCardsChance = ((1.0/37) *(1.0/36)) * 6 * 2;
        int numAces = 8-countAces();
        double oddsOneAce = (numAces/37.0) + (numAces/36.0) + (numAces/35.0) + (numAces/34.0);
        int addedMeld = 0;


        //marriage
        //01234567
        List<CardCombo> possibleCombinations = meldCounter.getMeldCombinations();
        HashMap<String, Integer> stateOfCombinations = meldCounter.getStateOfCombinations();
        List<CardCombo> combosGreaterThanNeededMeld = new ArrayList<>();
        List<CardCombo> combosLessThanNeededMeld = new ArrayList<>();

        ArrayList<Card> aceDef = new ArrayList<>(); aceDef.add(new Card("Not A suit", "ACE")); // add ace to def, can be any.
                                                                            // use not a suit so it cant find a card and thus always need 1.
        CardCombo ace = new CardCombo("ACE", 2, 1, aceDef);
        ace.setPlayersCards(new ArrayList());
        possibleCombinations.add(ace);
        //account for marriage in trump change in value

        for(CardCombo cardCombo: possibleCombinations){
            if(trump.equalsIgnoreCase("HEARTS")){
                if(cardCombo.getName().equalsIgnoreCase("marriage hearts")){
                    cardCombo.setValue(4);
                }
            }
            else if(trump.equalsIgnoreCase("CLUBS")){
                if(cardCombo.getName().equalsIgnoreCase("marriage clubs")){
                    cardCombo.setValue(4);
                }
            }
            else if(trump.equalsIgnoreCase("diamonds")){
                if(cardCombo.getName().equalsIgnoreCase("marriage diamonds")){
                    cardCombo.setValue(4);
                }
            }
            else{// trump is spades
                if(cardCombo.getName().equalsIgnoreCase("marriage spades")){
                    cardCombo.setValue(4);
                }
            }
        }

        //System.out.println("neededMeld : " + neededMeld);
        HashSet<Card> usedCards = new HashSet<>();

        //System.out.print("{");
        //for(CardCombo c: possibleCombinations){
         //   System.out.print("(" + c + ", " +c.getCardsNeededForCombo().size() + ")");
        //}
        //System.out.print("}");
        //System.out.println();


        // split into two lists based on whether meld added is greater than or less than the needed meld to make bid
        for(CardCombo combo: possibleCombinations) {
            if (combo.getName().equals("PINOCHLE")){
                if(combo.getPlayersCards().size() != 4){
                    if(combo.getPlayersCards().size() >= 2){
                        if(neededMeld <= 30){ // if already have 2 cards for pinochle... look for double
                            combo.setValue(30);
                            combosGreaterThanNeededMeld.add(combo);
                        }
                        else{
                            combo.setValue(30);
                            combosLessThanNeededMeld.add(combo);
                        }
                    }
                    else{ // looking for single pinochle
                        if(neededMeld <= 4){
                            combosGreaterThanNeededMeld.add(combo);
                        }
                        else{
                            combosLessThanNeededMeld.add(combo);
                        }
                    }
                }

            }
            else { // check to see if meld added of combo is greater than needed or not
                if(combo.getCardsNeededForCombo().size() > 0) { // dont bother checking for a combo that you already have points for
                    if (combo.getValue() >= neededMeld) { // add to combos Greater than
                        combosGreaterThanNeededMeld.add(combo);
                    } else {
                        combosLessThanNeededMeld.add(combo);
                    }
                }
            }
        }

        /// now that combos are split, calculate odds
        for(CardCombo cardCombo : combosGreaterThanNeededMeld){
            if(cardCombo.getCardsNeededForCombo().size() == 1){
                if(!usedCards.contains(cardCombo.getCardsNeededForCombo().get(0))){ // if need one card and havent used that one card yet
                    if(cardCombo.getName().equalsIgnoreCase("ace")){
                        oddsOfMakingit += oddsOneAce;
                    }
                    else{
                        oddsOfMakingit += needOneCardChance;
                    }
                    //System.out.println("combo one card away: "+ cardCombo.getName());
                    //System.out.println("odds running total = " + oddsOfMakingit);

                }
            }
            else{ // need more than one card
                if(cardCombo.getCardsNeededForCombo().size() == 2 ){ // dont bother calculating three for some meld combo
                    if(!usedCards.contains(cardCombo.getCardsNeededForCombo().get(0)) || !usedCards.contains(cardCombo.getCardsNeededForCombo().get(1))){
                        oddsOfMakingit += needTwoCardsChance;
                        //System.out.println("combo two cards away: "+ cardCombo.getName());
                        //System.out.println("odds running total = " + oddsOfMakingit);
                        usedCards.addAll(cardCombo.getCardsNeededForCombo());
                    }
                }
            }
        }


        // oddsOfMakingIt is added to the results found here
        // check to see if any combomination of two different chances will  get you the necessary meld and if so calculate the odds.
        for(int i = 0; i < combosLessThanNeededMeld.size()-1; i++) {
            usedCards = new HashSet<>(); // reset cards used for each combo j.
            for (int j = i+1; j < combosLessThanNeededMeld.size()-1; j++) {
                if (!usedCards.contains(combosLessThanNeededMeld.get(j))) { // if havent used card....
                    if (combosLessThanNeededMeld.get(i).getCardsNeededForCombo().size() == 1
                            && combosLessThanNeededMeld.get(j).getCardsNeededForCombo().size() == 1) { // if need one card for each combo
                        if (combosLessThanNeededMeld.get(i).getValue() + combosLessThanNeededMeld.get(j).getValue() >= neededMeld) {
                            // if the summed value of i and j is greater then needed meld...
                            if(combosLessThanNeededMeld.get(i).getName().equalsIgnoreCase("ace") || combosLessThanNeededMeld.get(j).getName().equalsIgnoreCase("ace")){
                                oddsOfMakingit += needOneCardChance * oddsOneAce; // add to running odds total
                            }
                            else{
                                oddsOfMakingit += (needOneCardChance * needOneCardChance);  // add to running odds total, not an ace combo
                            }

                            //System.out.println(combosLessThanNeededMeld.get(i) + " AND " + combosLessThanNeededMeld.get(j));
                            //System.out.println(oddsOfMakingit);
                            usedCards.addAll(combosLessThanNeededMeld.get(j).getCardsNeededForCombo());
                        }
                    }
                }
            }
        }


        //System.out.println("odds of making it: " + oddsOfMakingit);
        return oddsOfMakingit;
    }

}

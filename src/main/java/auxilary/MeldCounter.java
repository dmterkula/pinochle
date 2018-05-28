package auxilary;

import cards.Card;
import java.util.*;

/**
 * Created by dmter on 11/24/2017.
 */
public class MeldCounter {

    private List<Card> hand;
    private List<Card> hearts;
    private List<Card> diamonds;
    private List<Card> clubs;
    private List<Card> spades;
    private int meld;
    private int meldFromTrump;
    private int trumpNineCounter;

    // build sets of combinations.

    private List<CardCombo> meldCombinations;
    private HashMap<String, Integer> stateOfCombinations;



    private String trump;

    public MeldCounter(List<Card> hand){
        this.hand = hand;
        hearts = getHearts();
        spades = getSpades();
        clubs = getClubs();
        diamonds = getDiamonds();
        meld = 0;
        meldFromTrump = 0;
        trumpNineCounter = 0;
        meldCombinations = new ArrayList<>();
        stateOfCombinations = new HashMap<>();
        countMeld();
    }


    public void countMeld(){
        meld = 0;

        // definitions
        ArrayList<Card> hundredAcesDefinition = new ArrayList<>();
        hundredAcesDefinition.add(new Card("HEARTS", "ACE"));
        hundredAcesDefinition.add(new Card("CLUBS", "ACE"));
        hundredAcesDefinition.add(new Card("SPADES", "ACE"));
        hundredAcesDefinition.add(new Card("DIAMONDS", "ACE"));

        ArrayList<Card> kingsAroundDefinition = new ArrayList<>();
        kingsAroundDefinition.add(new Card("HEARTS", "KING"));
        kingsAroundDefinition.add(new Card("CLUBS", "KING"));
        kingsAroundDefinition.add(new Card("SPADES", "KING"));
        kingsAroundDefinition.add(new Card("DIAMONDS", "KING"));

        ArrayList<Card> queensAroundDefinition = new ArrayList<>();
        queensAroundDefinition.add(new Card("HEARTS", "QUEEN"));
        queensAroundDefinition.add(new Card("CLUBS", "QUEEN"));
        queensAroundDefinition.add(new Card("SPADES", "QUEEN"));
        queensAroundDefinition.add(new Card("DIAMONDS", "QUEEN"));

        ArrayList<Card> jacksAroundDefinition = new ArrayList<>();
        jacksAroundDefinition.add(new Card("HEARTS", "JACK"));
        jacksAroundDefinition.add(new Card("CLUBS", "JACK"));
        jacksAroundDefinition.add(new Card("SPADES", "JACK"));
        jacksAroundDefinition.add(new Card("DIAMONDS", "JACK"));

        ArrayList<Card> runHeartsDefinition = new ArrayList<>();
        runHeartsDefinition.add(new Card("HEARTS", "ACE"));
        runHeartsDefinition.add(new Card("HEARTS", "TEN"));
        runHeartsDefinition.add(new Card("HEARTS", "KING"));
        runHeartsDefinition.add(new Card("HEARTS", "QUEEN"));
        runHeartsDefinition.add(new Card("HEARTS", "JACK"));

        ArrayList<Card> runDiamondsDefinition = new ArrayList<>();
        runDiamondsDefinition.add(new Card("DIAMONDS", "ACE"));
        runDiamondsDefinition.add(new Card("DIAMONDS", "TEN"));
        runDiamondsDefinition.add(new Card("DIAMONDS", "KING"));
        runDiamondsDefinition.add(new Card("DIAMONDS", "QUEEN"));
        runDiamondsDefinition.add(new Card("DIAMONDS", "JACK"));

        ArrayList<Card> runClubsDefinition = new ArrayList<>();
        runClubsDefinition.add(new Card("CLUBS", "ACE"));
        runClubsDefinition.add(new Card("CLUBS", "TEN"));
        runClubsDefinition.add(new Card("CLUBS", "KING"));
        runClubsDefinition.add(new Card("CLUBS", "QUEEN"));
        runClubsDefinition.add(new Card("CLUBS", "JACK"));

        ArrayList<Card> runSpadesDefinition = new ArrayList<>();
        runSpadesDefinition.add(new Card("SPADES", "ACE"));
        runSpadesDefinition.add(new Card("SPADES", "TEN"));
        runSpadesDefinition.add(new Card("SPADES", "KING"));
        runSpadesDefinition.add(new Card("SPADES", "QUEEN"));
        runSpadesDefinition.add(new Card("SPADES", "JACK"));

        ArrayList<Card> pinochleDefinition = new ArrayList<>();
        pinochleDefinition.add(new Card("DIAMONDS", "JACK"));
        pinochleDefinition.add(new Card("DIAMONDS", "JACK"));

        ArrayList<Card> doublePinochleDefinition = new ArrayList<>();
        doublePinochleDefinition.addAll(pinochleDefinition); doublePinochleDefinition.addAll(pinochleDefinition);

        ArrayList<Card> roundHouseDefinition = new ArrayList<>();
        roundHouseDefinition.addAll(queensAroundDefinition); roundHouseDefinition.addAll(kingsAroundDefinition);

        ArrayList<Card> marriageHeartsDefinition = new ArrayList<>();
        marriageHeartsDefinition.add(runHeartsDefinition.get(2)); marriageHeartsDefinition.add(runHeartsDefinition.get(3));

        ArrayList<Card> marriageDiamondsDefinition = new ArrayList<>();
        marriageDiamondsDefinition.add(runDiamondsDefinition.get(2)); marriageDiamondsDefinition.add(runDiamondsDefinition.get(3));

        ArrayList<Card> marriageClubsDefinition = new ArrayList<>();
        marriageClubsDefinition.add(runClubsDefinition.get(2)); marriageClubsDefinition.add(runClubsDefinition.get(3));

        ArrayList<Card> marriageSpadesDefinition = new ArrayList<>();
        marriageSpadesDefinition.add(runSpadesDefinition.get(2)); marriageSpadesDefinition.add(runSpadesDefinition.get(3));


        CardCombo hundredAces = new CardCombo("HUNDRED ACES", 10, 4, hundredAcesDefinition);
        CardCombo kingsAround = new CardCombo("KINGS AROUND", 8, 4, kingsAroundDefinition);
        CardCombo queensAround = new CardCombo("QUEENS AROUND", 6, 4, queensAroundDefinition);
        CardCombo jacksAround = new CardCombo("JACKS AROUND", 4, 4, jacksAroundDefinition);
        CardCombo runHearts = new CardCombo("RUN HEARTS", 15, 5, runHeartsDefinition);
        CardCombo runDiamonds = new CardCombo("RUN DIAMONDS", 15, 5, runDiamondsDefinition);
        CardCombo runClubs = new CardCombo("RUN CLUBS", 15, 5, runClubsDefinition);
        CardCombo runSpades = new CardCombo("RUN SPADES", 15, 5 , runSpadesDefinition);
        CardCombo roundHouse = new CardCombo("ROUND HOUSE", 24, 8, roundHouseDefinition);
        CardCombo pinochle = new CardCombo("PINOCHLE", 4, 2, pinochleDefinition);
        CardCombo marriageHearts = new CardCombo("MARRIAGE HEARTS", 2, 2, marriageHeartsDefinition);
        CardCombo marriageClubs = new CardCombo("MARRIAGE CLUBS", 2, 2, marriageClubsDefinition);
        CardCombo marriageDiamonds = new CardCombo("MARRIAGE DIAMONDS", 2, 2, marriageDiamondsDefinition);
        CardCombo marriageSpades = new CardCombo("MARRIAGE SPADES", 2, 2, marriageSpadesDefinition);

        //check for aces
        Card c = findCardInSuit("HEARTS", "ACE");
        if(c != null){
            hundredAces.addPlayersCard(c);
            runHearts.addPlayersCard(c);
        }
        c = findCardInSuit("CLUBS", "ACE");
        if(c != null){
            hundredAces.addPlayersCard(c);
            runClubs.addPlayersCard(c);
        }
        c = findCardInSuit("DIAMONDS", "ACE");
        if(c != null){
            hundredAces.addPlayersCard(c);
            runDiamonds.addPlayersCard(c);
        }
        c = findCardInSuit("SPADES", "ACE");
        if(c != null){
            hundredAces.addPlayersCard(c);
            runSpades.addPlayersCard(c);
        }

        meldCombinations.add(hundredAces);


        // check for kingsAround


        c = findCardInSuit("HEARTS", "KING");
        if(c != null){
            kingsAround.addPlayersCard(c);
            runHearts.addPlayersCard(c);
            marriageHearts.addPlayersCard(c);
        }
        c = findCardInSuit("CLUBS", "KING");
        if(c != null){
            kingsAround.addPlayersCard(c);
            runClubs.addPlayersCard(c);
            marriageClubs.addPlayersCard(c);
        }
        c = findCardInSuit("DIAMONDS", "KING");
        if(c != null){
            kingsAround.addPlayersCard(c);
            runDiamonds.addPlayersCard(c);
            marriageDiamonds.addPlayersCard(c);
        }
        c = findCardInSuit("SPADES", "KING");
        if(c!= null){
            kingsAround.addPlayersCard(c);
            runSpades.addPlayersCard(c);
            marriageSpades.addPlayersCard(c);
        }

        meldCombinations.add(kingsAround);

        // check for queensAround

        c = findCardInSuit("HEARTS", "QUEEN");
        if(c != null){
            queensAround.addPlayersCard(c);
            runHearts.addPlayersCard(c);
            marriageHearts.addPlayersCard(c);
        }
        c = findCardInSuit("CLUBS", "QUEEN");
        if(c != null){
            queensAround.addPlayersCard(c);
            runClubs.addPlayersCard(c);
            marriageClubs.addPlayersCard(c);
        }
        c = findCardInSuit("DIAMONDS", "QUEEN");
        if(c != null){
            queensAround.addPlayersCard(c);
            runDiamonds.addPlayersCard(c);
            marriageDiamonds.addPlayersCard(c);
        }
        c = findCardInSuit("SPADES", "QUEEN");
        if(c != null){
            queensAround.addPlayersCard(c);
            runSpades.addPlayersCard(c);
            marriageSpades.addPlayersCard(c);
            pinochle.addPlayersCard(c);
        }

        meldCombinations.add(queensAround);

        // check for jacksAround

        c = findCardInSuit("HEARTS", "JACK");
        if(c != null){
            jacksAround.addPlayersCard(c);
            runHearts.addPlayersCard(c);

        }
        c = findCardInSuit("CLUBS", "JACK");
        if(c != null){
            jacksAround.addPlayersCard(c);
            runClubs.addPlayersCard(c);
        }
        c = findCardInSuit("DIAMONDS", "JACK");
        if(c != null){
            jacksAround.addPlayersCard(c);
            runDiamonds.addPlayersCard(c);
            pinochle.addPlayersCard(c);
        }
        c = findCardInSuit("SPADES", "JACK");
        if(c != null){
            jacksAround.addPlayersCard(c);
            runSpades.addPlayersCard(c);
        }

        meldCombinations.add(jacksAround);

        // check tens;

        c = findCardInSuit("HEARTS", "TEN");
        if(c != null){
            runHearts.addPlayersCard(c);
        }
        c = findCardInSuit("CLUBS", "TEN");
        if(c != null){
            runClubs.addPlayersCard(c);
        }
        c = findCardInSuit("DIAMONDS", "TEN");
        if(c != null){
            runDiamonds.addPlayersCard(c);
        }
        c = findCardInSuit("SPADES", "TEN");
        if(c != null){
            runSpades.addPlayersCard(c);
        }


        // add runs and round house meld combinations
        meldCombinations.add(runClubs); meldCombinations.add(runSpades);
        meldCombinations.add(runDiamonds); meldCombinations.add(runHearts);

        List<Card> roundHouseCards = new ArrayList<>();
        roundHouseCards.addAll(kingsAround.getPlayersCards());
        roundHouseCards.addAll(queensAround.getPlayersCards());
        for(Card card: roundHouseCards){
            roundHouse.addPlayersCard(card);
        }

        meldCombinations.add(roundHouse);

        // assess double marriages
        int removeCounter = 0;
        if(marriageHearts.getPlayersCards().size() == 2){
            List<Card> candidates = hearts;
            Iterator it = candidates.iterator();
            while(it.hasNext()){
                Card next = (Card)it.next();
                if(next.getRank().equalsIgnoreCase("KING") || next.getRank().equalsIgnoreCase("QUEEN")){
                    removeCounter ++;
                    it.remove();
                    if(removeCounter == 3){
                        marriageHearts.addPlayersCard(next);
                    }
                    if(removeCounter == 4){
                        marriageHearts.addPlayersCard(next);
                    }
                }
            }

        }

        removeCounter = 0;
        if(marriageClubs.getPlayersCards().size() == 2){
            List<Card> candidates = clubs;
            Iterator it = candidates.iterator();
            while(it.hasNext()){
                Card next = (Card)it.next();
                if(next.getRank().equalsIgnoreCase("KING") || next.getRank().equalsIgnoreCase("QUEEN")){
                    removeCounter ++;
                    it.remove();
                    if(removeCounter == 3){
                        marriageClubs.addPlayersCard(next);
                    }
                    if(removeCounter == 4){
                        marriageClubs.addPlayersCard(next);
                    }
                }
            }

        }

        removeCounter = 0;
        if(marriageDiamonds.getPlayersCards().size() == 2){
            List<Card> candidates = diamonds;
            Iterator it = candidates.iterator();
            while(it.hasNext()){
                Card next = (Card)it.next();
                if(next.getRank().equalsIgnoreCase("KING") || next.getRank().equalsIgnoreCase("QUEEN")){
                    removeCounter ++;
                    it.remove();
                    if(removeCounter == 3){
                        marriageDiamonds.addPlayersCard(next);
                    }
                    if(removeCounter == 4){
                        marriageDiamonds.addPlayersCard(next);
                    }
                }
            }

        }

        removeCounter = 0;
        if(marriageSpades.getPlayersCards().size() == 2){
            List<Card> candidates = spades;
            Iterator it = candidates.iterator();
            while(it.hasNext()){
                Card next = (Card)it.next();
                if(next.getRank().equalsIgnoreCase("KING") || next.getRank().equalsIgnoreCase("QUEEN")){
                    removeCounter ++;
                    it.remove();
                    if(removeCounter == 3){
                        marriageSpades.addPlayersCard(next);
                    }
                    if(removeCounter == 4){
                        marriageSpades.addPlayersCard(next);
                    }
                }
            }

        }

        // add marriage meld combinations

        meldCombinations.add(marriageClubs); meldCombinations.add(marriageSpades);
        meldCombinations.add(marriageHearts); meldCombinations.add(marriageDiamonds);

        // check for double pinochle
//        removeCounter = 0;
//        if(pinochle.getPlayersCards().size() == 2){
//            List<Card> candidates = spades;
//            Iterator it = candidates.iterator();
//            while(it.hasNext()){
//                Card next = (Card)it.next();
//                if(next.getRank().equalsIgnoreCase("QUEEN")) {
//                    removeCounter++;
//                    it.remove();
//                    if (removeCounter == 2) {
//                        pinochle.addPlayersCard(next);
//                    }
//                }
//            }
//
//        }
//        removeCounter = 0;
//        if(pinochle.getPlayersCards().size() == 2){
//            List<Card> candidates = diamonds;
//            Iterator it = candidates.iterator();
//            while(it.hasNext()){
//                Card next = (Card)it.next();
//                if(next.getRank().equalsIgnoreCase("JACK")) {
//                    removeCounter++;
//                    it.remove();
//                    if (removeCounter == 2) {
//                        pinochle.addPlayersCard(next);
//                    }
//                }
//            }
//
//        }

        meldCombinations.add(pinochle);



    // meld

        if(hundredAces.getPlayersCards().size() == 4){
        meld += 10;
        System.out.println("Aces around");
    }
        if(runClubs.getPlayersCards().size() == 5 || runSpades.getPlayersCards().size() == 5 ||
            runDiamonds.getPlayersCards().size() == 5 || runHearts.getPlayersCards().size() == 5){
        meld += 13;
        System.out.println("run");
    }

        if(roundHouse.getPlayersCards().size() == 8){
        meld += 24;
        System.out.println("Round House");
    }
        else {
        if (kingsAround.getPlayersCards().size() == 4) {
            meld += 8;
            System.out.println("Kings Around");
        } else if (queensAround.getPlayersCards().size() == 4) {
            System.out.println("Queens Around");
            meld += 6;
        }
        //check mariages
        if(marriageClubs.getPlayersCards().size() >= 2){
            meld +=2;
            System.out.println("M clubs");
            if(marriageClubs.getPlayersCards().size() == 4){
                meld += 2;
                System.out.println("M clubs");
            }
        }

        if(marriageSpades.getPlayersCards().size() >= 2){
            meld +=2;
            System.out.println("M spades");
            if(marriageSpades.getPlayersCards().size() == 4){
                meld += 2;
                System.out.println("M spades");
            }
        }

        if(marriageHearts.getPlayersCards().size() >= 2){
            meld +=2;
            System.out.println("M hearts");
            if(marriageHearts.getPlayersCards().size() == 4){
                meld += 2;
                System.out.println("M hearts");
            }
        }

        if(marriageDiamonds.getPlayersCards().size() >= 2){
            meld +=2;
            System.out.println("M diamonds");
            if(marriageDiamonds.getPlayersCards().size() == 4){
                meld += 2;
                System.out.println("M diamonds");
            }
        }

    }

        if(jacksAround.getPlayersCards().size() == 4){
        meld += 4;
        System.out.println("jacks around");
    }



        if(pinochle.getPlayersCards().size() == 4){
                 meld += 30;
                 System.out.println("double pinochle");
        }
        else if(pinochle.getPlayersCards().size() >= 2){
            meld += 4;
            System.out.println("1 pinochle ");
        }


        for(CardCombo cardCombo : meldCombinations){
        stateOfCombinations.put(cardCombo.getName(), cardCombo.getPlayersCards().size());
    }

    setMeld(meld);

}


    public List<Card> getHearts(){
        List<cards.Card> hearts = new ArrayList<>();
        for(cards.Card c: hand){
            if(c.getSuit().equalsIgnoreCase("HEARTS")){
                hearts.add(c);
            }
        }
        hearts.sort(Comparator.comparing(cards.Card::getRelativeRank).reversed());
        return hearts;

    }

    public List<Card> getDiamonds(){
        List<Card> diamonds = new ArrayList<>();
        for(Card c: hand){
            if(c.getSuit().equalsIgnoreCase("DIAMONDS")){
                diamonds.add(c);
            }
        }
        diamonds.sort(Comparator.comparing(Card::getRelativeRank).reversed());
        return diamonds;
    }

    public List<Card> getSpades(){
        List<Card> spades = new ArrayList<>();
        for(Card c: hand){
            if(c.getSuit().equalsIgnoreCase("SPADES")){
                spades.add(c);
            }
        }
        spades.sort(Comparator.comparing(Card::getRelativeRank).reversed());
        return spades;
    }

    public List<Card> getClubs(){
        List<Card> clubs = new ArrayList<>();
        for(Card c: hand){
            if(c.getSuit().equalsIgnoreCase("CLUBS")){
                clubs.add(c);
            }
        }
        clubs.sort(Comparator.comparing(Card::getRelativeRank).reversed());
        return clubs;
    }



    public boolean checkForCardInSuit(String suit, String rank){
        boolean found = false;
        if(suit.equalsIgnoreCase("HEARTS")){
            for(Card c: hearts){
                if(c.getRank().equalsIgnoreCase(rank)){
                    found = true;
                    break;
                }

            }
        }
        else if(suit.equalsIgnoreCase("DIAMONDS")){
            for(Card c: diamonds){
                if(c.getRank().equalsIgnoreCase(rank)){
                    found = true;
                    break;
                }
            }
        }
        else if(suit.equalsIgnoreCase("CLUBS")){
            for(Card c: clubs){
                if(c.getRank().equalsIgnoreCase(rank)){
                    found = true;
                    break;
                }
            }

        }
        else{ // spades
            for(Card c: spades){
                if(c.getRank().equalsIgnoreCase(rank)){
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public Card findCardInSuit(String suit, String rank){
        Card found = null;
        if(suit.equalsIgnoreCase("HEARTS")){
            for(Card c: hearts){
                if(c.getRank().equals(rank)){
                    found = c;
                    break;
                }

            }
        }
        else if(suit.equalsIgnoreCase("DIAMONDS")){
            for(Card c: diamonds){
                if(c.getRank().equals(rank)){
                    found = c;
                    break;
                }
            }
        }
        else if(suit.equalsIgnoreCase("CLUBS")){
            for(Card c: clubs){
                if(c.getRank().equals(rank)){
                    found = c;
                    break;
                }
            }

        }
        else{ // spades
            for(Card c: spades){
                if(c.getRank().equalsIgnoreCase(rank)){
                    found = c;
                    break;
                }
            }
        }
        return found;
    }

    public int getMeld(){
        return meld;
    }

    public void setMeld(int meld){
        this.meld = meld;
    }

    public List<CardCombo> getMeldCombinations() {
        return meldCombinations;
    }

    public HashMap<String, Integer> getStateOfCombinations() {
        return stateOfCombinations;
    }

    public int countMeldFromTrump(String trump){
        int moreMeld = 0;
        for(Card c: hand){
            if(c.getSuit().equalsIgnoreCase(trump) && c.getRank().equalsIgnoreCase("NINE")){
                moreMeld += 1;
                trumpNineCounter ++;
            }
        }

        if(trump.equalsIgnoreCase("HEARTS")){
            if(getStateOfCombinations().get("MARRIAGE HEARTS") >= 2){
                if(getStateOfCombinations().get("RUN HEARTS") != 5 && getStateOfCombinations().get(("ROUND HOUSE"))!= 8){ // if dont have run or roundhouse, count marriage
                    moreMeld += 2;
                }
                if(getStateOfCombinations().get("MARRIAGE HEARTS") == 4){
                    moreMeld +=2;
                }
            }
        }
        else if(trump.equalsIgnoreCase("DIAMONDS")){
            if(getStateOfCombinations().get("MARRIAGE DIAMONDS") >= 2){
                if(getStateOfCombinations().get("RUN DIAMONDS") != 5 && getStateOfCombinations().get(("ROUND HOUSE"))!= 8){ // if dont have run or roundhouse, count marriage
                    moreMeld += 2;
                }
                if(getStateOfCombinations().get("MARRIAGE DIAMONDS") == 4){
                    moreMeld +=2;
                }
            }
        }
        else if(trump.equalsIgnoreCase("CLUBS")){
            if(getStateOfCombinations().get("MARRIAGE CLUBS") >= 2){
                if(getStateOfCombinations().get("RUN CLUBS") != 5 && getStateOfCombinations().get(("ROUND HOUSE"))!= 8){ // if dont have run or roundhouse, count marriage
                    moreMeld += 2;
                }
                if(getStateOfCombinations().get("MARRIAGE CLUBS") == 4){
                    moreMeld +=2;
                }
            }
        }
        else{ // trump = spades
            if(getStateOfCombinations().get("MARRIAGE SPADES") >= 2){
                if(getStateOfCombinations().get("RUN SPADES") != 5 && getStateOfCombinations().get(("ROUND HOUSE"))!= 8){ // if dont have run or roundhouse, count marriage
                    moreMeld += 2;
                }
                if(getStateOfCombinations().get("MARRIAGE SPADES") == 4){
                    moreMeld += 2;
                }
            }
        }
        meldFromTrump = moreMeld;
        return moreMeld + getMeld();
    }

    public int getNineCounter(){
        return trumpNineCounter;
    }


}

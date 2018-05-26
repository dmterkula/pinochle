package data;

import cards.Card;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dmter on 12/25/2017.
 */
public class CardReader {
    private String fileName;
    private int numRecords;
    private String[][] cardSet;

    public CardReader(String fileName, int numRecords) {
        this.fileName = fileName;
        this.numRecords = numRecords;
        cardSet = new String[numRecords][11];
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }

    public String[][] getCardSet() {
        return cardSet;
    }

    public void setCardSet(String[][] cardSet) {
        this.cardSet = cardSet;
    }

    public void readInCardSet(){
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> allCards = stream.collect(Collectors.toList());
            String [] linesArray = allCards.toArray(new String[11]);
            for(int lineCounter = 0; lineCounter < numRecords ; lineCounter++){
                String lineofCards = linesArray[lineCounter];
                String [] cards = lineofCards.split(",");
                cardSet[lineCounter] = cards;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Card> convertStringToCard(String[] stringRepresentation){
        List<Card> cards = new ArrayList<>();
         for(int i = 0; i < stringRepresentation.length; i++){
             String[] name = stringRepresentation[i].split(" ");
             cards.add(new Card(name[2], name[0]));
         }
         return cards;
    }

}

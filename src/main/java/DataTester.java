import cards.Card;
import data.CardReader;
import data.DataReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by dmter on 12/23/2017.
 */
public class DataTester {

    private static String absPathCards = "C:\\Users\\dmter\\Desktop\\pinochle\\src\\main\\resources\\cards.txt";
    private static String csvPath = "C:\\Users\\dmter\\Desktop\\pinochle\\src\\main\\resources\\data.csv";

    public static void main(String [] args)throws IOException{
        DataReader dataReader = new DataReader(csvPath, 351);
        String[][] data = dataReader.readDataFile();

        dataReader.printData(data);

//        CardReader cardReader = new CardReader(absPathCards, 1);
//        cardReader.readInCardSet();
//        List<Card> cards = cardReader.convertStringToCard(cardReader.getCardSet()[0]);
//        for(Card c: cards){
//            System.out.println(c);
//        }


    }

    /*public static void writeDataToCsv(String[][] data) throws IOException{

        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(csvPath);

            for(int i = 0; i < data.length; i++){
                for(int j = 0; j < data[j].length; j++){
                    if(j == data[j].length-1){
                        String bidOrPass = "";
                        if(data[i][j].equals("0")){
                            bidOrPass = "pass";
                        }
                        else if(data[i][j].equals("1")){
                            bidOrPass = "bid";
                        }
                        else{
                            bidOrPass = "meld bid";
                        }
                        fileWriter.append(bidOrPass);
                    }
                    else{
                        fileWriter.append(data[i][j]);
                    }

                    if(j != data[j].length-1){
                        fileWriter.append(",");
                    }
                    else{
                        fileWriter.append("\n");
                    }
                }
            }

        }

        catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");

                e.printStackTrace();

            }

        }

    }*/

}

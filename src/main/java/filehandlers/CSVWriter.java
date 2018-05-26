package filehandlers;

import cards.Card;

import java.io.*;
import java.util.List;

/**
 * Created by dmter on 12/30/2017.
 */
public class CSVWriter {

    private static String fileLocation = "C:\\Users\\dmter\\Desktop\\pinochle\\src\\main\\resources\\";
    private static String filePath;

    public CSVWriter(String fileName) throws IOException{
        filePath = fileLocation + fileName;
        boolean emptyFile =  isFileEmpty();
        if(emptyFile) {
            createHeader();
        }
    }

public boolean isFileEmpty() {
    boolean returnMe = false;
    BufferedReader br = null;
    FileReader fr = null;
    try {
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        if (br.readLine() == null) {
            returnMe = true;
        }

    } catch (IOException e) {

        e.printStackTrace();

    } finally {
        try {

            if (br != null)
                br.close();

            if (fr != null)
                fr.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }
    return returnMe;
}


    public void createHeader() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath, true);
            bw = new BufferedWriter(fw);
            String fileHeader = "meld,hand-strength,AiBid,didPartnerBid,DidPartnerMeldBid,DidPartnerPass,handCounter,scoreCounter,lastBid,BidPassOrMeldBid";
            bw.write(fileHeader);
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

    public void writeToCSV(Object[] inputs){
    BufferedWriter bw = null;
    FileWriter fw = null;
        try {
        fw = new FileWriter(filePath, true);
        bw = new BufferedWriter(fw);
        String val = "";
        int counter = 0;
        for(Object o: inputs){
            counter ++;
            val = o.toString();
            bw.write(val);
            if(counter != inputs.length) {
                bw.write(",");
            }


        }

        //bw.write(" ");
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

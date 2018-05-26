package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dmter on 12/23/2017.
 */
public class DataReader {

    private String fileName;
    private int numRecords;
    String[][] dataAsStringArray;
    private int [][] data;

    public DataReader(String filename, int numRecords){
        this.fileName = filename;
        this.numRecords = numRecords;
        readDataFile();

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

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public String[][] readDataFile(){
        String[][] dataAsString = new String[numRecords][10];
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> lines = stream.collect(Collectors.toList());
            String [] linesArray = lines.toArray(new String[10]);
            for(int lineCounter = 0; lineCounter < numRecords; lineCounter++){
                String lineAsString = linesArray[lineCounter];
                String [] tokens = lineAsString.split(",");
                dataAsString[lineCounter] = tokens;
            }
            dataAsStringArray = dataAsString;
        } catch (IOException e) {
            e.printStackTrace();
        }

    //}

    return dataAsString;
}


    public void printData(String[][] dataAsString){
        //test, should print file as is but stored in 2d array
            for(int i = 0; i < dataAsString.length; i++){
                for(int j = 0; j < dataAsString[i].length; j++){
                    System.out.print(dataAsString[i][j] + ",");
                }
                System.out.println();
            }
    }






}

package filehandlers;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dmter on 12/29/2017.
 */
public class CSVToARFFConverter {

    private static String fileLocation =  "C:\\Users\\David\\Desktop\\pinochle\\src\\main\\resources\\";
    private static String arrf = ".arff";
    private String destPath;
    private String fileSource;

    public CSVToARFFConverter(String fileName){
        fileSource = fileLocation + fileName;
        destPath = fileLocation + fileName.substring(0, fileName.length()-4) + arrf;
                /// test.txt
                /// 01234567

    }

    public String getFileSource() {
        return fileSource;
    }

    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }

//    public String getDestFileName() {
//        return destFileName;
//    }
//
//    public void setDestFileName(String destFileName) {
//        this.destFileName = destFileName;
//    }

    public void writeToARRF() throws IOException {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(getFileSource()));
        Instances data = loader.getDataSet();

        // save ARFF
//        ArffSaver saver = new ArffSaver();
//        saver.setInstances(data);
//
//        saver.setFile(new File(destPath));
//        saver.setDestination(new File(destPath));
//        saver.writeBatch();
        BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
        writer.write(data.toString());
        writer.flush();
        writer.close();
    }
}

package ml;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by dmter on 12/27/2017.
 */
public class DecisionTree implements Serializable{

    private String filePath;
    private J48 tree;
    private Instances data;
    private static String fileLocation = "C:\\Users\\David\\Desktop\\pinochle\\src\\main\\resources\\";

    public DecisionTree(String fileName) throws Exception{
        filePath = fileLocation + fileName;
        tree = new J48();
        data = getInstances(filePath);
    }

    public DecisionTree(J48 tree){
        this.tree = tree;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }



    public Instances getInstances(String filePath)throws IOException{
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
         data = new Instances(reader);
        reader.close();
        // setting class attribute
        if(data.classIndex() == -1){
            data.setClassIndex(data.numAttributes() - 1);
        }

        return data;
    }

    public void createClassifier()throws Exception{
        tree.buildClassifier(data);

    }

    public void crossValidateTree() throws Exception{
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(tree, data, 10, new Random(1));
        System.out.println(eval.toSummaryString());
    }

    public String evalauteInstance(String fileName) throws Exception{
        //12.classify
        //result
        Instances instance = getInstances(fileLocation + fileName);
        //System.out.println(tree.classifyInstance(instance.firstInstance()));
        //classified result value
        String nominalPredicition = instance.attribute(instance.classIndex()).value((int)instance.firstInstance().classValue());
        System.out.println("prediction: " + nominalPredicition);
        //System.out.println(tree.distributionForInstance(instance.firstInstance()));
        return nominalPredicition;
    }

    public Classifier getJ48() {
        return tree;
    }

    public void setJ48(J48 tree) {
        this.tree = tree;
    }
}

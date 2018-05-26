import filehandlers.CSVToARFFConverter;
import ml.DecisionTree;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

/**
 * Created by David on 12/28/2017.
 */
public class MLTester {

    public static void main(String [] args) throws Exception{
        CSVToARFFConverter converter =  new CSVToARFFConverter("data.csv");
        converter.writeToARRF();
        DecisionTree tree = new DecisionTree("data.arff");
        tree.createClassifier();
        tree.crossValidateTree();
        tree.evalauteInstance("data.arff");


        weka.core.SerializationHelper.write("C:\\Users\\David\\Desktop\\pinochle\\src\\main\\resources\\j48.model", tree.getJ48());

       J48 theTree = (J48) weka.core.SerializationHelper.read("C:\\Users\\David\\Desktop\\pinochle\\src\\main\\resources\\j48.model");
       DecisionTree readTree = new DecisionTree(theTree);
       readTree.evalauteInstance("data.arff");
    }

}

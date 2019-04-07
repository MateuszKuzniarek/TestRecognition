package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import logic.EuclideanMetric;
import logic.ExampleLoader;
import logic.FeatureExtractor;
import logic.KNNClassification;
import logic.ManhattanMetric;
import logic.Metric;
import logic.TFExtractor;
import logic.TFIDFExtractor;
import logic.TextSample;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLController implements Initializable
{
    
    @FXML
    private Label resultLabel;

    @FXML
    private ComboBox<FeatureExtractor> extractorComboBox;

    @FXML
    private ComboBox<Metric> metricComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<Integer> kCoefficientComboBox;
    
    @FXML
    private void testButtonAction(ActionEvent event)
    {
        try
        {
            List<TextSample> examples2 = ExampleLoader.loadFromAllFiles(categoryComboBox.getValue());
            examples2 = ExampleLoader.filterPlaces(examples2);
            //System.out.println(examples2.size());
            List<TextSample> examples = examples2.subList(0, 2000);
            int count = examples.size();
            int trainingCount = (int) (0.6*count);
            List<TextSample> trainingSamples = examples.subList(0, trainingCount);
            List<TextSample> testSamples = examples.subList(trainingCount, count);

            KNNClassification knn = new KNNClassification(kCoefficientComboBox.getValue());
            knn.setFeatureExtractor(extractorComboBox.getValue());
            knn.setMetric(metricComboBox.getValue());
            knn.train(trainingSamples);
            int correctAnswers = 0;
            for(TextSample sample : testSamples)
            {
                String answer = knn.classify(sample);
                System.out.println(answer + " " + sample.getLabels().get(0));
                if(answer.equals(sample.getLabels().get(0))) correctAnswers++;
            }
            double result =(double)correctAnswers/testSamples.size();
            System.out.println(result);
            resultLabel.setText("Wynik:" + result * 100 + "%");

            //for(logic.TextSample example : examples) System.out.println(example);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        extractorComboBox.getItems().add(new TFExtractor());
        extractorComboBox.getItems().add(new TFIDFExtractor());
        metricComboBox.getItems().add(new EuclideanMetric());
        metricComboBox.getItems().add(new ManhattanMetric());
        kCoefficientComboBox.getItems().add(new Integer(3));
        kCoefficientComboBox.getItems().add(new Integer(4));
        kCoefficientComboBox.getItems().add(new Integer(5));
        categoryComboBox.getItems().add("PLACES");
    }    
}

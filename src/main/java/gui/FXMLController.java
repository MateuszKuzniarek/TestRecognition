package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;
import logic.CSVData;
import logic.CSVWriter;
import logic.ChebyshevMetric;
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
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLController implements Initializable
{
    
    @FXML
    private Label resultLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private ComboBox<FeatureExtractor> extractorComboBox;

    @FXML
    private ComboBox<Metric> metricComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField kCoefficientTextField;

    @FXML
    private Button testButton;

    private KNNClassification knn = new KNNClassification();
    private List<TextSample> trainingSamples;
    private List<TextSample> testSamples;

    @FXML
    private void findKeywordsButtonAction(ActionEvent event)
            throws IOException, SAXException, ParserConfigurationException
    {
        List<TextSample> examples = ExampleLoader.loadFromAllFiles(categoryComboBox.getValue());
        if(categoryComboBox.getValue().equals("PLACES")) examples = ExampleLoader.filterPlaces(examples);
        //System.out.println(examples2.size());
        //List<TextSample> examples = examples2.subList(0, 2000);
        int count = examples.size();
        int trainingCount = (int) (0.6*count);
        trainingSamples = examples.subList(0, trainingCount);
        testSamples = examples.subList(trainingCount, count);
        knn.init(trainingSamples);
        testButton.setDisable(false);
    }

    @FXML
    private void testButtonAction(ActionEvent event)
    {
        long startTime = System.nanoTime();
        knn.setK(Integer.parseInt(kCoefficientTextField.getText()));
        knn.setFeatureExtractor(extractorComboBox.getValue());
        knn.setMetric(metricComboBox.getValue());
        knn.train();
        int correctAnswers = 0;
        for(TextSample sample : testSamples)
        {
            String answer = knn.classify(sample);
            System.out.println(answer + " " + sample.getLabels().get(0));
            if(answer.equals(sample.getLabels().get(0))) correctAnswers++;
        }
        double result =(double)correctAnswers/testSamples.size();
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime)/1000000000f;
        DecimalFormat format = new DecimalFormat("#.###");
        format.setRoundingMode(RoundingMode.HALF_UP);
        CSVWriter.appendDataToFile(new CSVData(result, elapsedTime), "result.csv");
        System.out.println(result);
        resultLabel.setText(format.format(result * 100) + "%");
        timeLabel.setText(format.format(elapsedTime) + "s");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        kCoefficientTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        extractorComboBox.getItems().add(new TFExtractor());
        extractorComboBox.getItems().add(new TFIDFExtractor());
        metricComboBox.getItems().add(new EuclideanMetric());
        metricComboBox.getItems().add(new ManhattanMetric());
        metricComboBox.getItems().add(new ChebyshevMetric());
        categoryComboBox.getItems().add("PLACES");
        categoryComboBox.getItems().add("TOPICS");
    }    
}

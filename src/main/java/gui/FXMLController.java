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
import logic.metrics.AverageMinSimilarity;
import logic.metrics.CustomSimilarity;
import logic.metrics.MinMaxSimilarity;
import logic.utils.CSVData;
import logic.utils.CSVWriter;
import logic.metrics.ChebyshevMetric;
import logic.extractors.CustomExtractor;
import logic.metrics.EuclideanMetric;
import logic.utils.ExampleLoader;
import logic.extractors.FeatureExtractor;
import logic.classification.KNNClassification;
import logic.metrics.ManhattanMetric;
import logic.metrics.Metric;
import logic.extractors.TFExtractor;
import logic.extractors.TFIDFExtractor;
import logic.classification.TextSample;
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
    private ComboBox<String> dataSetComboBox;

    @FXML
    private TextField kCoefficientTextField;

    @FXML
    private TextField numberOfKeywordsTextField;

    @FXML
    private Button testButton;

    private KNNClassification knn = new KNNClassification();
    private List<TextSample> trainingSamples;
    private List<TextSample> testSamples;

    @FXML
    private void changeDataSet(ActionEvent event)
    {
        if(dataSetComboBox.getValue().equals("REUTERS"))
        {
            categoryComboBox.getItems().clear();
            categoryComboBox.getItems().add("PLACES");
            categoryComboBox.getItems().add("TOPICS");
        }
        else if(dataSetComboBox.getValue().equals("QUOTES"))
        {
            categoryComboBox.getItems().clear();
            categoryComboBox.getItems().add("AUTHOR");
        }
    }

    @FXML
    private void findKeywordsButtonAction(ActionEvent event)
            throws IOException, SAXException, ParserConfigurationException
    {
        System.out.println("Processing...");
        List<TextSample> examples = ExampleLoader.loadDataSet(dataSetComboBox.getValue(), categoryComboBox.getValue());
        if(categoryComboBox.getValue().equals("PLACES")) examples = ExampleLoader.filterPlaces(examples);
        else if(categoryComboBox.getValue().equals("TOPICS")) examples = ExampleLoader.filterTopics(examples);
        //System.out.println(examples2.size());
        //List<TextSample> examples = examples2.subList(0, 2000);
        int count = examples.size();
        int trainingCount = (int) (0.6*count);
        trainingSamples = examples.subList(0, trainingCount);
        testSamples = examples.subList(trainingCount, count);
        knn.init(trainingSamples, Integer.parseInt(numberOfKeywordsTextField.getText()));
        testButton.setDisable(false);
        System.out.println("Done!");
    }

    @FXML
    private void testButtonAction(ActionEvent event)
    {
        System.out.println("Processing...");
        knn.setK(Integer.parseInt(kCoefficientTextField.getText()));
        knn.setFeatureExtractor(extractorComboBox.getValue());
        knn.setMetric(metricComboBox.getValue());
        knn.train();
        long startTime = System.nanoTime();
        int correctAnswers = 0;
        for(TextSample sample : testSamples)
        {
            String answer = knn.classify(sample);
            //System.out.println(answer + " " + sample.getLabels().get(0));
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
        System.out.println("Done!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        kCoefficientTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        kCoefficientTextField.setText("3");
        numberOfKeywordsTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        numberOfKeywordsTextField.setText("3");
        extractorComboBox.getItems().add(new TFExtractor());
        extractorComboBox.getItems().add(new TFIDFExtractor());
        extractorComboBox.getItems().add(new CustomExtractor());
        metricComboBox.getItems().add(new EuclideanMetric());
        metricComboBox.getItems().add(new ManhattanMetric());
        metricComboBox.getItems().add(new ChebyshevMetric());
        metricComboBox.getItems().add(new AverageMinSimilarity());
        metricComboBox.getItems().add(new MinMaxSimilarity());
        metricComboBox.getItems().add(new CustomSimilarity());
        dataSetComboBox.getItems().add("REUTERS");
        dataSetComboBox.getItems().add("QUOTES");
    }    
}

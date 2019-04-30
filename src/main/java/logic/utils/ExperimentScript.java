package logic.utils;

import logic.classification.KNNClassification;
import logic.classification.TextSample;
import logic.extractors.CustomExtractor;
import logic.features.Feature;
import logic.metrics.AverageMinSimilarity;
import logic.metrics.ChebyshevMetric;
import logic.metrics.CustomSimilarity;
import logic.metrics.EuclideanMetric;
import logic.metrics.ManhattanMetric;
import logic.metrics.Metric;
import logic.metrics.MinMaxSimilarity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Smieciowy skrypt, tylko na potrzeby pisania sprawka. Usunac, jak tylko nie bedzie potrzebny i zatrzec wszystkie slady.
 */
public class ExperimentScript
{
    public static void start() throws ParserConfigurationException, SAXException, IOException
    {
        //experiment1();
        //experiment2("PLACES");
        //experiment2("TOPICS");
        //experiment2("QUOTES");
        //experiment4("PLACES");
        //experiment4("TOPICS");
        //experiment4("QUOTES");
        //experiment3();
        experiment5();
    }

    public static void experiment1() throws ParserConfigurationException, SAXException, IOException {
        CustomExtractor customExtractor = new CustomExtractor();
        System.out.println("Processing...");
        List<TextSample> examples = ExampleLoader.loadDataSet("REUTERS", "PLACES");
        //if(categoryComboBox.getValue().equals("PLACES"))
        examples = ExampleLoader.filterPlaces(examples);
        //else if(categoryComboBox.getValue().equals("TOPICS"))
        //examples = ExampleLoader.filterTopics(examples);
        //System.out.println(examples2.size());
        //List<TextSample> examples = examples2.subList(0, 2000);
        int count = examples.size();
        int trainingCount = (int) (0.6*count);
        List<TextSample> trainingSamples = examples.subList(0, trainingCount);
        List<TextSample> testSamples = examples.subList(trainingCount, count);
        KNNClassification knn = new KNNClassification();
        knn.init(trainingSamples, 3);
        //testButton.setDisable(false);
        System.out.println("Done!");


        customExtractor.initExtractor(knn.getKeywords());
        CSVWriter.appendStringToFile("EKSPERYMENT 1 - cechy - reuters, places, 3 slowa kluczowe, k=5", "result.csv");
        ArrayList<Feature> originalFeatures = new ArrayList<>();
        originalFeatures.addAll(customExtractor.getFeatures());
        for(Feature feature : originalFeatures)
        {
            double averageElapsedTime = 0;
            double result = 0;
            CSVWriter.appendStringToFile("removed" + feature.toString(), "result.csv");
            customExtractor.getFeatures().remove(feature);
            for(int i=0; i<5; i++)
            {
                System.out.println("Processing...");
                knn.setK(5);
                knn.setFeatureExtractor(customExtractor);
                knn.setMetric(new EuclideanMetric());
                knn.train();
                long startTime = System.nanoTime();
                int correctAnswers = 0;
                for (TextSample sample : testSamples) {
                    String answer = knn.classify(sample);
                    //System.out.println(answer + " " + sample.getLabels().get(0));
                    if (answer.equals(sample.getLabels().get(0)))
                        correctAnswers++;
                }
                result = (double) correctAnswers / testSamples.size();
                long endTime = System.nanoTime();
                averageElapsedTime += (endTime - startTime) / 1000000000f;
                System.out.println(averageElapsedTime);
            }
            averageElapsedTime /= 5;
            DecimalFormat format = new DecimalFormat("#.###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            CSVWriter.appendDataToFile(new CSVData(result, averageElapsedTime), "result.csv");
            System.out.println(result);
            customExtractor.setFeatures(new ArrayList<>());
            customExtractor.getFeatures().addAll(originalFeatures);
            //resultLabel.setText(format.format(result * 100) + "%");
            //timeLabel.setText(format.format(elapsedTime) + "s");
            System.out.println("Done!");
        }
    }

    public static void experiment2(String category) throws ParserConfigurationException, SAXException, IOException {

        ArrayList<TextSample> testSamples = new ArrayList<>();
        KNNClassification knn = initKnn(category, testSamples);
        System.out.println(testSamples);

        List<Integer> kCoefficients = Arrays.asList(3,7,9);
        CSVWriter.appendStringToFile("EKSPERYMENT 2 - parametr k - 3 slowa kluczowe, euklides, " +category, "result.csv");
        for(Integer k : kCoefficients)
        {
            double averageElapsedTime = 0;
            double result = 0;
            CSVWriter.appendStringToFile(k.toString(), "result.csv");
            for(int i=0; i<5; i++)
            {
                System.out.println("Processing...");
                knn.setK(k);
                CustomExtractor customExtractor = new CustomExtractor();
                customExtractor.initExtractor(knn.getKeywords());
                knn.setFeatureExtractor(customExtractor);
                //System.out.println(knn.getFeatureExtractor().getFeatures());
                knn.setMetric(new EuclideanMetric());
                knn.train();
                long startTime = System.nanoTime();
                int correctAnswers = 0;
                for (TextSample sample : testSamples) {
                    String answer = knn.classify(sample);
                    //System.out.println(answer + " " + sample.getLabels().get(0));
                    if (answer.equals(sample.getLabels().get(0)))
                        correctAnswers++;
                }
                result = (double) correctAnswers / testSamples.size();
                long endTime = System.nanoTime();
                averageElapsedTime += (endTime - startTime) / 1000000000f;
            }
            averageElapsedTime /= 5;
            DecimalFormat format = new DecimalFormat("#.###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            CSVWriter.appendDataToFile(new CSVData(result, averageElapsedTime), "result.csv");
            System.out.println(result);
            //resultLabel.setText(format.format(result * 100) + "%");
            //timeLabel.setText(format.format(elapsedTime) + "s");
            System.out.println("Done!");
        }
    }

    public static void experiment3() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Metric> metrics = new ArrayList<>();
        metrics.addAll(Arrays.asList(new AverageMinSimilarity(),
                new ChebyshevMetric(),
                new CustomSimilarity(),
                new EuclideanMetric(),
                new ManhattanMetric(),
                new MinMaxSimilarity()));
        System.out.println("Processing...");
        List<TextSample> examples = ExampleLoader.loadDataSet("REUTERS", "PLACES");
        //if(categoryComboBox.getValue().equals("PLACES"))
        examples = ExampleLoader.filterPlaces(examples);
        //else if(categoryComboBox.getValue().equals("TOPICS")) examples = ExampleLoader.filterTopics(examples);
        //System.out.println(examples2.size());
        //List<TextSample> examples = examples2.subList(0, 2000);
        int count = examples.size();
        int trainingCount = (int) (0.6*count);
        List<TextSample> trainingSamples = examples.subList(0, trainingCount);
        List<TextSample> testSamples = examples.subList(trainingCount, count);
        KNNClassification knn = new KNNClassification();
        knn.init(trainingSamples, 3);
        //testButton.setDisable(false);
        System.out.println("Done!");


        CSVWriter.appendStringToFile("EKSPERYMENT 3 - metryki - reuters, places, 3 slowa kluczowe, k=3", "result.csv");
        for(Metric metric : metrics)
        {
            double averageElapsedTime = 0;
            double result = 0;
            CSVWriter.appendStringToFile(metric.toString(), "result.csv");
            for(int i=0; i<5; i++)
            {
                System.out.println("Processing...");
                knn.setK(3);
                CustomExtractor customExtractor = new CustomExtractor();
                customExtractor.initExtractor(knn.getKeywords());
                knn.setFeatureExtractor(customExtractor);
                knn.setMetric(metric);
                knn.train();
                long startTime = System.nanoTime();
                int correctAnswers = 0;
                for (TextSample sample : testSamples) {
                    String answer = knn.classify(sample);
                    //System.out.println(answer + " " + sample.getLabels().get(0));
                    if (answer.equals(sample.getLabels().get(0)))
                        correctAnswers++;
                }
                result = (double) correctAnswers / testSamples.size();
                long endTime = System.nanoTime();
                averageElapsedTime += (endTime - startTime) / 1000000000f;
            }
            averageElapsedTime /= 5;
            DecimalFormat format = new DecimalFormat("#.###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            CSVWriter.appendDataToFile(new CSVData(result, averageElapsedTime), "result.csv");
            System.out.println(result);
            //resultLabel.setText(format.format(result * 100) + "%");
            //timeLabel.setText(format.format(elapsedTime) + "s");
            System.out.println("Done!");
        }
    }

    public static void experiment4(String category) throws ParserConfigurationException, SAXException, IOException {

        ArrayList<TextSample> testSamples = new ArrayList<>();
        KNNClassification knn = initKnn(category, testSamples);
        List<Boolean> booleanValues = Arrays.asList(true, false);
        CSVWriter.appendStringToFile("EKSPERYMENT 4 - normalizacja - 3 slowa kluczowe, euklides, k=5 "+category, "result.csv");
        for(Boolean booleanValue : booleanValues)
        {
            double averageElapsedTime = 0;
            double result = 0;
            CSVWriter.appendStringToFile(booleanValue.toString(), "result.csv");
            knn.setNormalizationEnabled(booleanValue);
            for(int i=0; i<5; i++)
            {
                System.out.println("Processing...");
                knn.setK(5);
                CustomExtractor customExtractor = new CustomExtractor();
                customExtractor.initExtractor(knn.getKeywords());
                knn.setFeatureExtractor(customExtractor);
                knn.setMetric(new EuclideanMetric());
                knn.train();
                long startTime = System.nanoTime();
                int correctAnswers = 0;
                for (TextSample sample : testSamples) {
                    String answer = knn.classify(sample);
                    //System.out.println(answer + " " + sample.getLabels().get(0));
                    if (answer.equals(sample.getLabels().get(0)))
                        correctAnswers++;
                }
                result = (double) correctAnswers / testSamples.size();
                long endTime = System.nanoTime();
                averageElapsedTime += (endTime - startTime) / 1000000000f;
            }
            averageElapsedTime /= 5;
            DecimalFormat format = new DecimalFormat("#.###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            CSVWriter.appendDataToFile(new CSVData(result, averageElapsedTime), "result.csv");
            System.out.println(result);
            //resultLabel.setText(format.format(result * 100) + "%");
            //timeLabel.setText(format.format(elapsedTime) + "s");
            System.out.println("Done!");
        }
    }
    
    private static KNNClassification initKnn(String category, List<TextSample> testSamples)
            throws ParserConfigurationException, SAXException, IOException {
        System.out.println("Processing...");
        List<TextSample> examples = new ArrayList<>();
        if(category=="PLACES")
        {
            examples = ExampleLoader.loadDataSet("REUTERS", "PLACES");
            examples = ExampleLoader.filterPlaces(examples);

        }
        else if(category=="TOPICS")
        {
            examples = ExampleLoader.loadDataSet("REUTERS", "TOPICS");
            examples = ExampleLoader.filterTopics(examples);
        }
        else if(category=="QUOTES")
        {
            examples = ExampleLoader.loadDataSet("QUOTES", "AUTHOR");
        }
        int count = examples.size();
        int trainingCount = (int) (0.6*count);
        List<TextSample> trainingSamples = examples.subList(0, trainingCount);
        testSamples.addAll(examples.subList(trainingCount, count));
        KNNClassification knn = new KNNClassification();
        knn.init(trainingSamples, 3);
        //testButton.setDisable(false);
        System.out.println("Done!");
        return knn;
    }

    public static void experiment5() throws ParserConfigurationException, SAXException, IOException {

        List<Double> proportions = Arrays.asList(0.3,0.5,0.7);
        System.out.println("Processing...");
        List<TextSample> examples = ExampleLoader.loadDataSet("QUOTES", "AUTHOR");
        for(Double proportion : proportions)
        {

            //examples = ExampleLoader.filterPlaces(examples);
            int count = examples.size();
            int trainingCount = (int) (proportion*count);
            List<TextSample> trainingSamples = examples.subList(0, trainingCount);
            List<TextSample> testSamples = examples.subList(trainingCount, count);
            System.out.println(trainingSamples.size());
            System.out.println(testSamples.size());
            KNNClassification knn = new KNNClassification();
            knn.init(trainingSamples, 3);
            //testButton.setDisable(false);
            System.out.println("Done!");

            CSVWriter.appendStringToFile("EKSPERYMENT 5 - proporcje - reuters, places, 3 slowa kluczowe, k=5", "result.csv");
            double averageElapsedTime = 0;
            double result = 0;
            CSVWriter.appendStringToFile(proportion.toString(), "result.csv");
            for(int i=0; i<5; i++)
            {
                System.out.println("Processing...");
                knn.setK(5);
                CustomExtractor customExtractor = new CustomExtractor();
                customExtractor.initExtractor(knn.getKeywords());
                knn.setFeatureExtractor(customExtractor);
                knn.setMetric(new EuclideanMetric());
                knn.train();
                long startTime = System.nanoTime();
                int correctAnswers = 0;
                for (TextSample sample : testSamples) {
                    String answer = knn.classify(sample);
                    //System.out.println(answer + " " + sample.getLabels().get(0));
                    if (answer.equals(sample.getLabels().get(0)))
                        correctAnswers++;
                }
                result = (double) correctAnswers / testSamples.size();
                long endTime = System.nanoTime();
                averageElapsedTime += (endTime - startTime) / 1000000000f;
                //System.out.println(averageElapsedTime);
            }
            averageElapsedTime /= 5;
            DecimalFormat format = new DecimalFormat("#.###");
            format.setRoundingMode(RoundingMode.HALF_UP);
            CSVWriter.appendDataToFile(new CSVData(result, averageElapsedTime), "result.csv");
            System.out.println(result);
            //resultLabel.setText(format.format(result * 100) + "%");
            //timeLabel.setText(format.format(elapsedTime) + "s");
            System.out.println("Done!");
        }
    }
}

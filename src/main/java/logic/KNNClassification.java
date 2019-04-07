package logic;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class KNNClassification
{
    private ArrayList<TrainingExample> trainingExamples;
    private ArrayList<TextSample> trainingTextSamples = new ArrayList<>();
    private FeatureExtractor featureExtractor;
    private Metric metric;
    private int k;

    public KNNClassification(int k)
    {
        this.k = k;
    }

    public void train(List<TextSample> samples)
    {
        //TODO do something with that 3
        trainingTextSamples.addAll(samples);
        featureExtractor.findKeywords(samples, 3);
        System.out.println(featureExtractor.keywords);
        featureExtractor.initExtractor();
        trainingExamples = new ArrayList<>();
        for(TextSample sample : samples)
        {
            trainingExamples.add(featureExtractor.extractFeatures(samples, sample));
        }
    }

    public String classify(TextSample testSample)
    {
        String result = "";
        TrainingExample testExample = featureExtractor.extractFeatures(trainingTextSamples, testSample);
        trainingExamples.sort(Comparator.comparingDouble(a -> metric.getDistance(a.getFeatures(), testExample.getFeatures())));
        HashMap<String, Integer> answers = new HashMap<String, Integer>();
        for(int i=0; i<k; i++)
        {
            String label = trainingExamples.get(i).getLabel();
            if(answers.containsKey(label)) answers.put(label, answers.get(label)+1);
            else answers.put(label, 1);
        }
        int maxFrequency = 0;
        for(Map.Entry<String, Integer> entry : answers.entrySet())
        {
            if(maxFrequency<entry.getValue())
            {
                result = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return result;
    }
}

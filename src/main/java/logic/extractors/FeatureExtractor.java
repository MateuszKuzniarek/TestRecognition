package logic.extractors;

import logic.classification.TextSample;
import logic.classification.TrainingExample;
import logic.features.Feature;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureExtractor
{
    //protected List<TrainingExample> trainingExamples;
    ArrayList<Feature> features = new ArrayList<>();

    public TrainingExample extractFeatures(List<TextSample> allSamples, TextSample sample)
    {
        TrainingExample trainingExample = new TrainingExample();
        trainingExample.setLabel(sample.getLabels().get(0));
        for(Feature feature : features)
        {
            trainingExample.getFeatures().add(feature.extractFeature(allSamples, sample));
        }
        return trainingExample;
    }

    public abstract void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel);

    /*
    public List<TrainingExample> getTrainingExamples() {
        return trainingExamples;
    }

    public void setTrainingExamples(List<TrainingExample> trainingExamples)
    {
        this.trainingExamples = trainingExamples;
    }
    */



    /*private HashMap<String, HashMap<String, Integer>> getWordsForLabel(List<TextSample> samples, List<String> labels)
    {
        HashMap<String, HashMap<String, Integer>> wordsForLabel = new HashMap<>();
        for(String label : labels)
        {
            wordsForLabel.put(label, new HashMap<>());
        }

        for(TextSample sample : samples)
        {
            for(String word : sample.getWords())
            {
                HashMap<String, Integer> wordMap = wordsForLabel.get(sample.getLabels().get(0));
                if(wordMap.containsKey(word)) wordMap.put(word, wordMap.get(word)+1);
                else wordMap.put(word, 1);
            }
        }
        return wordsForLabel;
    }*/


}
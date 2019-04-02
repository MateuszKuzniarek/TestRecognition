package logic;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class FeatureExtractor
{
    //protected List<TrainingExample> trainingExamples;
    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<Feature> features = new ArrayList<>();

    public TrainingExample extractFeatures(List<TextSample> allSamples, TextSample sample)
    {
        TrainingExample trainingExample = new TrainingExample();
        for(Feature feature : features)
        {
            trainingExample.getFeatures().add(feature.extractFeature(allSamples, sample));
        }
        return trainingExample;
    }

    public abstract void initExtractor();

    /*
    public List<TrainingExample> getTrainingExamples() {
        return trainingExamples;
    }

    public void setTrainingExamples(List<TrainingExample> trainingExamples)
    {
        this.trainingExamples = trainingExamples;
    }
    */

    private ArrayList<String> getLabels(List<TextSample> samples)
    {
        ArrayList<String> labels = new ArrayList<>();
        for(TextSample sample : samples)
        {
            if(!labels.contains(sample.getLabels().get(0))) labels.add(sample.getLabels().get(0));
        }
        return labels;
    }

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

    private HashMap<String, ArrayList<String>> getWordsForLabel(List<TextSample> samples, List<String> labels)
    {
        HashMap<String, ArrayList<String>> wordsForLabel = new HashMap<>();
        for(String label : labels) wordsForLabel.put(label, new ArrayList<>());

        for(TextSample sample : samples)
        {
            wordsForLabel.get(sample.getLabels().get(0)).addAll(sample.getWords());
        }
        return wordsForLabel;
    }

    private boolean checkForWord(String word, ArrayList<Pair<String, Integer>> words)
    {
        for(Pair<String, Integer> pair : words)
        {
            if(word.equals(pair.getKey())) return true;
        }
        return false;
    }

    protected void findKeywords(List<TextSample> samples, int n)
    {
        ArrayList<String> labels = getLabels(samples);
        HashMap<String, ArrayList<String>> wordsForLabel = getWordsForLabel(samples, labels);
        for(String label : wordsForLabel.keySet())
        {
            ArrayList<Pair<String, Integer>> words = new ArrayList<>();
            for(String word : wordsForLabel.get(label))
            {
                if(!checkForWord(word, words))
                {
                    words.add(new Pair(word, Collections.frequency(wordsForLabel.get(label), word)));
                }
            }
            Collections.sort(words, Comparator.comparingInt(Pair::getValue));
            for(int i=0; i<n; i++) keywords.add(words.get(words.size()-1-i).getKey());
        }
    }
}
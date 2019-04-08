package logic.extractors;

import logic.classification.TextSample;
import logic.classification.TrainingExample;
import logic.features.Feature;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureExtractor
{
    ArrayList<Feature> features = new ArrayList<>();

    public abstract void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel);

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

}
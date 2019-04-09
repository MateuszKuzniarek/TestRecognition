package logic.extractors;

import logic.classification.ExtractedSample;
import logic.classification.TextSample;
import logic.features.Feature;

import java.util.ArrayList;
import java.util.List;

public abstract class FeatureExtractor
{
    ArrayList<Feature> features = new ArrayList<>();

    public abstract void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel);

    public ExtractedSample extractFeatures(List<TextSample> allSamples, TextSample sample)
    {
        ExtractedSample extractedSample = new ExtractedSample();
        extractedSample.setLabel(sample.getLabels().get(0));
        for(Feature feature : features)
        {
            extractedSample.getFeatures().add(feature.extractFeature(allSamples, sample));
        }
        return extractedSample;
    }

}
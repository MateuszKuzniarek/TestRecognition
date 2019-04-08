package logic.features;

import logic.classification.TextSample;

import java.util.ArrayList;
import java.util.List;

public class CombinedNGram extends Feature
{
    private List<NGram> termFrequencyFeatures = new ArrayList<>();

    public CombinedNGram(List<String> keywords, int n)
    {
        for(String keyword : keywords)
        {
            termFrequencyFeatures.add(new NGram(n, keyword));
        }
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double sum = 0;
        for(NGram nGram : termFrequencyFeatures)
        {
            sum += nGram.extractFeature(allSamples, sample);
        }
        return sum;
    }
}

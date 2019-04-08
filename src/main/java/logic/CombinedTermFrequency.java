package logic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CombinedTermFrequency extends Feature
{
    private List<TermFrequency> termFrequencyFeatures = new ArrayList<>();

    public CombinedTermFrequency(List<String> keywords)
    {
        for(String keyword : keywords)
        {
            termFrequencyFeatures.add(new TermFrequency(keyword));
        }
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double sum = 0;
        for(TermFrequency termFrequency : termFrequencyFeatures)
        {
            sum += termFrequency.extractFeature(allSamples, sample);
        }
        return sum;
    }
}
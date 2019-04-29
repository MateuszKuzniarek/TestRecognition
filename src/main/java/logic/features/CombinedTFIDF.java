package logic.features;

import logic.classification.TextSample;

import java.util.ArrayList;
import java.util.List;

public class CombinedTFIDF extends Feature
{
    private List<TermFrequencyInverseDocumentFrequency> tFIDFFeatures = new ArrayList<>();

    public CombinedTFIDF(List<String> keywords)
    {
        for(String keyword : keywords)
        {
            tFIDFFeatures.add(new TermFrequencyInverseDocumentFrequency(keyword));
        }
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double sum = 0;
        for(TermFrequencyInverseDocumentFrequency tFIDF : tFIDFFeatures)
        {
            sum += tFIDF.extractFeature(allSamples, sample);
        }
        return sum;
    }
}

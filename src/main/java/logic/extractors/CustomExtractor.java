package logic.extractors;

import logic.features.CombinedNGram;
import logic.features.Length;
import logic.features.AverageNumber;
import logic.features.CombinedTermFrequency;
import logic.features.LongestWordsAverageLength;

import java.util.List;

public class CustomExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel)
    {
        features.add(new AverageNumber());
        features.add(new Length());
        features.add(new LongestWordsAverageLength());
        for(int i=0; i<keywords.size(); i+=numberOfKeywordsPerLabel)
        {
            List<String> keywordsForLabel = keywords.subList(i, i+numberOfKeywordsPerLabel);
            features.add(new CombinedTermFrequency(keywordsForLabel));
            features.add(new CombinedNGram(keywordsForLabel, 3));
        }
    }

    @Override
    public String toString() {
        return "Własny Ekstraktor";
    }
}

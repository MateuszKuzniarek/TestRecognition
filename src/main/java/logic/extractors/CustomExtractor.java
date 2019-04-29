package logic.extractors;

import logic.features.CombinedNGram;
import logic.features.Length;
import logic.features.AverageNumber;
import logic.features.CombinedTermFrequency;
import logic.features.LongestWordsAverageLength;
import logic.features.TermFrequency;

import java.util.List;

public class CustomExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords)
    {
        features.add(new AverageNumber());
        features.add(new Length());
        features.add(new LongestWordsAverageLength());
        features.add(new CombinedNGram(keywords, 3));
        for(int i=0; i<keywords.size(); i++)
        {
            features.add(new TermFrequency(keywords.get(i)));
        }
    }

    @Override
    public String toString() {
        return "Własny Ekstraktor";
    }
}

package logic.extractors;

import logic.features.CombinedNGram;
import logic.features.GeneralizedNGram;
import logic.features.Length;
import logic.features.AverageNumber;
import logic.features.CombinedTermFrequency;
import logic.features.LongestWordsAverageLength;
import logic.features.NGram;
import logic.features.TermFrequency;
import logic.features.TermFrequencyInverseDocumentFrequency;

import java.util.List;

public class CustomExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords)
    {
        features.add(new AverageNumber());
        features.add(new Length());
        features.add(new LongestWordsAverageLength());
        for(int i=0; i<keywords.size(); i++)
        {
            features.add(new TermFrequency(keywords.get(i)));
            features.add(new NGram(3, keywords.get(i)));
            features.add(new GeneralizedNGram(keywords.get(i)));
        }
    }

    @Override
    public String toString() {
        return "Własny Ekstraktor";
    }
}

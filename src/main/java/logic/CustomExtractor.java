package logic;

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
            features.add(new CombinedTermFrequency(keywords.subList(i, i+numberOfKeywordsPerLabel)));
        }
    }
}

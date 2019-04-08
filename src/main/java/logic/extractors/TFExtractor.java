package logic.extractors;


import logic.features.TermFrequency;

import java.util.List;

public class TFExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel)
    {
        for(String keyword: keywords)
        {
            features.add(new TermFrequency(keyword));
        }
    }

    @Override
    public String toString() {
        return "Ekstraktor TF";
    }
}

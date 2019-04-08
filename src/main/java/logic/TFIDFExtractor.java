package logic;

import java.util.List;

public class TFIDFExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords, int numberOfKeywordsPerLabel)
    {
        for(String keyword: keywords)
        {
            features.add(new TermFrequencyInverseDocumentFrequency(keyword));
        }
    }

    @Override
    public String toString() {
        return "Ekstraktor TFIDF";
    }
}
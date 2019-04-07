package logic;

public class TFIDFExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor()
    {
        for(String keyword: keywords)
        {
            features.add(new TermFrequencyInverseDocumentFrequency(keyword));
        }
    }
}
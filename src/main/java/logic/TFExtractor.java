package logic;


import java.util.List;

public class TFExtractor extends FeatureExtractor
{
    @Override
    public void initExtractor(List<String> keywords)
    {
        for(String keyword: keywords)
        {
            features.add(new TermFrequency(keyword));
        }
    }
}

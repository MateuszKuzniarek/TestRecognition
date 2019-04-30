package logic.features;

import logic.classification.TextSample;

import java.util.List;

public class GeneralizedNGram extends Feature
{
    String keyword;

    public GeneralizedNGram(String keyword)
    {
        this.keyword = keyword;
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double numberOfSubseries = 0;
        for(int i=0; i<keyword.length(); i++)
        {
            numberOfSubseries += NGram.getNumberOfSubseries(keyword, i+1, sample);
        }
        return (2*numberOfSubseries)/(keyword.length()*keyword.length()+keyword.length());
    }
}

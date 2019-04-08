package logic.features;

import logic.classification.TextSample;

import java.util.List;

public class NGram extends Feature
{
    int nCoefficient;
    String keyword;

    public NGram(int n, String keyword)
    {
        this.nCoefficient = n;
        this.keyword = keyword;
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        int nGramLimit = keyword.length()-nCoefficient;
        double numberOfPresentSubSeries = 0;
        for(int i=0; i<nGramLimit; i++)
        {
            String subSeries = keyword.substring(i, i+nCoefficient);
            if(sample.getWords().contains(subSeries)) numberOfPresentSubSeries++;
        }
        return numberOfPresentSubSeries/(nGramLimit+1);
    }
}

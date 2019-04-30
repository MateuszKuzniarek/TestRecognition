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

    public static double getNumberOfSubseries(String keyword, int nCoefficient, TextSample sample)
    {
        int nGramLimit = keyword.length()-nCoefficient;
        double numberOfPresentSubSeries = 0;
        for(int i=0; i<nGramLimit; i++)
        {
            String subSeries = keyword.substring(i, i+nCoefficient);
            boolean isSubseriesFound = false;
            for(int j=0; j<sample.getWords().size() && !isSubseriesFound; j++)
            {
                if(sample.getWords().get(j).contains(subSeries)) isSubseriesFound = true;
            }
            if(isSubseriesFound) numberOfPresentSubSeries++;
        }
        return numberOfPresentSubSeries;
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double numberOfPresentSubSeries = NGram.getNumberOfSubseries(keyword, nCoefficient, sample);
        return numberOfPresentSubSeries/(keyword.length()-nCoefficient+1);
    }
}

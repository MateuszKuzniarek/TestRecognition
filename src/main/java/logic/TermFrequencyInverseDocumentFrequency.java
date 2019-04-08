package logic;

import java.util.Collections;
import java.util.List;

public class TermFrequencyInverseDocumentFrequency extends Feature
{

    private String keyword;

    public TermFrequencyInverseDocumentFrequency(String keyword)
    {
        this.keyword = keyword;
    }

    private double calculateTF(TextSample sample, String word)
    {
        double frequency = Collections.frequency(sample.getWords(), word);
        double numberOfWords = sample.getWords().size();
        return frequency/numberOfWords;
    }

    private double calculateIDF(List<TextSample> allSamples, String word)
    {
        double counter = 0;
        for(TextSample sample : allSamples)
        {
            if(sample.getWords().contains(word)) counter++;
        }
        return Math.log(allSamples.size()/counter);
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        return (calculateTF(sample, keyword) * calculateIDF(allSamples, keyword));
    }
}

package logic.features;

import logic.classification.TextSample;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class TermFrequency extends Feature
{
    private String keyword;

    public TermFrequency(String keyword)
    {
        this.keyword = keyword;
    }

    private double calculateTF(TextSample sample, String word)
    {
        double frequency = Collections.frequency(sample.getWords(), word);
        double numberOfWords = sample.getWords().size();
        return frequency/numberOfWords;
    }

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        return calculateTF(sample, getKeyword());
    }
}

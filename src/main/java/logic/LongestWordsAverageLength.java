package logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LongestWordsAverageLength extends Feature
{
    private static final int numberOfLongestWords = 4;

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample) {
        List<String> words = sample.getWords();
        Collections.sort(words, Comparator.comparingInt(String::length));
        double sum = 0;
        for(int i=0; i<numberOfLongestWords && i<words.size(); i++)
        {
            sum += words.get(words.size()-1-i).length();
        }
        return sum/numberOfLongestWords;
    }
}

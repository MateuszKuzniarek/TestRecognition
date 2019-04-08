package logic.features;

import logic.classification.TextSample;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class AverageNumber extends Feature
{
    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        double sum = 0;
        int numberOfNumbers = 0;
        for(String word : sample.getWords())
        {
            if(NumberUtils.isNumber(word))
            {
                sum += Double.parseDouble(word);
                numberOfNumbers++;
            }
        }
        return sum/numberOfNumbers;
    }
}

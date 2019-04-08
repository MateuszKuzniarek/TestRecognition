package logic.features;

import logic.classification.TextSample;

import java.util.List;

public class Length extends Feature
{

    @Override
    public double extractFeature(List<TextSample> allSamples, TextSample sample)
    {
        return sample.getWords().size();
    }
}

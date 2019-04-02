package logic;

import java.util.List;

public abstract class Feature
{
    public abstract double extractFeature(List<TextSample> allSamples, TextSample sample);
}

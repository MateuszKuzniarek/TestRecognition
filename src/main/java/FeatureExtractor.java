import java.util.List;

public abstract class FeatureExtractor
{
    //used for training
    public abstract List<TrainingExample> extractFeatures(List<TextSample> samples);

    //used for testing
    public abstract TrainingExample extractFeatures(TextSample samples);
}

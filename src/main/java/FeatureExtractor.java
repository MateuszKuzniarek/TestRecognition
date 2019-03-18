import java.util.List;

public abstract class FeatureExtractor
{
    protected List<TrainingExample> trainingExamples;

    //used for training
    public abstract void train(List<TextSample> samples);

    //used for testing
    public abstract TrainingExample extractFeatures(TextSample sample);

    public List<TrainingExample> getTrainingExamples() {
        return trainingExamples;
    }

    public void setTrainingExamples(List<TrainingExample> trainingExamples) {
        this.trainingExamples = trainingExamples;
    }
}

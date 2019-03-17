import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNClassification
{
    private List<TrainingExample> trainingExamples;
    private FeatureExtractor featureExtractor;
    private Metric metric;

    public String classify(TextSample testSample, int k)
    {
        String result = "";
        TrainingExample testExample = featureExtractor.extractFeatures(testSample);
        trainingExamples.sort(Comparator.comparingDouble(a -> metric.getDistance(a.getFeatures(), testExample.getFeatures())));
        HashMap<String, Integer> answers = new HashMap<String, Integer>();
        for(int i=0; i<k; i++)
        {
            String label = trainingExamples.get(i).getLabel();
            if(answers.containsKey(label)) answers.put(label, answers.get(label)+1);
            else answers.put(label, 1);
        }
        int maxFrequency = 0;
        for(Map.Entry<String, Integer> entry : answers.entrySet())
        {
            if(maxFrequency<entry.getValue())
            {
                result = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return result;
    }
}

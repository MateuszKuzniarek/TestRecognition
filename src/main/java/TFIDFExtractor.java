import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TFIDFExtractor extends FeatureExtractor
{
    int numberOfFeatures;
    //todo
    //int groupLevel;
    List<String> features = new ArrayList<>();
    List<Double> idfs = new ArrayList<>();


    public TFIDFExtractor(int numberOfFeatures)
    {
        this.numberOfFeatures = numberOfFeatures;
        //this.groupLevel = groupLevel;
    }

    private double calculateTF(TextSample sample, String word)
    {
        double frequency = Collections.frequency(sample.getWords(), word);
        double numberOfWords = sample.getWords().size();
        return frequency/numberOfWords;
    }

    private void calculateValueForAllSamples(List<TextSample> samples, String word)
    {
        int numberOfSamplesWithGivenWord = 0;
        for(TextSample sample : samples)
        {
            if(sample.getWords().contains(word)) numberOfSamplesWithGivenWord++;
        }
        double idf = (double)samples.size()/numberOfSamplesWithGivenWord;
        idfs.add(idf);
        for(int i=0; i<samples.size(); i++)
        {
            double tf = calculateTF(samples.get(i), word);
            double tfidf = tf*idf;
            trainingExamples.get(i).getFeatures().add(tfidf);
        }
    }

    private void createTrainingExamples(List<TextSample> samples)
    {
        trainingExamples = new ArrayList<TrainingExample>();

        for(TextSample sample : samples)
        {
            TrainingExample newExample = new TrainingExample();
            // get(0) ???
            newExample.setLabel(sample.getLabels().get(0));
            trainingExamples.add(newExample);
        }
    }

    @Override
    public void train(List<TextSample> samples)
    {
        createTrainingExamples(samples);
        for(TextSample sample : samples)
        {
            for(String word : sample.getWords())
            {
                if(!features.contains(word))
                {
                    features.add(word);
                    calculateValueForAllSamples(samples, word);
                }
            }
        }
    }

    @Override
    public TrainingExample extractFeatures(TextSample sample)
    {
        TrainingExample result = new TrainingExample();
        for(int i=0; i<features.size(); i++)
        {
            double tf = calculateTF(sample, features.get(i));
            double tfidf = tf* idfs.get(i);
            result.getFeatures().add(tfidf);
        }
        return result;
    }
}

package logic.classification;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Normalizer {
    private List<Double> minimums;
    private List<Double> maximums;
    private int numberOfFeatures;

    public Normalizer() {
        minimums = new ArrayList<>();
        maximums = new ArrayList<>();
    }

    public void initializeWages(List<TrainingExample> trainingExamples) {
        numberOfFeatures = trainingExamples.get(0).getFeatures().size();

        for (int i = 0; i < numberOfFeatures; i++) {
                minimums.add(Double.MAX_VALUE);
                maximums.add(Double.MIN_VALUE);
            for (TrainingExample trainingExample : trainingExamples) {
                double feature = trainingExample.getFeatures().get(i);
                minimums.set(i, Double.min(minimums.get(i), feature));
                maximums.set(i, Double.max(maximums.get(i), feature));
            }
        }
    }

    public void normalize(TrainingExample trainingExample) {
        for (int i = 0; i < numberOfFeatures; i++)
        {
            double divisor = maximums.get(i) - minimums.get(i);

            if (divisor != 0)
            {
                double newValue = (trainingExample.getFeatures().get(i) - minimums.get(i)) / divisor;
                trainingExample.getFeatures().set(i, newValue);
                //System.out.println(newValue);
            }
            else
            {
                trainingExample.getFeatures().set(i, 0d);
                //System.out.println(trainingExample.getFeatures().get(i));
            }
        }
    }
}
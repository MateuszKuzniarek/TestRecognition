package logic;

import java.util.List;

public class EuclideanMetric extends Metric
{

    public double getDistance(List<Double> point1, List<Double> point2)
    {
        double sum = 0;
        for(int i=0; i<point1.size(); i++)
        {
            double difference = point1.get(i) - point2.get(i);
            sum += difference*difference;
        }
        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        return "Metryka Euklidesowa";
    }
}

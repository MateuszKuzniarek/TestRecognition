package logic.metrics;

import java.util.List;

public abstract class Similiarity extends Metric
{
    public abstract double getSimilarity(List<Double> point1, List<Double> point2);

    public double getDistance(List<Double> point1, List<Double> point2)
    {
        double result = 1/getSimilarity(point1, point2) - 1;
        return result;
    }
}

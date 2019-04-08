package logic;

import java.util.List;

public class ChebyshevMetric extends Metric
{
    @Override
    public double getDistance(List<Double> point1, List<Double> point2)
    {
        double maxDifference = 0;
        for(int i=0; i<point1.size(); i++)
        {
            double difference = Math.abs(point1.get(i) - point2.get(i));
            if(difference > maxDifference) maxDifference = difference;
        }
        return maxDifference;
    }

    @Override
    public String toString() {
        return "Metryka Czebyszewa";
    }
}

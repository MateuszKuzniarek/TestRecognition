package logic;

import java.util.List;

public class ManhattanMetric extends Metric
{

    @Override
    public double getDistance(List<Double> point1, List<Double> point2)
    {
        double result = 0;
        for(int i=0; i<point1.size(); i++)
        {
            result += Math.abs(point1.get(i) - point2.get(i));
        }
        return result;
    }
}

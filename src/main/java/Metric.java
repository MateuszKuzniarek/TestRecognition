import java.util.List;

public abstract class Metric
{
    public abstract double getDistance(List<Double> point1, List<Double> point2);
}

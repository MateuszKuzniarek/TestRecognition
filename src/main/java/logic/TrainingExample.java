package logic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingExample
{
    private String label;
    private List<Double> features = new ArrayList<>();
}

package logic.classification;

import javafx.print.Collation;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class TrainingExample
{
    private String label;
    private List<Double> features = new ArrayList<>();
}

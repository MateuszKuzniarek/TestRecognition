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

    public void normalize()
    {
        double maximum = Collections.max(features);
        double minimum = Collections.min(features);

        System.out.println(features.get(1));

        for (int i = 0; i<features.size(); i++)
        {
            if(maximum - minimum != 0) features.set(i, (features.get(i) - minimum)/(maximum - minimum));
        }

        System.out.println(features.get(1));
    }
}

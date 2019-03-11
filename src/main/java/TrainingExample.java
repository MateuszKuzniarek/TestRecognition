import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingExample
{
    private List<String> labels = new ArrayList<String>();
    private String text;
}

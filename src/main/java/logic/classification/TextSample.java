package logic.classification;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextSample
{
    private List<String> labels = new ArrayList<String>();
    private List<String> words;

}

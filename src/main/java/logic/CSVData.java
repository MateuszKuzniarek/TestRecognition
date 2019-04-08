package logic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CSVData
{
    private double efficiency;
    private double elapsedTime;

    @Override
    public String toString()
    {
        return efficiency + "\t" + elapsedTime;
    }
}

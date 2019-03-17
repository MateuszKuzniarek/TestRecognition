import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello, World");
        try
        {
            List<TextSample> examples = ExampleLoader.loadFromAllFiles("PLACES");
            examples = ExampleLoader.filterPlaces(examples);
            for(TextSample example : examples) System.out.println(example);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}

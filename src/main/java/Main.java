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
            //for(int i=10; i<22; i++) SgmToXmlConverter.convertToXml("./examples/sgmFiles/reut2-0" + i + ".sgm");
            List<TextSample> examples = ExampleLoader.loadFromXmlFile("PLACES", "./examples/sgmFiles/reut2-000.xml");
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

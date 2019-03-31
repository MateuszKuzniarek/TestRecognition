package logic;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SgmToXmlConverter
{
    public static void convertToXml(String path) throws IOException {
        FileWriter writer = new FileWriter(path.substring(0, path.length()-4) + ".xml");
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n");
        writer.write("<examples>");
        String content = FileUtils.readFileToString(new File(path), "UTF-8");
        content = content.replaceAll("&([^<]*);", "");
        content = content.replaceAll("<!DOCTYPE lewis SYSTEM \"lewis.dtd\">", "");
        writer.write(content);
        writer.write("</examples>");
        writer.close();
    }
}

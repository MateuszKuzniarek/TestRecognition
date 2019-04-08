package logic.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SgmToXmlConverter
{
    private static String convertFileContent(String path) throws IOException
    {
        String content = FileUtils.readFileToString(new File(path), "UTF-8");
        content = content.replaceAll("&([^<]*);", "");
        content = content.replaceAll("<!DOCTYPE lewis SYSTEM \"lewis.dtd\">", "");
        return content;
    }

    public static void convertToXml(List<String> paths, String targetPath) throws IOException
    {
        FileWriter writer = new FileWriter(targetPath);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n");
        writer.write("<examples>");
        for(String path : paths)
        {
            String content = convertFileContent(path);
            writer.write(content);
        }
        writer.write("</examples>");
        writer.close();
    }
}

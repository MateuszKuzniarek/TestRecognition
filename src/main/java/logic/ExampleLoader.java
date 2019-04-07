package logic;

import jdk.nashorn.internal.parser.TokenStream;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleLoader
{
    private static List<String> ignoredWords = Arrays.asList("i", "me", "my", "myself", "we", "our", "ours", "ourselves",
            "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers",
            "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who",
            "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have",
            "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because",
            "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through",
            "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over",
            "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all",
            "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own",
            "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now", "", " ", "said",
            "mln", "pct", "vs", "billion", "bank", "market", "year", "be", "issu", "net", "reuter", "rate", "with",
            "as", "was", "tax", "bond", "price", "hous", "govern", "money", "trade", "secur", "manag", "industri", "last");

    private static List<String> punctuationMarks = Arrays.asList(",", ".", "?", "!", "'", "\"", "/", "\\", "");
    private static List<String> allowedPlaces = Arrays.asList("west-germany", /*"usa",*/ "france", "uk", "canada", "japan");

    private static String removePunctuationMarks(String text)
    {
        String result = text;
        result = result.replace("\n", " ");
        for(String punctuationMark : punctuationMarks)
        {
            result = result.replace(punctuationMark, "");
        }
        return result;
    }

    private static List<String> getWords(String text)
    {
        text = text.toLowerCase();
        text = removePunctuationMarks(text);
        ArrayList<String> result = new ArrayList<String>(Arrays.asList(text.split(" ")));
        SnowballStemmer stemmer = new englishStemmer();
        for(int i=0; i<result.size(); i++)
        {
            stemmer.setCurrent(result.get(i));
            stemmer.stem();
            result.set(i, stemmer.getCurrent());
        }
        result.removeAll(ignoredWords);
        //System.out.println(result);
        return result;
    }

    public static List<TextSample> loadFromXmlFile(String labelName, String filePath) throws IOException, SAXException, ParserConfigurationException
    {
        ArrayList<TextSample> examples = new ArrayList<TextSample>();
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        NodeList reuters = document.getElementsByTagName("REUTERS");
        for(int i=0; i<reuters.getLength(); i++)
        {
            Element element = (Element) reuters.item(i);
            TextSample example = new TextSample();
            Element textElement = (Element) element.getElementsByTagName("TEXT").item(0);
            Node textNode = textElement.getElementsByTagName("BODY").item(0);
            Node titleNode = textElement.getElementsByTagName("TITLE").item(0);
            String text = "";
            if(textNode!=null) text += textNode.getTextContent();
            if(titleNode!=null) text += titleNode.getTextContent();
            example.setWords(getWords(text));
            Node labelNode = element.getElementsByTagName(labelName).item(0);
            Element labelElement = (Element) labelNode;
            NodeList labels = labelElement.getElementsByTagName("D");
            if(labels.getLength()>0)
            {
                for(int j=0; j<labels.getLength(); j++)
                {
                    example.getLabels().add(labels.item(j).getTextContent());
                }
            }
            else example.getLabels().add(labelNode.getTextContent());
            examples.add(example);
        }
        return examples;
    }

    public static List<TextSample> filterPlaces(List<TextSample> samples)
    {
        List<TextSample> result = new ArrayList<TextSample>();
        for(TextSample sample : samples)
        {
            if(sample.getLabels().size() == 1 && allowedPlaces.contains(sample.getLabels().get(0)))
            {
                result.add(sample);
            }
        }
        return result;
    }

    public static List<TextSample> loadFromAllFiles(String labelName) throws ParserConfigurationException, SAXException, IOException {
        List<TextSample> result = new ArrayList<TextSample>();
        String path = "";
        for(int i=0; i<22; i++)
        {
             result.addAll(loadFromXmlFile(labelName, "./examples/sgmFiles/reut2-0" + String.format("%02d", i) + ".xml"));
        }
        return result;
    }
}

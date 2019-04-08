package logic.utils;

import logic.classification.TextSample;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            "as", "was", "tax", "bond", "price", "hous", "govern", "money", "trade", "secur", "manag", "industri", "last",
            "man", "one", "even", "littl", "shall", "onli", "thing", "know", "look", "truth", "make", "like", "minor",
            "major", "object", "perhap", "tell", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "great", "say", "think", "also", "take", "told", "thou", "a","about","above","after","again","against",
            "ain","all","am","an","and","any","are","aren","aren't","as","at","be","because","been","before","being",
            "below","between","both","but","by","can","couldn","couldn't","d","did","didn","didn't","do","does","doesn",
            "doesn't","doing","don","don't","down","during","each","few","for","from","further","had","hadn","hadn't",
            "has","hasn","hasn't","have","haven","haven't","having","he","her","here","hers","herself","him","himself",
            "his","how","i","if","in","into","is","isn","isn't","it","it's","its","itself","just","ll","m","ma","me",
            "mightn","mightn't","more","most","mustn","mustn't","my","myself","needn","needn't","no","nor","not","now",
            "o","of","off","on","once","only","or","other","our","ours","ourselves","out","over","own","re","s","same",
            "shan","shan't","she","she's","should","should've","shouldn","shouldn't","so","some","such","t","than",
            "that","that'll","the","their","theirs","them","themselves","then","there","these","they","this","those",
            "through","to","too","under","until","up","ve","very","was","wasn","wasn't","we","were","weren","weren't",
            "what","when","where","which","while","who","whom","why","will","with","won","won't","wouldn","wouldn't",
            "y","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves","could","he'd","he'll",
            "he's","here's","how's","i'd","i'll","i'm","i've","let's","ought","she'd","she'll","that's","there's",
            "they'd","they'll","they're","they've","we'd","we'll","we're","we've","what's","when's","where's","who's",
            "why's","would","able","abst","accordance","according","accordingly","across","act","actually","added","adj",
            "affected","affecting","affects","afterwards","ah","almost","alone","along","already","also","although",
            "always","among","amongst","announce","another","anybody","anyhow","anymore","anyone","anything","anyway",
            "anyways","anywhere","apparently","approximately","arent","arise","around","aside","ask","asking","auth",
            "available","away","awfully","b","back","became","become","becomes","becoming","beforehand","begin",
            "beginning","beginnings","begins","behind","believe","beside","besides","beyond","biol","brief","briefly",
            "c","ca","came","cannot","can't","cause","causes","certain","certainly","co","com","come","comes","contain",
            "containing","contains","couldnt","date","different","done","downwards","due","e","ed","edu","effect","eg",
            "eight","eighty","either","else","elsewhere","end","ending","enough","especially","et","etc","even","ever",
            "every","everybody","everyone","everything","everywhere","ex","except","f","far","ff","fifth","first","five",
            "fix","followed","following","follows","former","formerly","forth","found","four","furthermore","g","gave",
            "get","gets","getting","give","given","gives","giving","go","goes","gone","got","gotten","h","happens",
            "hardly","hed","hence","hereafter","hereby","herein","heres","hereupon","hes","hi","hid","hither","home",
            "howbeit","however","hundred","id","ie","im","immediate","immediately","importance","important","inc",
            "indeed","index","information","instead","invention","inward","itd","it'll","j","k","keep","keeps","kept",
            "kg","km","know","known","knows","l","largely","last","lately","later","latter","latterly","least","less",
            "lest","let","lets","like","liked","likely","line","little","'ll","look","looking","looks","ltd","made",
            "mainly","make","makes","many","may","maybe","mean","means","meantime","meanwhile","merely","mg","might",
            "million","miss","ml","moreover","mostly","mr","mrs","much","mug","must","n","na","name","namely","nay","nd",
            "near","nearly","necessarily","necessary","need","needs","neither","never","nevertheless","new","next",
            "nine","ninety","nobody","non","none","nonetheless","noone","normally","nos","noted","nothing","nowhere",
            "obtain","obtained","obviously","often","oh","ok","okay","old","omitted","one","ones","onto","ord","others",
            "otherwise","outside","overall","owing","p","page","pages","part","particular","particularly","past","per",
            "perhaps","placed","please","plus","poorly","possible","possibly","potentially","pp","predominantly",
            "present","previously","primarily","probably","promptly","proud","provides","put","q","que","quickly",
            "quite","qv","r","ran","rather","rd","readily","really","recent","recently","ref","refs","regarding",
            "regardless","regards","related","relatively","research","respectively","resulted","resulting","results",
            "right","run","said","saw","say","saying","says","sec","section","see","seeing","seem","seemed","seeming",
            "seems","seen","self","selves","sent","seven","several","shall","shed","shes","show","showed","shown",
            "showns","shows","significant","significantly","similar","similarly","since","six","slightly","somebody",
            "somehow","someone","somethan","something","sometime","sometimes","somewhat","somewhere","soon","sorry",
            "specifically","specified","specify","specifying","still","stop","strongly","sub","substantially",
            "successfully","sufficiently","suggest","sup","sure","take","taken","taking","tell","tends","th","thank",
            "thanks","thanx","thats","that've","thence","thereafter","thereby","thered","therefore","therein",
            "there'll","thereof","therere","theres","thereto","thereupon","there've","theyd","theyre","think","thou",
            "though","thoughh","thousand","throug","throughout","thru","thus","til","tip","together","took","toward",
            "towards","tried","tries","truly","try","trying","ts","twice","two","u","un","unfortunately","unless",
            "unlike","unlikely","unto","upon","ups","us","use","used","useful","usefully","usefulness","uses","using",
            "usually","v","value","various","'ve","via","viz","vol","vols","vs","w","want","wants","wasnt","way","wed",
            "welcome","went","werent","whatever","what'll","whats","whence","whenever","whereafter","whereas","whereby",
            "wherein","wheres","whereupon","wherever","whether","whim","whither","whod","whoever","whole","who'll",
            "whomever","whos","whose","widely","willing","wish","within","without","wont","words","world","wouldnt",
            "www","x","yes","yet","youd","youre","z","zero","a's","ain't","allow","allows","apart","appear","appreciate",
            "appropriate","associated","best","better","c'mon","c's","cant","changes","clearly","concerning",
            "consequently","consider","considering","corresponding","course","currently","definitely","described",
            "despite","entirely","exactly","example","going","greetings","hello","help","hopefully","ignored","inasmuch",
            "indicate","indicated","indicates","inner","insofar","it'd","keep","keeps","novel","presumably","reasonably",
            "second","secondly","sensible","serious","seriously","sure","t's","third","thorough","thoroughly","three",
            "well","wonder","a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all",
            "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst",
            "amount", "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around",
            "as", "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand",
            "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by",
            "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do",
            "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty",
            "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen",
            "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four",
            "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her",
            "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how",
            "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself",
            "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile",
            "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself",
            "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone",
            "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or",
            "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
            "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several",
            "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone",
            "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that",
            "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore",
            "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three",
            "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve",
            "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were",
            "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein",
            "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose",
            "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves",
            "the","a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    private static List<String> punctuationMarks = Arrays.asList(",", ".", "?", "!", "'", "\"", "/", "\\", "", "-");
    private static List<String> allowedPlaces = Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan");

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

    public static List<TextSample> loadFromXmlFile(String labelName, String filePath, String mainElement) throws IOException, SAXException, ParserConfigurationException
    {
        ArrayList<TextSample> examples = new ArrayList<TextSample>();
        File file = new File(filePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        NodeList reuters = document.getElementsByTagName(mainElement);
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
            if(allowedPlaces.contains(sample.getLabels().get(0)))
            {
                result.add(sample);
            }
        }
        return result;
    }

    public static List<TextSample> filterLabelsOut(List<TextSample> samples)
    {
        List<TextSample> result = new ArrayList<TextSample>();
        for(TextSample sample : samples)
        {
            if(sample.getLabels().size() == 1 && !sample.getLabels().get(0).equals(""))
            {
                result.add(sample);
            }
        }
        return result;
    }

    private static List<TextSample> loadReutersFromAllFiles(String labelName) throws ParserConfigurationException, SAXException, IOException
    {
        List<TextSample> result = new ArrayList<TextSample>();
        String path = "";
        for(int i=0; i<22; i++)
        {
             result.addAll(loadFromXmlFile(labelName, "./examples/sgmFiles/reut2-0" + String.format("%02d", i) + ".xml", "REUTERS"));
        }
        result = filterLabelsOut(result);
        return result;
    }

    private static List<TextSample> loadQuotes(String labelName) throws ParserConfigurationException, SAXException, IOException
    {
        List<TextSample> result = loadFromXmlFile(labelName, "./examples/quotes/quotes.xml", "QUOTE");
        Collections.shuffle(result);
        return result;
    }

    //This ugly function is ugly because combining all xml files into one doesnt work (its too long probably) so reuters
    //needs special treatment
    public static List<TextSample> loadDataSet(String discriminator, String labelName)
            throws IOException, SAXException, ParserConfigurationException
    {
        if("REUTERS".equals(discriminator))
        {
            return loadReutersFromAllFiles(labelName);
        }
        else if("QUOTES".equals(discriminator))
        {
            return loadQuotes(labelName);
        }
        else return new ArrayList<>();
    }
}

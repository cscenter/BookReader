import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import reader.ReaderException;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import viewer.*;
import model.*;
import reader.*;
import sound.*;

public class Main {
    
    private static String nameOfAudioFile = "resource/2.wav";
    private static String nameOfRusText = "resource/test1.txt";
    private static String nameOfEngText = "resource/test2.txt";
    private static String nameOfXMLFile = "resource/Concordance.xml";
    
    private static String defNameOfAudioFile = "../../resource/Rey.wav";
    private static String defNameOfRusText = "../../resource/ReyBredbery.txt";
    private static String defNameOfEngText = "../../resource/test2.txt";
    private static String defNameOfXMLFile = "../../resource/Concordance.xml";

    private static void parseArgs(String[] args) throws ReaderException, InterruptedException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--a")) {
                nameOfAudioFile = args[i + 1];
            }
            if (args[i].equals("--r")) {
                nameOfRusText = args[i + 1];
            }
            if (args[i].equals("--e")) {
                nameOfEngText = args[i + 1];
            }
            if (args[i].equals("--h")) {
                printHelpInformation();
                return;
            }
            if (args[i].equals("--def")) {
                nameOfAudioFile = defNameOfAudioFile;
                nameOfEngText = defNameOfEngText;
                nameOfRusText = defNameOfRusText;
                nameOfXMLFile = defNameOfXMLFile;
            }
        }
        readAndShowAll();
    }

    private static void readAndShowAll() throws ReaderException, InterruptedException {
        SoundModel audioModel = new SoundReader().read(nameOfAudioFile);
        
        TextReader rusReader = new TextReader();
        TextModel rusModel = rusReader.read(nameOfRusText, new Russian());
        
        TextReader engReader = new TextReader();
        TextModel engModel = engReader.read(nameOfEngText, new English());
        
        rusReader.setControlPoints(engModel);
        engReader.setControlPoints(rusModel);

        XMLReader xmlReader = new XMLReader();
        Model m = xmlReader.read(nameOfXMLFile);
        audioModel.setConcordance(m.getAudioModel().getConcordance());
                   
        Model model = new Model(audioModel, rusModel, engModel);
        Viewer myViewer = new Viewer(model, nameOfXMLFile);      
    }
    
    private static void printHelpInformation() {
        System.out.println("If you want to choose a Russian book, type --r \"name of file\"");
        System.out.println("If you want to choose a English book, type --e \"name of file\"");
        System.out.println("If you want to choose a audio book, type --a \"name of file\"");
        System.out.println("For example: --r \"RusFile.txt\" --e \"EngFile.txt\" --a \"AudioFile.wav\"");
        System.out.println("Or only: --r \"RusFile.txt\"");
    }
    
    public static void main(String[] args) throws InterruptedException, ReaderException {
        parseArgs(args);
    }
}

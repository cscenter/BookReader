import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import exception.ReaderException;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import sound.Average;
import sound.SoundFindSilence;
import sound.ZeroCrossing;
import viewer.*;
import model.*;
import reader.*;

public class Main {
    private static final int MIN = 10000;
//    private static final double DELTA = 1.15;
    private static final double DELTA = 1.15;

    private static String nameOfAudioFile = "resource/Rey.wav";
    private static String nameOfRusText = "resource/test1.txt";

    private static String nameOfEngText = "resource/test2.txt";
    private static String defNameOfAudioFile = "../../resource/Rey.wav";
    private static String defNameOfRusText = "../../resource/ReyBredbery.txt";
    private static String defNameOfEngText = "../../resource/test2.txt";

    public static void main(String[] args) throws InterruptedException, ReaderException {
        parseArgs(args);
    }

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
            }
        }
        readAndShowAll();
    }


    private static void readAndShowAll() throws ReaderException, InterruptedException {
        SoundReader soundReader = new SoundReader(nameOfAudioFile);
        short[] audio = soundReader.getShortAmplitudeArr();

        NewTextReader rusBuilder = new NewTextReader();
        rusBuilder.tokenizer(nameOfRusText, new Russian());
        TextModel rusModel = rusBuilder.getModel();

        NewTextReader engBuilder = new NewTextReader();
        engBuilder.tokenizer(nameOfEngText, new English());
        TextModel engModel = engBuilder.getModel();

        rusBuilder.setControlPoints(engModel);
        engBuilder.setControlPoints(rusModel);

        for(int i= 0; i < rusModel.getControlPoints().size(); i++){
            System.out.print(rusModel.getControlPoints().get(i).getKeySentence()+" "+
                    rusModel.getControlPoints().get(i).getValueSentence()+ " ");

        }
        System.out.println();
        for(int i= 0; i < engModel.getControlPoints().size(); i++){
            System.out.print(engModel.getControlPoints().get(i).getKeySentence()+ " " +
                    engModel.getControlPoints().get(i).getValueSentence()+ " ");
        }

//        Model model = XMLToModel();
//        model.setAudioModel(new SoundModel(audio));
        Model model = new Model(audio, rusModel, engModel);

        model.getAudioModel().setAudioBytes(soundReader.getByteAmplitudeArr());
        model.getAudioModel().setAudioFormat(soundReader.getAudioFormat());
        model.getAudioModel().setStart(13881);
        model.getAudioModel().setEnd(16881);
        model.getAudioModel().setAudioFileFormat(soundReader.getFileFormat());
        model.getAudioModel().setNameOfFile(nameOfAudioFile);
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, DELTA);
        Viewer myViewer = new Viewer(model);
        modelToXML(model);
    }
    
    private static void modelToXML(Model model){
        try {
            File file = new File("file.xml");
            System.out.println(file.getAbsolutePath());
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
            jaxbMarshaller.marshal(model, file);
	    } catch (JAXBException e) {
                e.printStackTrace();
            }
    }
    
    private static Model XMLToModel(){
        try {
            File file = new File("file.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Model model = (Model) jaxbUnmarshaller.unmarshal(file);
            return model;
 
	  } catch (JAXBException e) {
              System.out.println(e.getLocalizedMessage());
              e.printStackTrace();
	  }
        return new Model();
    }

    private static void printHelpInformation() {
        System.out.println("If you want to choose a Russian book, type --r \"name of file\"");
        System.out.println("If you want to choose a English book, type --e \"name of file\"");
        System.out.println("If you want to choose a audio book, type --a \"name of file\"");
        System.out.println("For example: --r \"RusFile.txt\" --e \"EngFile.txt\" --a \"AudioFile.wav\"");
        System.out.println("Or only: --r \"RusFile.txt\"");
    }

}

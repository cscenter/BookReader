import exception.ReaderException;
import sound.SoundFindSilence;
import viewer.*;
import model.*;
import reader.*;

public class Main {
    private static final int MIN = 10000;
    private static final double LAMBDA = 0.995;
    private static String nameOfAudioFile = "resource/Rey Bredbery.wav";
    private static String nameOfRusText = "resource/ReyBredbery.txt";
    private static String nameOfEngText = "resource/test2.txt";

    public static void main(String[] args) throws InterruptedException, ReaderException {
        parseArgs(args);
    }

    private static void parseArgs(String[] args) throws ReaderException{
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
        }
        readAndShowAll();
    }

    private static void readAndShowAll() throws ReaderException {
        SoundReader soundReader = new SoundReader(nameOfAudioFile);
        short[] audio = soundReader.getShortAmplitudeArr();

        Model model = new Model(audio, nameOfRusText, nameOfEngText);

        model.getAudioModel().setStart(13881);
        model.getAudioModel().setEnd(14881);
        model.getAudioModel().setAudioFileFormat(soundReader.getFileFormat());
        model.getAudioModel().setNameOfFile(nameOfAudioFile);
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, LAMBDA);
        Viewer myViewer = new Viewer(model);
    }

    private static void printHelpInformation() {
        System.out.println("If you want to choose a Russian book, type --r \"name of file\"");
        System.out.println("If you want to choose a English book, type --e \"name of file\"");
        System.out.println("If you want to choose a audio book, type --a \"name of file\"");
        System.out.println("For example: --r \"RusFile.txt\" --e \"EngFile.txt\" --a \"AudioFile.wav\"");
        System.out.println("Or only: --r \"RusFile.txt\"");
    }

}

import exception.ReaderException;
import sound.SoundFindSilence;
import viewer.*;
import model.*;
import reader.*;

public class Main {
    private static final int MIN = 10000;
    private static final double LAMBDA = 0.995;


    public static void main(String[] args) throws InterruptedException, ReaderException {
        SoundReader soundReader = new SoundReader("resource/Rey Bredbery.wav");
        short[] audio = soundReader.getShortAmplitudeArr();
        Model model = new Model(audio,"resource/ReyBredbery.txt","resource/test2.txt");

        model.getAudioModel().setStart(13881);
        model.getAudioModel().setEnd(14881);
        model.getAudioModel().setAudioFileFormat(soundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, LAMBDA);
        Viewer myViewer = new Viewer(model);
    }
}

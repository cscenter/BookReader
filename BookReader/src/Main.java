import sound.SoundFindSilence;
import viewer.*;
import model.*;
import reader.*;

public class Main {
    private static final int MIN = 10000;
    private static final double LAMBDA = 0.995;


    public static void main(String[] args) throws InterruptedException {
        short[] audio = SoundReader.readAudio("resource/Rey.wav");
        Model model = new Model(audio,"resource/ReyBredbery.txt","resource/test2.txt");

        model.getAudioModel().setStart(13881);
        model.getAudioModel().setEnd(14881);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey.wav");
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, LAMBDA);
        Viewer myViewer = new Viewer(model);
    }
}

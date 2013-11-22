import sound.SoundFindSilence;
import viewer.*;
import model.*;
import reader.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");
        Model model = new Model(audio,"resource/ReyBredbery.txt","resource/test2.txt");

        model.getAudioModel().setStart(13881);
        model.getAudioModel().setEnd(14881);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), 1000, 0.995);
        Viewer myViewer = new Viewer(model);
    }
}

import sound.SoundFindSilence;
import viewer.*;
import model.*;
import reader.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] rusText = TextReader.Tokenizer("resource/1.txt");
        String[] engText = TextReader.Tokenizer("resource/2.txt");
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");

        Model model = new Model(rusText, engText, audio);
        model.getAudioModel().setFrom(13881);
        model.getAudioModel().setTo(60000);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");

        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), 1, 0.995);

        Viewer myViewer = new Viewer(model);
    }
}

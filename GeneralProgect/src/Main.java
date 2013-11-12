import sound.PlayAudio;
import sound.SoundFindPauses;
import viewer.*;
import model.*;
import reader.*;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 26.10.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        // createReader
        // createModels

        String[] rusText = TextReader.Tokenizer("resource/1.txt");
        String[] engText = TextReader.Tokenizer("resource/2.txt");
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");


        Model model = new Model(rusText, engText, audio);
        model.getAudioModel().setFrom(13881);
        model.getAudioModel().setTo(60000);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");
        SoundFindPauses.findPauses(model.getAudioModel());



        Viewer myViewer = new Viewer(model);


    }

}
//TODO

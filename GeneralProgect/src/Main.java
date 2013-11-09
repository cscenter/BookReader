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

    public static void main(String[] args) {

        // createReader
        // createModels

        String[] rusText = TextReader.Tokenizer("resource/1.txt");
        String[] engText = TextReader.Tokenizer("resource/2.txt");
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");


        Model model = new Model(rusText, engText, audio);
        model.getAudioModel().setFrom(12881);
        model.getAudioModel().setTo(60000);
        Viewer myViewer = new Viewer(model);


    }

}
//TODO
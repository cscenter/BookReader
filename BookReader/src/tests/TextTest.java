package tests;

import exception.ReaderException;
import model.Model;
import model.TextModel;
import reader.English;
import reader.NewTextReader;
import reader.Russian;
import reader.SoundReader;
import translate.Search;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 15.11.13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class TextTest {
    static int[] rusQuestion = {0, 2, 6, 30, 65, 75, 127, 213, 396, 820, 1122  };
    static int[] rightAnswer = {0, 1, 7, 30, 60, 67, 111, 188, 368, 748, 1256  };
    static int[] answer = new int[rightAnswer.length];

    public static void main(String[] args) throws InterruptedException, ReaderException {
        SoundReader soundReader = new SoundReader("resource/Rey Bredbery.wav");
        short[] audio = soundReader.getShortAmplitudeArr();
        NewTextReader rusBuilder = new NewTextReader();
        rusBuilder.tokenizer("resource/test1.txt", new Russian());
        TextModel rusModel = rusBuilder.getModel();

        NewTextReader engBuilder = new NewTextReader();
        engBuilder.tokenizer("resource/test2.txt", new English());
        TextModel engModel = engBuilder.getModel();
        rusBuilder.setControlPoints(engModel);
        engBuilder.setControlPoints(rusModel);

        Model model = new Model(audio, rusModel, engModel);
        int count = 0;

        for(int i = 0; i < answer.length; i++){
            rusModel.setCurrentSentence(rusQuestion[i]);
            model.getEngModel().setSentenceFromText(rusModel);
            answer[i] = model.getEngModel().getCurrentSentence();
            if(answer[i] >= rightAnswer[i] - Search.DEVIATION && answer[i] <= rightAnswer[i] + Search.DEVIATION)
                count ++;
        }

        for (int anAnswer : answer) System.out.print(anAnswer+" ");
        System.out.println('\n');
        for (int anAnswer : rightAnswer) System.out.print(anAnswer+" ");
        System.out.println(count + "/"+ answer.length);



    }
}

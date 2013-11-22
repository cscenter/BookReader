package tests;

import model.Model;
import reader.SoundReader;
import sound.SoundFindSilence;
import viewer.Viewer;

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

    public static void main(String[] args) throws InterruptedException {
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");
        Model model = new Model(audio,"resource/test1.txt","resource/test2.txt");

        for(int i = 0; i < answer.length; i++){
            model.getEngModel().setSentenceFromText(rusQuestion[i]);
          //  double percent = model.getRusModel().findPercent(rusQuestion[i]);
          //  model.getEngModel().setSentencesFromPercent(percent);
            answer[i] = model.getEngModel().getCurrentSentence();
        }

        for (int anAnswer : answer) System.out.print(anAnswer+" ");
        System.out.println('\n');
        for (int anAnswer : rightAnswer) System.out.print(anAnswer+" ");



    }
}

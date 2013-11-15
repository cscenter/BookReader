package tests;

import model.Model;
import reader.SoundReader;
import sound.SoundFindSilence;

public class SoundTest {

    private static Model model;
    private static Double silenceFromAlgorithm[];
    private static Double silenceFromFile[] =
            {
                    0.0,
                    2.30,
                    4.80,
                    6.00,
                    12.00,
                    17.00,
                    21.20,
                    23.5,
                    26.0,
                    28.0,
                    29.5,
                    35.5,
                    39.0,
                    41.0,
                    48.0,
                    52.0,
                    57.5
            }; 
    
    public static void main(String[] args) {
        String[] rusText = {"SoundTest"};
        String[] engText = {"SoundTest"};
        short[] audio = SoundReader.readAudio("resource/Rey Bredbery.wav");

        model = new Model(rusText, engText, audio);
        model.getAudioModel().setFrom(13881);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), 100, 0.995);
        System.out.println(calculatePoints() + " points from " + silenceFromFile.length);
        System.out.println();
        printSilenceFromAlgorithm();
        System.out.println();
        printSilenceFromFile();
    }

    private static int calculatePoints() {
        silenceFromAlgorithm = model.getAudioModel().getSilence();
        int count = 0;
        boolean flag;
        for (int i = 0; i < silenceFromFile.length; i++) {
            flag = false;
            for (int j = 0; j < silenceFromAlgorithm.length; j++) {
                if (silenceFromFile[i] + 1.1 > silenceFromAlgorithm[j] &&
                        silenceFromFile[i] - 1.1 < silenceFromAlgorithm[j]) {
                    count++;
                    System.out.print(silenceFromFile[i] + "   " + silenceFromAlgorithm[j] + "   ");
                    System.out.printf("%f", Math.abs(silenceFromFile[i] - silenceFromAlgorithm[j]));
                    System.out.println();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                count--;
            }
        }
        return count;
    }

    private static void printSilenceFromAlgorithm() {
        silenceFromAlgorithm = model.getAudioModel().getSilence();
        System.out.println("Silence from algorithm");
        for (int i = 0; i < silenceFromAlgorithm.length; i++) {
            System.out.println(i + ". " + silenceFromAlgorithm[i]);
        }
    }

    private static void printSilenceFromFile() {
        System.out.println("Silence from file");
        for (int i = 0; i < silenceFromFile.length; i++) {
            System.out.println(i + ". " + silenceFromFile[i]);
        }
    }

    private static void printBothResult() {
        System.out.println("Silence: ");
        for (int i = 0; (i < silenceFromFile.length) || (i < silenceFromFile.length); i++) {
            System.out.print(i + ". ");
            if (i < silenceFromFile.length) {
                System.out.print(silenceFromFile[i]);
            }
            System.out.print(" ");
            if (i < silenceFromAlgorithm.length) {
                System.out.print(silenceFromAlgorithm[i]);
            }
            System.out.println();
        }
    }
}

package tests;

import model.Model;
import reader.SoundReader;
import sound.SoundFindSilence;

public class SoundTest {

    private static Model model;
    private static Integer silenceFromAlgorithm[];
    private static final int FREQUENCY = 8000;
    private static final int MIN = 100;
    private static final double LAMBDA = 0.995;
    private static final double ACCURACY_BACK = 1.1;
    private static final double ACCURACY_FORWARD = 0.5;

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
        model.getAudioModel().setStart(13881);
        model.getAudioModel().setAudioFileFormat(SoundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey Bredbery.wav");
        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, LAMBDA);

        System.out.println(calculatePoints() + " points from " + silenceFromFile.length);
        System.out.println();
        printSilenceFromAlgorithm();
        System.out.println();

        printSilenceFromFile();
    }

    private static int calculatePoints() {
        silenceFromAlgorithm = model.getAudioModel().getSilence();
        int count = 0;
        for (int i = 0; i < silenceFromFile.length; i++) {
            for (int j = 0; j < silenceFromAlgorithm.length; j++) {
                if (silenceFromFile[i] + ACCURACY_BACK > (double)silenceFromAlgorithm[j]/FREQUENCY &&
                        silenceFromFile[i] - ACCURACY_FORWARD < (double)silenceFromAlgorithm[j]/FREQUENCY) {
                    count++;
                    System.out.print(silenceFromFile[i] + "   " + (double) silenceFromAlgorithm[j] / FREQUENCY + "   ");
                    System.out.printf("%f", Math.abs(silenceFromFile[i] - (double) silenceFromAlgorithm[j] / FREQUENCY));
                    System.out.println();
                    break;
                }
            }
        }
        return count;
    }

    private static void printSilenceFromAlgorithm() {
        silenceFromAlgorithm = model.getAudioModel().getSilence();
        System.out.println("Silence from algorithm");
        for (int i = 0; i < silenceFromAlgorithm.length; i++) {
            System.out.println(i + ". " + (double) silenceFromAlgorithm[i] / FREQUENCY );
        }
    }

    private static void printSilenceFromFile() {
        System.out.println("Silence from file");
        for (int i = 0; i < silenceFromFile.length; i++) {
            System.out.println(i + ". " + silenceFromFile[i]);
        }
    }
}
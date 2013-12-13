package tests;

import exception.ReaderException;
import model.Model;
import model.TextModel;
import reader.SoundReader;
import sound.Average;
import sound.SoundFindSilence;
import sound.ZeroCrossing;

public class SoundTest {

    private static Model model;
    private static Integer silenceFromAlgorithm[];
    private static final int FREQUENCY = 8000;
    private static final int MIN = 10000;
    private static final double LAMBDA = 1.15;
    private static final double ACCURACY_BACK = 4;
    private static final double ACCURACY_FORWARD = 2;

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
    private static Double silenceFromFullFile[] =
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
                    57.5,
                  //  5034D,
                  //  5032D,
                   // 5096D,
                   // 3226D,
                    805D,
                   // 7131D,
                   // 8912D,
                   // 9029D,
                    126D,
                    767D,
                    759D,
                  //  4575D,
                   // 5352D,
                   // 5260D,
                   // 5049D,
                   // 2539D,
                    2135D
                    //6096D
            };

    public static void main(String[] args) throws ReaderException {
        SoundReader soundReader = new SoundReader("resource/Rey.wav");
        short[] audio = soundReader.getShortAmplitudeArr();

        TextModel ruModel = new TextModel();
        TextModel engModel = new TextModel();
        model = new Model(audio, ruModel, engModel);
        model.getAudioModel().setStart(13881);
        model.getAudioModel().setAudioFileFormat(soundReader.getFileFormat());
        model.getAudioModel().setNameOfFile("resource/Rey.wav");

//        SoundFindSilence soundFindSilence = new SoundFindSilence(model.getAudioModel(), MIN, LAMBDA);
//        System.out.println(calculatePoints2() + " points from " + silenceFromFullFile.length);
//
//        ZeroCrossing zeroCrossing = new ZeroCrossing(model.getAudioModel(), MIN, LAMBDA);
//        System.out.println(calculatePoints2() + " points from " + silenceFromFullFile.length);


        Average average = new Average(model.getAudioModel(), MIN, LAMBDA);
        System.out.println(calculatePoints2() + " points from " + silenceFromFullFile.length);
//        System.out.println(calculatePoints() + " points from " + silenceFromFile.length);
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

    private static int calculatePoints2() {
        silenceFromAlgorithm = model.getAudioModel().getSilence();
        int count = 0;
        for (int i = 0; i < silenceFromFullFile.length; i++) {
            for (int j = 0; j < silenceFromAlgorithm.length; j++) {
                if (silenceFromFullFile[i] + ACCURACY_BACK > (double)silenceFromAlgorithm[j]/FREQUENCY &&
                        silenceFromFullFile[i] - ACCURACY_FORWARD < (double)silenceFromAlgorithm[j]/FREQUENCY) {
                    count++;
                    System.out.print(silenceFromFullFile[i] + "   " + (double) silenceFromAlgorithm[j] / FREQUENCY + "   ");
                    System.out.printf("%f", Math.abs(silenceFromFullFile[i] - (double) silenceFromAlgorithm[j] / FREQUENCY));
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

    private static void printSilenceFromFullFile() {
        System.out.println("Silence from file");
        for (int i = 0; i < silenceFromFullFile.length; i++) {
            System.out.println(i + ". " + silenceFromFullFile[i]);
        }
    }
}

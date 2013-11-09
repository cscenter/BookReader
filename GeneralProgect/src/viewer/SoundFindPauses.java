package viewer;

import model.SoundModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Oskina Olga
 * SPBGPU
 * 2013
 */
public class SoundFindPauses {
    private final static int lengthFrame = 240;
    private final static int lengthPause = 1200;
    private final static double accuracy = 1;
    private static short amplitude[];
    private static boolean pauses[];
    private static List <Integer> listPauses;

    public static void findPauses(SoundModel model){
        amplitude = model.getShortAmplitude();
        pauses = new boolean[(amplitude.length / lengthFrame) + 1];
        listPauses = new ArrayList<Integer>();
        double energyInFile = 0;
        double energyInFrame;
        int k = 2;
        for (int i = 0; i < amplitude.length/ lengthFrame; i += lengthFrame){
            energyInFile += energy(i);
        }
        energyInFile /= amplitude.length/ lengthFrame;
        for (int i = 0; i < amplitude.length; i += lengthFrame){

            // Calculate energy in frame
            energyInFrame = energy(i);

            if (energyInFrame > k * energyInFile) {
                pauses[i / lengthFrame] = true;
            }
            else {
                pauses[i / lengthFrame] = false;
            }
        }

        findPause();

        model.setBooleanPauses(pauses);
        model.setPauses(listPauses.toArray( new Integer[listPauses.size()]));
    }


    private static double energy(int j){
        double mean = 0;

        for (int i = (j - 1) * lengthFrame; i < j - lengthFrame; i++){
            mean += amplitude[i] * amplitude[i];
        }
        mean /= lengthFrame;
        return mean;
    }

    public static int getLengthFrame(){
        return lengthFrame;
    }


    private static void findPause(){
        for (int i = 0; i < pauses.length;
             i += lengthPause/lengthFrame){
            int countPause = 0;
            for (int j = i; j < (i + lengthPause/lengthFrame); j++) {
                if (pauses[j]) {
                    countPause++;
                }
            }
            if (countPause == lengthPause/lengthFrame ) {
                listPauses.add(i * lengthFrame);
            }
        }
    }

}

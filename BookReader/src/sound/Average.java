package sound;

import model.SoundModel;

import java.util.ArrayList;
import java.util.List;

public class Average {
    private SoundModel audioModel;
    private final static int LENGTH_FRAME = 250;
    private final int LENGTH_SILENCE = 8;
    private boolean isVoice[];

    private List<Integer> silence;

    private short[] amplitudeArr;

    public Average(SoundModel model, double minInit, double deltaInit) {

        silence = new ArrayList<Integer>();
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / LENGTH_FRAME + 1];
        amplitudeArr = model.getShortAmplitude();
        algorithm();
        audioModel.setPauses(silence.toArray(new Integer[silence.size()]));
        audioModel.setBooleanPauses(isVoice);
    }

    private int calculateMin(int n) {
        int min = Integer.MAX_VALUE;
        for (int i = n; i < n + LENGTH_FRAME; i++) {
            if (Math.abs(amplitudeArr[i]) < min) {
                min = amplitudeArr[i];
            }
        }
        return min;
    }
    private int calculateMax(int n) {
        int max = Integer.MIN_VALUE;
        for (int i = n; i < n + LENGTH_FRAME; i++) {
            if (amplitudeArr[i] > max) {
                max = amplitudeArr[i];
            }
        }
        return max;
    }

    private void algorithm() {

        for (int i = 0; i < amplitudeArr.length - LENGTH_FRAME; i += LENGTH_FRAME) {
            boolean voice = true;
            int average = (calculateMax(i) + calculateMin(i)) / 2;
            for (int j = i; j < i +LENGTH_FRAME; j++) {

                if (Math.abs(amplitudeArr[j]) < average) {
                    voice = false;
                } else {
                    voice = true;
                }
            }
            if (voice) {
                isVoice[i / LENGTH_FRAME] = true;
            } else {
                isVoice[i / LENGTH_FRAME] = false;
            }
        }
        check();
    }

    private void check(){
        for (int i = 0; i < isVoice.length - LENGTH_SILENCE; i++) {
            int count = 0;
            for (int j = i; j < i + LENGTH_SILENCE; j++) {
                if (!isVoice[j]) {
                    count++;
                }
            }
            if (count >= LENGTH_SILENCE / 4) {
                silence.add(i * LENGTH_FRAME);
            }
        }
    }


}
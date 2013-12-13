package sound;

import model.SoundModel;

import java.util.ArrayList;
import java.util.List;

public class ZeroCrossing {
    private SoundModel audioModel;
    private final static int LENGTH_FRAME = 250;
    private final int LENGTH_SILENCE = 8;
    private boolean isVoice[];

    private List<Integer> silence;

    private short[] amplitudeArr;

    public ZeroCrossing(SoundModel model, double minInit, double deltaInit) {

        silence = new ArrayList<Integer>();
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / LENGTH_FRAME + 1];
        amplitudeArr = model.getShortAmplitude();

        algorithm();

        audioModel.setPauses(silence.toArray(new Integer[silence.size()]));
        audioModel.setBooleanPauses(isVoice);
    }

    private void algorithm() {
//        if currentVal < 0 - false
        int middle = average();
        boolean currentVal = false;
        for (int i = 0; i < amplitudeArr.length - LENGTH_FRAME; i += LENGTH_FRAME) {
            int max = Integer.MIN_VALUE;
            int count = 0;
            for (int j = i; j < i + LENGTH_FRAME; j++) {

                if (currentVal && (amplitudeArr[i] > 0)) {
                    if (count > max) {
                        max = count;
                    }
                    count = 0;
                }
                if (!currentVal && (amplitudeArr[i] < 0)) {
                    if (count > max) {
                        max = count;
                    }
                    count = 0;
                }
                count++;
                currentVal = (amplitudeArr[i] > 0);
            }

            if (max > middle) {
                isVoice[i / LENGTH_FRAME] = true;
            } else {
                isVoice[i / LENGTH_FRAME] = false;
            }

        }
        check();

    }

    private int  average() {
        int val = 0;
        boolean currentVal = false;
        for (int i = 0; i < amplitudeArr.length - LENGTH_FRAME; i += LENGTH_FRAME) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            int count = 0;
            for (int j = i; j < i + LENGTH_FRAME; j++) {

                if (currentVal && (amplitudeArr[i] > 0)) {
                    if (count > max) {
                        max = count;
                    }
                    if (count < min) {
                        min = count;
                    }
                    count = 0;
                }
                if (!currentVal && (amplitudeArr[i] < 0)) {
                    if (count > max) {
                        max = count;
                    }
                    if (count < min) {
                        min = count;
                    }
                    count = 0;
                }
                count++;
                currentVal = (amplitudeArr[i] > 0);
            }
            val += (min + max) / 2;
        }

        return val / LENGTH_FRAME;

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
package sound;
import model.SoundModel;
import java.util.ArrayList;
import java.util.List;

public class SoundFindSilence {
    private SoundModel audioModel;
    private final static int LENGTH_FRAME = 240;
    private double eMax;
    private double eMin;
    private double delta;
    private double minInitValue;
    private double deltaInitValue;
    private double threshold;
    private boolean isVoice[];
    private List<Integer> silence;

    public SoundFindSilence(SoundModel model, double minInit, double deltaInit) {
        silence = new ArrayList<Integer>();
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / LENGTH_FRAME + 1];
        minInitValue = minInit;
        deltaInitValue = deltaInit;
        algorithmDLED();
        checkIsSilence();
        addSilence();
        audioModel.setSilence(silence.toArray(new Integer[silence.size()]));
        audioModel.setBooleanPauses(isVoice);

    }

    public static int getLengthFrame() {
        return LENGTH_FRAME;
    }

    private void algorithmDLED() {
        delta = deltaInitValue;
        for(int i = LENGTH_FRAME; i < audioModel.getShortAmplitude().length - LENGTH_FRAME;
            i += LENGTH_FRAME) {
            double currentEnergy = calculateEnergyOfFrame(i / LENGTH_FRAME);
            if (i/ LENGTH_FRAME == 1) {
                firstFrame(currentEnergy);
            }
            if (currentEnergy > eMax) {
                moreEMax(currentEnergy);
            }
            if (currentEnergy < eMin) {
                lessEMin(currentEnergy);
            } else {
                moreEMin();
            }
            calculateThreshold();
            if (currentEnergy > threshold) {
                moreThreshold(i / LENGTH_FRAME);
            } else {
                lessThreshold(i / LENGTH_FRAME);
            }
            increaseEMin();
        }
    }

    private void addSilence() {
        boolean currentStatus = false;
        for (int i = 0; i < isVoice.length; i++) {
            if (!isVoice[i] && currentStatus) {
                currentStatus = false;
                silence.add(i * LENGTH_FRAME);
            } else if (isVoice[i] && !currentStatus){
                currentStatus = true;
            }
        }
    }

    private void checkIsSilence() {
        final int LENGTH_SILENCE = 8;
        final int hangoverThreshold = 1;
        for(int i = 0; i < isVoice.length - LENGTH_SILENCE; i += LENGTH_SILENCE) {
            int inactiveCount = 0;
            for(int j = i; j < i + LENGTH_SILENCE; j++) {
                if (!isVoice[j]) {
                    inactiveCount++;
                }
            }
            if (inactiveCount > hangoverThreshold) {
                for(int j = i; j < i + LENGTH_SILENCE; j++) {
                    isVoice[j] = false;
                }
            }
        }
    }

    private double calculateEnergyOfFrame(int j) {
        double energy = 0;
        for (int i = (j - 1) * LENGTH_FRAME + 1; i < j * LENGTH_FRAME; i++) {
            energy = energy + (audioModel.getShortAmplitude()[i] *
                    audioModel.getShortAmplitude()[i]);
        }
        energy /= LENGTH_FRAME;
        energy = Math.sqrt(energy);
        return energy;
    }

    private void firstFrame(double currentEnergy) {
        eMax = currentEnergy;
        eMin = minInitValue;
    }

    private void moreEMax(double currentEnergy) {
        eMax = currentEnergy;
    }

    private void lessEMin(double currentEnergy) {
        if (currentEnergy == 0) {
            eMin = minInitValue;
        } else {

            eMin = currentEnergy;
        }
        delta = deltaInitValue;
    }

    private void moreEMin() {
        delta = deltaInitValue;
    }

    private void calculateThreshold() {
        double lambda = (eMax - eMin) / eMax;
        threshold = (1 - lambda) * eMax + lambda * eMin;
    }

    private void moreThreshold(int j) {
        j--;
        isVoice[j] = true;
    }

    private void lessThreshold(int j) {
        j--;
        isVoice[j] = false;
    }

    private void increaseEMin() {
        final double CHANGE_DELTA = 1.0001;
        delta = delta * CHANGE_DELTA;
        eMin = eMin * delta;
    }
}


package sound;
import model.SoundModel;
import java.util.ArrayList;
import java.util.List;

public class SoundFindSilence {
    private SoundModel audioModel;
    private static int lengthFrame = 240;
    private int lengthSilence = 8;
    private int hangoverThreshold = 1;
    private double eMax;
    private double eMin;
    private double delta;
    private double minInitValue = 1;
    private double deltaInitValue = 1;
    private double threshold;
    private boolean isVoice[];
    private List<Double> silence;

    public SoundFindSilence(SoundModel model, double minInit, double deltaInit) {
        silence = new ArrayList<Double>();
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / lengthFrame + 1];

        algorithmDLED(minInit, deltaInit);
        checkIsSilence();
        addSilence();

        audioModel.setSilence(silence.toArray(new Double[silence.size()]));
        audioModel.setBooleanPauses(isVoice);

    }


    public static int getLengthFrame() {
        return lengthFrame;
    }

    private void algorithmDLED(double minInit, double deltaInit) {
        minInitValue = minInit;
        delta = deltaInit;
        for(int i = lengthFrame; i < audioModel.getShortAmplitude().length - lengthFrame;
            i += lengthFrame) {
            double currentEnergy = calculateEnergyOfFrame(i / lengthFrame);
            if (i/lengthFrame == 1) {
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
                moreThreshold(i / lengthFrame);
            } else {
                lessThreshold(i / lengthFrame);
            }
            increaseEMin();
        }
    }

    private void addSilence() {
        boolean currentStatus = false;
        for (int i = 0; i < isVoice.length; i++) {
            if (!isVoice[i] && currentStatus) {
                currentStatus = false;
                silence.add((double)i * lengthFrame / 8000);
            } else if (isVoice[i] && !currentStatus){
                currentStatus = true;
            }
        }
    }

    private void checkIsSilence() {
        for(int i = 0; i < isVoice.length - lengthSilence; i += lengthSilence) {
            int inactiveCount = 0;
            for(int j = i; j < i + lengthSilence; j++) {
                if (!isVoice[j]) {
                    inactiveCount++;
                }
            }
            if (inactiveCount > hangoverThreshold) {
                for(int j = i; j < i + lengthSilence; j++) {
                    isVoice[j] = false;
                }
            }
        }
    }

    private double calculateEnergyOfFrame(int j) {
        double energy = 0;
        for (int i = (j - 1) * lengthFrame + 1; i < j * lengthFrame; i++) {
            energy = energy + (audioModel.getShortAmplitude()[i] *
                    audioModel.getShortAmplitude()[i]);
        }
        energy /= lengthFrame;
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
        delta = delta * 1.0001;
        eMin = eMin * delta;
    }
}


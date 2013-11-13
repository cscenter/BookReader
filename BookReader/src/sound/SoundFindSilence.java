package sound;


import model.SoundModel;

public class SoundFindSilence {
    private static SoundModel audioModel;
    private static int lengthFrame = 1000;
    private static int lengthSilense = 1;
    private static int hangoverThreshold = 1;
    private static double eMax;
    private static double eMin;
    private static double delta;
    private static double minInitValue = 1;
    private static double deltaInitValue = 1;
    private static double threshold;
    private static boolean isVoice[];



    public SoundFindSilence(SoundModel model, double minInit, double deltaInit) {
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / lengthFrame + 1];
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

        for(int i = 0; i < isVoice.length - lengthSilense; i += lengthSilense) {
            int inactiveCount = 0;
            for(int j = i; j < i + lengthSilense; j++) {
                if (!isVoice[j]) {
                    inactiveCount++;
                }
            }
            if (inactiveCount > hangoverThreshold) {
                for(int j = i; j < i + lengthSilense; j++) {
                        isVoice[j] = false;
                }
            }
        }
        audioModel.setBooleanPauses(isVoice);

    }

    public static int getLengthFrame() {
        return lengthFrame;
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

    private static void increaseEMin() {
        delta = delta * 1.0001;
        eMin = eMin * delta;
    }

}


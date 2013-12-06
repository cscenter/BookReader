package sound;
import model.SoundModel;
import java.util.ArrayList;
import java.util.List;

public class SoundFindSilence {
    private SoundModel audioModel;
    private final static int LENGTH_FRAME = 250;
    private final int LENGTH_SILENCE = 4;
    private final int HANGOVER_THRESHOLD = 1;
    private double eMax = 0;
    private double eMin = 0;
    private double delta;
    private double minInitValue;
    private double deltaInitValue;
    private double threshold;
    private boolean isVoice[];
    private final double CHANGE_DELTA = 1.0001;
    private int inactiveCounter;
    private final double eps = 0.0001;

    private List<Double> eMaxArr;
    private List<Double> eMinArr;
    private List<Double> thresholdArr;
    private List<Integer> silence;
    private List<Double> energy;

    public SoundFindSilence(SoundModel model, double minInit, double deltaInit) {

        silence = new ArrayList<Integer>();
        audioModel = model;
        isVoice = new boolean[audioModel.getShortAmplitude().length / LENGTH_FRAME + 1];
        eMaxArr = new ArrayList<Double>();
        eMinArr = new ArrayList<Double>();
        thresholdArr = new ArrayList<Double>();
        energy = new ArrayList<Double>();
        minInitValue = minInit;
        deltaInitValue = deltaInit;
        delta = deltaInit;
        inactiveCounter = 0;
        algorithmDLED();
        //checkIsSilence();
        addSilence();

        audioModel.setSilence(silence.toArray(new Integer[silence.size()]));
        audioModel.setBooleanPauses(isVoice);
        audioModel.setEMaxArr(eMaxArr);
        audioModel.setEMinArr(eMinArr);
        audioModel.setThresholdArr(thresholdArr);
        audioModel.setEnergy(energy);
    }


    public static int getLENGTH_FRAME() {
        return LENGTH_FRAME;
    }

    private void algorithmDLED() {
        int j = 0;
        for(int i = LENGTH_FRAME; i < audioModel.getShortAmplitude().length - LENGTH_FRAME;
            i += LENGTH_FRAME) {

              j++;
            double currentEnergy = calculateEnergyOfFrame(i / LENGTH_FRAME);
            if (i / LENGTH_FRAME == 1) {
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
            eMaxArr.add(eMax);
            eMinArr.add(eMin);
            thresholdArr.add(threshold);
            energy.add(currentEnergy);

//            System.out.println(threshold);
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
        for(int i = 0; i < isVoice.length - LENGTH_SILENCE; i += LENGTH_SILENCE) {
            int inactiveCount = 0;
            for(int j = i; j < i + LENGTH_SILENCE; j++) {
                if (!isVoice[j]) {
                    inactiveCount++;
                }
            }
            if (inactiveCount > HANGOVER_THRESHOLD) {
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
        if (currentEnergy == 0 + eps) {
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
        lambda *= 0.9999;
        threshold = (1 - lambda) * eMax + lambda * eMin;
    }

    private void moreThreshold(int j) {
        isVoice[j - 1] = true;
        inactiveCounter = 0;
    }

    private void lessThreshold(int j) {
        if (inactiveCounter > HANGOVER_THRESHOLD) {
            isVoice[j - 1] = false;
        } else {
            isVoice[j - 1] = true;
            inactiveCounter++;
        }
    }

    private void increaseEMin() {
        delta = delta * CHANGE_DELTA;
        eMin = eMin * delta;
    }
}
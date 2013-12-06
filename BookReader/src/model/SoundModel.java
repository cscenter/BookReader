package model;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import java.util.List;

public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private boolean booleanPauses[];
    private byte[] audioBytes;
    private int start;
    private int end;
    private AudioFileFormat audioFileFormat;
    private String nameOfFile;
    private AudioFormat audioFormat;
    private List<Double> eMaxArr;
    private List<Double> eMinArr;
    private List<Double> thresholdArr;
    private List<Double> energy;

    public SoundModel(short[] audio){
        setShortAmplitude(audio);
    }

    public void setAudioFileFormat(AudioFileFormat value) {
        audioFileFormat = value;
    }

    public AudioFileFormat getAudioFileFormat() {
        return audioFileFormat;
    }

    public void setAudioFormat(AudioFormat value) {
        audioFormat = value;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }


    public void setNameOfFile(String value) {
        nameOfFile = value;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public short[] getShortAmplitude(){
        return shortAmplitude;
    }

    public void setShortAmplitude(short arr[]){
        shortAmplitude = arr;
    }

    public int getStart(){
        return start;
    }

    public void setStart(int value){
        start = value;
    }

    public int getEnd(){
        return end;
    }

    public void setEnd(int value){
        end = value;
    }

    public void setSilence(Integer arr[]){
        pauses = arr;
    }

    public Integer[] getSilence(){
        return pauses;
    }

    public boolean[] getBooleanPauses(){
        return booleanPauses;
    }

    public void setBooleanPauses(boolean arr[]){
        booleanPauses = arr;
    }

    public void setAudioBytes(byte[] arr) {
        audioBytes = arr;
    }

    public byte[] getAudioBytes() {
        return audioBytes;
    }

    public List<Double> getEMaxArr() {
        return eMaxArr;
    }

    public void setEMaxArr(List<Double> eMaxArr) {
        this.eMaxArr = eMaxArr;
    }

    public List<Double> getEMinArr() {
        return eMinArr;
    }

    public void setEMinArr(List<Double> eMinArr) {
        this.eMinArr = eMinArr;
    }

    public List<Double> getThresholdArr() {
        return thresholdArr;
    }

    public void setThresholdArr(List<Double> thresholdArr) {
        this.thresholdArr = thresholdArr;
    }

    public List<Double> getEnergy() {
        return energy;
    }

    public void setEnergy(List<Double> energy) {
        this.energy = energy;
    }

}

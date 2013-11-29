package model;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private boolean booleanPauses[];
    private byte[] audioBytes;
    private int start;
    private int end;
    private AudioFileFormat audioFileFormat;
    private String nameOfFile;
    private AudioFormat audioFormat;

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


}

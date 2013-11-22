package model;
import javax.sound.sampled.AudioFileFormat;

public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private boolean booleanPauses[];
    private int start;
    private int end;
    private AudioFileFormat audioFileFormat;
    private String nameOfFile;

    public SoundModel(short[] audio){
        setShortAmplitude(audio);
    }

    public void setAudioFileFormat(AudioFileFormat value) {
        audioFileFormat = value;
    }

    public AudioFileFormat getAudioFileFormat() {
        return audioFileFormat;
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
}

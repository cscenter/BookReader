package model;
import javax.sound.sampled.AudioFileFormat;

public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private boolean booleanPauses[];
    private int from;
    private AudioFileFormat audioFileFormat;
    private String nameOfFile;
    private Double[] silence;

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

    public int getFrom(){
        return from;
    }

    public void setFrom(int value){
        from = value;
    }

    public void setSilence(Double arr[]){
        silence = arr;
    }

    public Double[] getSilence(){
        return silence;
    }

    public boolean[] getBooleanPauses(){
        return booleanPauses;
    }

    public void setBooleanPauses(boolean arr[]){
        booleanPauses = arr;
    }
}

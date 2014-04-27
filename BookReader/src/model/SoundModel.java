package model;
import java.util.List;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType( XmlAccessType.NONE )
@XmlRootElement
public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private byte[] audioBytes;
    private int start;
    private int end;
    private AudioFileFormat audioFileFormat;
    @XmlAttribute(name = "fileName")
    private String fileName;
    private AudioFormat audioFormat;
    private List<Double> eMaxArr;
    private List<Double> eMinArr;
    private List<Double> thresholdArr;
    private List<Double> energy;
        
    public SoundModel(){}
    
    public SoundModel(short[] audio){
        setConcordance(new Concordance());
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

    public void setFileName(String value) {
        fileName = value;
    }

    public String getFileName() {
        return fileName;
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

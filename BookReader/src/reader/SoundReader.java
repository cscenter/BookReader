package reader;
import javax.sound.sampled.*;
import java.io.*;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;
import model.SoundModel;

public class SoundReader {

    private byte[] audioBytes;
    private short[] shortAmplitudeArr;
    private AudioFileFormat audioFileFormat;
    private AudioFormat audioFormat;
    private String nameOfFile;
    private SoundModel audioModel;
    
    public SoundReader(String name) throws ReaderException {
        nameOfFile = name;
        readAudio();
        
        audioModel = new SoundModel(shortAmplitudeArr);
        audioModel.setAudioBytes(audioBytes);
        audioModel.setAudioFormat(audioFormat);
        audioModel.setStart(13881);
        audioModel.setEnd(16881);
        audioModel.setAudioFileFormat(audioFileFormat);
        audioModel.setNameOfFile(nameOfFile);
    }
    
    public SoundModel getModel(){
        return audioModel;              
    } 
    
    private void readAudio() throws ReaderException {
        int numBytesRead;
        File fileIn = new File(nameOfFile);
        try {
            audioFileFormat = getAudioFileFormat(fileIn);
            AudioInputStream audioInputStream =
                AudioSystem.getAudioInputStream(fileIn);
            audioFormat = audioInputStream.getFormat();

            int countOfChannel =  audioFileFormat.getFormat().getChannels();
            int bytesPerFrame = audioFileFormat.getFormat().getFrameSize();
            if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                bytesPerFrame = 1;
            }
            int numBytes = audioFileFormat.getFrameLength()*bytesPerFrame;
            if (numBytes == AudioSystem.NOT_SPECIFIED){
                numBytes = 16000 * bytesPerFrame;
            }
            audioBytes = new byte[numBytes];
            numBytesRead = audioInputStream.read(audioBytes);
            shortAmplitudeArr =  new short[numBytesRead/bytesPerFrame];

            if (audioFileFormat.getFormat().isBigEndian()){
                bigEndianOrder(shortAmplitudeArr, countOfChannel);
            }
            else {
                littleEndianOrder(shortAmplitudeArr, countOfChannel);
            }

        } catch (Exception e) {
            System.out.println(fileIn.getAbsolutePath());
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ReaderException("SoundReader: " + e.getMessage());
        }
    }

    private void littleEndianOrder(short shortAmplitudeArr[], int countOfChannel){
        if (countOfChannel == 1) {
            for (int i = 0, j = 0; i < audioBytes.length - 1; i += 2, j++){
                int aux = audioBytes[i+1];
                int dop = audioBytes[i] & 0xFF;
                aux = aux << 8;
                aux = aux| dop;
                shortAmplitudeArr[j] = (short) aux;
            }
        } else {
            for (int i = 0, j = 0; i < audioBytes.length - 1; i += 4, j++){
                int aux = audioBytes[i+1];
                int dop = audioBytes[i] & 0xFF;
                aux = aux << 8;
                aux = aux| dop;
                shortAmplitudeArr[j] = (short) aux;
            }
        }


    }

    private void bigEndianOrder(short shortAmplitudeArr[], int countOfChannel){
        if (countOfChannel == 1) {
            for (int i = 0, j = 0; i < shortAmplitudeArr.length; i += 2, j++){
                int aux = audioBytes[i];
                aux = aux << 8;
                aux = aux| (audioBytes[i + 1] & 0xFF);
                shortAmplitudeArr[j] = (short) aux;
            }
        } else{
            for (int i = 0, j = 0; i < shortAmplitudeArr.length; i += 4, j++){
                int aux = audioBytes[i];
                aux = aux << 8;
                aux = aux| (audioBytes[i + 1] & 0xFF);
                shortAmplitudeArr[j] = (short) aux;
            }
        }
    }

    public AudioFileFormat getFileFormat() {
        return audioFileFormat;
    }

    public short[] getShortAmplitudeArr() {
        return shortAmplitudeArr;
    }

    public byte[] getByteAmplitudeArr() {
        return audioBytes;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
}

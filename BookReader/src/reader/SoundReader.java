package reader;
import javax.sound.sampled.*;
import java.io.*;

import exception.ReaderException;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;

public class SoundReader {

    private byte[] audioBytes;
    private short[] shortAmplitudeArr;
    private AudioFileFormat audioFileFormat;
    private String nameOfFile;

    public SoundReader(String name) throws ReaderException {
        nameOfFile = name;
        readAudio();
    }

    private void readAudio() throws ReaderException {
        int numBytesRead;
        File fileIn = new File(nameOfFile);
        try {
            audioFileFormat = getAudioFileFormat(fileIn);
            if (audioFileFormat.getType() == AudioFileFormat.Type.WAVE) {
                AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(fileIn);
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
            } else {
                throw new ReaderException("Don't support type " + audioFileFormat.getType());
            }

        } catch (Exception e) {
            throw new ReaderException("SoundReader", e);
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


}

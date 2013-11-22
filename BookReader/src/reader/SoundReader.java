package reader;
import javax.sound.sampled.*;
import java.io.*;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;

public class SoundReader {

    private static byte[] audioBytes;
    private static short[] shortAmplitudeArr;
    private static AudioInputStream audioInputStream;
    private static AudioFileFormat audioFileFormat;

    public static short[] readAudio(String nameOfFile){
        int numBytesRead = 0;
        File fileIn = new File(nameOfFile);
        try {
            audioInputStream =
                    AudioSystem.getAudioInputStream(fileIn);
            audioFileFormat =  getAudioFileFormat(fileIn);
            int bytesPerFrame = audioFileFormat.getFormat().getFrameSize();
            if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                bytesPerFrame = 1;
            }
            int numBytes = audioFileFormat.getFrameLength()*bytesPerFrame;
            if (numBytes == AudioSystem.NOT_SPECIFIED){
                numBytes = 16000 * bytesPerFrame;
            }
            audioBytes = new byte[numBytes];
            try {
                numBytesRead = audioInputStream.read(audioBytes);
            } catch (Exception ex) {
                System.out.println("1 AudioReaderException!!!");
            }
            shortAmplitudeArr =  new short[numBytesRead/bytesPerFrame];
            if (audioFileFormat.getFormat().isBigEndian()){
                bigEndianOrder(shortAmplitudeArr);
            }
            else {
                littleEndianOrder(shortAmplitudeArr);
            }
        } catch (Exception e) {
            System.out.println("2 AudioReaderException!!!");
        }
        return shortAmplitudeArr;
    }

    private static void littleEndianOrder(short shortAmplitudeArr[]){
        for (int i = 0, j = 0; i < audioBytes.length - 1; i += 2, j++){
            int aux = audioBytes[i+1];
            int dop = audioBytes[i] & 0xFF;
            aux = aux << 8;
            aux = aux| dop;
            shortAmplitudeArr[j] = (short) aux;
        }
    }

    private static void bigEndianOrder(short shortAmplitudeArr[]){
        for (int i = 0, j = 0; i < shortAmplitudeArr.length; i += 2, j++){
            int aux = audioBytes[i];
            aux = aux << 8;
            aux = aux| (audioBytes[i + 1] & 0xFF);
            shortAmplitudeArr[j] = (short) aux;
        }
    }

    public static AudioFileFormat getFileFormat() {
        return audioFileFormat;
    }

}

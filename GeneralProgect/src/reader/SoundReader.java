package reader;

import javax.sound.sampled.*;
import java.io.*;

import static javax.sound.sampled.AudioSystem.getAudioFileFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 15.10.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */

public class SoundReader {

    private static byte[] audioBytes;
    private static short[] shortAmplitudeArr;
    private static AudioInputStream audioInputStream;

    public static short[] readAudio(String nameOfFile){

        int numBytesRead = 0;
        File fileIn = new File(nameOfFile);

        try {
            audioInputStream =
                    AudioSystem.getAudioInputStream(fileIn);
            AudioFileFormat audioFileFormat =  getAudioFileFormat(fileIn);
            // load the sound into memory (a Clip)





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
                System.out.println("1AudioReaderExeption!!!");
            }
            shortAmplitudeArr =  new short[numBytesRead/bytesPerFrame];

            if (audioFileFormat.getFormat().isBigEndian()){
                bigEndianOrder(shortAmplitudeArr);
            }
            else {
                littleEndianOrder(shortAmplitudeArr);
            }
        } catch (Exception e) {
            System.out.println("2AudioReaderExeption!!!");
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
    public static void playClip(){
        try {
        DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioInputStream);

        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                    System.exit(0);
                }
            }
        });

        clip.start();
        } catch (Exception e) {
            System.out.println("AudioReaderExeption!!!");
        }
    }

}

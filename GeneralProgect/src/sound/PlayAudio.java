package sound;

import model.SoundModel;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine;
import java.io.File;

/**
 * Oskina Olga
 * SPBGPU
 * 2013
 */
public class PlayAudio {

    private static SoundModel audio;
    private static Clip line = null;
    private static int start = 0;
    private static int endLoop = 0;

    public PlayAudio(SoundModel model) throws InterruptedException {
        audio = model;
    }

    public void playClip() throws InterruptedException {
        try {
            AudioFileFormat audioFileFormat = audio.getAudioFileFormat();
            AudioFormat audioFormat = audioFileFormat.getFormat();
            File fileIn = new File(audio.getNameOfFile());
            AudioInputStream stream = AudioSystem.getAudioInputStream(fileIn);
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            if (AudioSystem.isLineSupported(info)) {
                line = (Clip)AudioSystem.getLine(info);
                line.open(stream);
                line.setFramePosition(start);
                line.start();


                // Why do I do it?
                while (!line.isRunning())
                    Thread.sleep(10);
                while (line.isRunning())
                    Thread.sleep(10);
                line.close();

            }
        } catch (Exception e) {
            System.out.println("AudioReaderExeption!!!");
        }
        line.close();
    }

    public void setStart(int value) {
        start = value;
    }
    public void setEndLoop(int value) {
        endLoop = value;
    }

}

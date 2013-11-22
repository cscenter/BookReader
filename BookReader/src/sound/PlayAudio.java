package sound;
import model.SoundModel;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine;
import java.io.File;

public class PlayAudio {

    private SoundModel audio;
    private Clip line = null;
    private int start = 0;

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

                while (!line.isRunning())
                    Thread.sleep(10);
                while (line.isRunning())
                    Thread.sleep(10);
                line.close();
            }
        } catch (Exception e) {
        }
        line.close();
    }

    public void setStart(int value) {
        start = value;
    }
}

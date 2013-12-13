package sound;
import model.SoundModel;
import viewer.Paint;
import viewer.SoundLine;
import viewer.SoundViewer;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine;
import java.io.File;

public class PlayAudio {

    private SoundModel audio;
    private Clip line = null;
    private int start = 0;
    private int end = 0;
    private boolean run = true;
    private SoundLine line1;
    private SoundViewer soundViewer;
    public PlayAudio(SoundModel model, SoundLine l, SoundViewer sv) throws InterruptedException {
        audio = model;
        line1 = l;
        soundViewer = sv;
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
                while (!line.isRunning()) {
                    Thread.sleep(10);
                }
                while (line.isRunning()) {
                    Thread.sleep(10);
                    line1.setStart(line.getFramePosition());
                    line1.setEnd(line.getFramePosition() + 1600);
                    soundViewer.update(line.getFramePosition());
//                    line1.repaint();
                }
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
    public void setEnd(int value) {
        end = value;
    }
    public boolean getRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }



}

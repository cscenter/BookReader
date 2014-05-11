package sound;
import model.SoundModel;
import viewer.SoundLine;
import viewer.SoundViewer;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine;
import java.io.File;

public class PlayAudio {
    private SoundModel audioModel;
    private Clip clip = null;
    private int start = 0;
    private int end = 0;
    private boolean run = true;
    private SoundLine soundLine;
    private SoundViewer soundViewer;
    
    public PlayAudio(SoundModel audioModel, SoundLine soundLine, SoundViewer soundViewer) throws InterruptedException {
        this.audioModel = audioModel;
        this.soundLine = soundLine;
        this.soundViewer = soundViewer;        
    }

    public void playClip() throws InterruptedException {
        try {
            AudioFileFormat audioFileFormat = audioModel.getAudioFileFormat();
            AudioFormat audioFormat = audioFileFormat.getFormat();
            File fileIn = new File(audioModel.getFileName());
            AudioInputStream stream = AudioSystem.getAudioInputStream(fileIn);
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            
            
            if (AudioSystem.isLineSupported(info)) {
                clip = (Clip)AudioSystem.getLine(info);
                clip.open(stream);
                clip.setFramePosition(start);
                System.out.println("MicrosecondPosition  " + clip.getMicrosecondPosition());
                System.out.println("FramePosition  " + clip.getFramePosition());
                System.out.println("FrameLength  " + clip.getFrameLength());
                
                clip.start();

                // Why do I do it?
                while (!clip.isRunning()) {
                    Thread.sleep(10);
                }
                while (clip.isRunning()) {
                    Thread.sleep(10);
                    soundLine.setStart(clip.getFramePosition());
                    soundLine.setEnd(clip.getFramePosition() + 1600);
                    soundViewer.update(clip.getFramePosition());
                    //soundLine.repaint();
                }
                clip.close();
            }
        } catch (Exception e) {
            System.out.println("AudioReaderExeption!!!");
        }
        clip.close();
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

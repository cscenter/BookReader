package viewer;

import model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 26.10.13
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
public class Viewer{
    private JFrame frame;
    private TextViewer rusPanel;
    private TextViewer engPanel;
    private SoundViewer audioPanel;
    private Model model;

    public Viewer(Model model) throws InterruptedException {
        frame = new JFrame("BookReader");
        this.model = model;
        rusPanel = new TextViewer(model.getRusModel().text,this);
        engPanel = new TextViewer(model.getEngModel().text,this);
        audioPanel = new SoundViewer(model.getAudioModel(),this);

        frame.getContentPane().add(rusPanel, BorderLayout.CENTER);
        frame.getContentPane().add(engPanel, BorderLayout.EAST);
        frame.getContentPane().add(audioPanel, BorderLayout.SOUTH);
        frame.setSize(600, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void update (AbstractViewer viewer){
        int currentSentence = 0 ;
        int currentPause;
        if(viewer == rusPanel) {
            currentSentence = model.getRusModel().findSentence(viewer.position);
            model.getRusModel().setCurrentSentence(currentSentence);
            currentPause = model.getRusModel().findPause(viewer.position);
            model.getRusModel().setCurrentPause(currentPause);
            model.getEngModel().setSentenceFromText(currentSentence);
            model.getAudioModel().setPauseFromText(currentPause);
            audioPanel.update(model.getAudioModel().getPausePosition(model.getAudioModel().getCurrentPause() + 1));
        }else if(viewer == engPanel){

            currentSentence = model.getEngModel().findSentence(viewer.position);
            model.getEngModel().setCurrentSentence(currentSentence);
            model.getRusModel().setSentenceFromText(currentSentence);
            currentPause = model.getRusModel().findPause(currentSentence);
            model.getRusModel().setCurrentPause(currentPause);
            model.getAudioModel().setPauseFromText(currentPause);
            audioPanel.update(model.getAudioModel().getPausePosition(model.getAudioModel().getCurrentPause() + 1));
        }else if(viewer == audioPanel){
            currentPause = model.getAudioModel().findPause(viewer.position);
            model.getAudioModel().setCurrentPause(currentPause);
            model.getRusModel().setSentenceFromSound(currentPause);
            currentSentence = model.getRusModel().getCurrentSentence();
            model.getRusModel().setCurrentPause(currentPause);
            model.getEngModel().setSentenceFromText(currentSentence);

        }

        engPanel.update(model.getEngModel().getSentencePosition(model.getEngModel().getCurrentSentence()));
        rusPanel.update(model.getRusModel().getSentencePosition(model.getRusModel().getCurrentSentence()));
    }

}

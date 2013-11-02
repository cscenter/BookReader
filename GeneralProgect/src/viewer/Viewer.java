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

    public Viewer(Model model){
        frame = new JFrame("Name");
        rusPanel = new TextViewer(model.getRusModel().getText());
        engPanel = new TextViewer(model.getEngModel().getText());
        audioPanel = new SoundViewer(model.getAudioModel());

        frame.getContentPane().add(rusPanel, BorderLayout.CENTER);
        frame.getContentPane().add(engPanel, BorderLayout.EAST);
        frame.getContentPane().add(audioPanel, BorderLayout.SOUTH);
        frame.setSize(600, 400);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

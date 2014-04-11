package viewer;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import javax.swing.*;
import reader.ReaderException;
import reader.XMLReader;

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
    private String nameOfXMLFile;
    private float frameRate=1;

    public Viewer(Model model, String nameOfXMLFile) throws InterruptedException {
        this.nameOfXMLFile = nameOfXMLFile;
        frame = new JFrame("BookReader");
        this.model = model;
        rusPanel = new TextViewer(model.getRusModel().getText(),this);
        engPanel = new TextViewer(model.getEngModel().getText(),this);
        audioPanel = new SoundViewer(model.getAudioModel(),this);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,rusPanel,engPanel);
        //JPanel panel = new JPanel();
       // panel.add(splitPane);

        splitPane.setDividerLocation(300);

        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPane,audioPanel);

     //   frame.getContentPane().add(splitPane);
      //  frame.getContentPane().add(engPanel, BorderLayout.EAST);
        frame.getContentPane().add(verticalSplitPane);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setVisible(true);
        frameRate = model.getAudioModel().getAudioFormat().getFrameRate();
        MyShutdownHook shutdownHook = new MyShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
    
    private void shutdown() {
        try {
            XMLReader xmlReader = new XMLReader(nameOfXMLFile);
            xmlReader.setModel(model);
            xmlReader.writeXML();
        } catch (ReaderException ex) {
            Logger.getLogger(Viewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private class MyShutdownHook extends Thread {

        public void run() {
            shutdown();
        }
    }
    private void updateWithRusModel(){
        int currentSentence = model.getRusModel().getCurrentSentence();
        model.getEngModel().setSentenceFromText(model.getRusModel());
        int curentSec = model.getAudioModel().getConcordance().get(currentSentence);
        audioPanel.update((int)(curentSec*frameRate));
    }

    public void update (AbstractViewer viewer){
        int currentSentence = 0 ;
        int currentAudioPosition;
        if(viewer == rusPanel) {
            currentSentence = model.getRusModel().findSentence(viewer.position);
            model.getRusModel().setCurrentSentence(currentSentence);
            updateWithRusModel();
        }else if(viewer == engPanel){
            currentSentence = model.getEngModel().findSentence(viewer.position);
            model.getEngModel().setCurrentSentence(currentSentence);
            model.getRusModel().setSentenceFromText(model.getEngModel());
            currentSentence = model.getRusModel().getCurrentSentence();
            int currentSec = model.getAudioModel().getConcordance().get(currentSentence);
            audioPanel.update((int)(currentSec*frameRate));
        }else if(viewer == audioPanel){
            int currentSec = (int)(viewer.position/frameRate);
            System.out.println("viewer.position " + viewer.position);
            System.out.println("lenAmpl " + model.getAudioModel().getShortAmplitude().length);
            currentSentence = model.getAudioModel().getConcordance().getSentence(currentSec);
            model.getRusModel().setCurrentSentence(currentSentence);
            model.getEngModel().setSentenceFromText(model.getRusModel());
        }
        System.out.println("Р СџРЎР‚Р ВµР Т‘Р В»Р С•Р В¶Р ВµР Р…Р С‘Р Вµ ru: "+model.getRusModel().getCurrentSentence());
        System.out.println("Р СџРЎР‚Р ВµР Т‘Р В»Р С•Р В¶Р ВµР Р…Р С‘Р Вµ eng: "+model.getEngModel().getCurrentSentence());
        engPanel.update(model.getEngModel().getSentencePosition(model.getEngModel().getCurrentSentence()));
        rusPanel.update(model.getRusModel().getSentencePosition(model.getRusModel().getCurrentSentence()));
    }

}

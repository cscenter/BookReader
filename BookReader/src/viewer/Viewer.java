package viewer;

import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import javax.swing.*;
import javax.swing.KeyStroke;
import reader.*;

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
    private JLabel currPos;
    private Model model;
    private String nameOfXMLFile;
    private float frameRate=1;

    public Viewer(final Model model, final String nameOfXMLFile) throws InterruptedException {
        this.nameOfXMLFile = nameOfXMLFile;
        frame = new JFrame("SuperBook");
        this.model = model;
        frameRate = model.getAudioModel().getAudioFormat().getFrameRate();         
        rusPanel = new TextViewer(model.getRusModel(),this);
        engPanel = new TextViewer(model.getEngModel(),this);
        audioPanel = new SoundViewer(model.getAudioModel(),this);
        currPos = new JLabel("Current position:");
        addToolBar();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,rusPanel,engPanel);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);
        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,splitPane,audioPanel);
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,verticalSplitPane,currPos);
        frame.getContentPane().add(verticalSplitPane);
        frame.pack();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
        frame.addWindowListener(new WindowAdapter() {  
            
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog (
                        null, "Would you like to save changes?",
                        "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        XMLReader xml = new XMLReader();
                        xml.write("conc1.xml", model);
                    } catch (ReaderException ex) {
                        ex.showError();
                    }
                    System.out.println("Saving");
                }
                if (option == JOptionPane.YES_OPTION || option == JOptionPane.NO_OPTION){
                    frame.dispose();
                }
            }            
        });
    }
    
    private void addToolBar(){
        Font font = new Font("Verdana", Font.PLAIN, 11); 
        JMenuBar menuBar = new JMenuBar();
         
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);
        fileMenu.setMnemonic(KeyEvent.VK_F);
         
        JMenu newMenu = new JMenu("New");
        newMenu.setFont(font);
        fileMenu.add(newMenu);
         
        JMenuItem textFileItem = new JMenuItem("Text file");
        textFileItem.setFont(font);
        newMenu.add(textFileItem);
         
        JMenuItem audioFileItem = new JMenuItem("Audio file");
        audioFileItem.setFont(font);
        newMenu.add(audioFileItem);
        audioFileItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                JFileChooser dialog = new JFileChooser();
                dialog.showOpenDialog(frame);
                File file = dialog.getSelectedFile();
                String nameOfAudioFile = file.getPath();
                SoundReader audioBuilder;
                try {
                    SoundModel audioModel = new SoundReader().read(nameOfAudioFile);
                    model.setAudioModel(audioModel);
                } catch (ReaderException ex) {
                    ex.showError();
                }
            }
        });
        
        JMenuItem xmlFileItem = new JMenuItem("XML file");
        xmlFileItem.setFont(font);
        newMenu.add(xmlFileItem);
        xmlFileItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialog = new JFileChooser();
                dialog.showOpenDialog(frame);
                File file =dialog.getSelectedFile();
                String nameOfXMLFile = file.getPath();
                XMLReader xmlReader;
                try {
                    xmlReader = new XMLReader();
                    Model m = xmlReader.read(nameOfXMLFile);
                    model.getAudioModel().setConcordance(m.getAudioModel().getConcordance());
                } catch (ReaderException ex) {
                    ex.showError();
                }
            }
        });    
         
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setFont(font);
        fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                XMLReader xml;
                try {
                    xml = new XMLReader();
                    xml.write(nameOfXMLFile, model);
                    System.out.println("Saving");
                } catch (ReaderException ex) {
                    ex.showError();
                }
            }
        });
         
        fileMenu.addSeparator();
         
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setFont(font);
        fileMenu.add(exitItem);
         
        exitItem.addActionListener(new ActionListener() {          
            
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }           
        });

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }
    

    public void update (AbstractViewer viewer){
        int currentSentence = 0 ;
        int currentSec = 0 ;
        if (viewer == rusPanel) {
            currentSentence = model.getRusModel().findSentence(viewer.position);
            model.getRusModel().setCurrentSentence(currentSentence);
            model.getAudioModel().setCurrentSentence(currentSentence);
            model.getEngModel().setSentenceFromText(model.getRusModel());
            currentSec = model.getAudioModel().getConcordance().get(currentSentence);
            audioPanel.update((int)(currentSec*frameRate));
        } else if (viewer == engPanel){
            currentSentence = model.getEngModel().findSentence(viewer.position);
            model.getEngModel().setCurrentSentence(currentSentence);
            model.getRusModel().setSentenceFromText(model.getEngModel());
            currentSentence = model.getRusModel().getCurrentSentence();
            currentSec = model.getAudioModel().getConcordance().get(currentSentence);
            model.getAudioModel().setCurrentSentence(currentSentence);
            audioPanel.update((int)(currentSec*frameRate));
        } else if (viewer == audioPanel){
            currentSec = (int)(viewer.position/frameRate);
            System.out.println("viewer.position " + viewer.position);
            System.out.println("lenAmpl " + model.getAudioModel().getShortAmplitude().length);
            currentSentence = model.getAudioModel().getConcordance().getSentence(currentSec);
            model.getRusModel().setCurrentSentence(currentSentence);
            model.getEngModel().setSentenceFromText(model.getRusModel());
        }
                
        engPanel.update(model.getEngModel().getSentencePosition(model.getEngModel().getCurrentSentence()));
        rusPanel.update(model.getRusModel().getSentencePosition(model.getRusModel().getCurrentSentence()));
        currPos.setText("Current position:" + 
                        " rus = " + currentSentence + 
                        ", eng = " + model.getEngModel().getCurrentSentence() + 
                        ", sec = " + currentSec);
    }
}

package viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import javax.swing.*;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
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
    private String nameOfXMLFile="conc1.xml";
    private float frameRate=1;

    public Viewer(final Model model) throws InterruptedException {
       // this.nameOfXMLFile = nameOfXMLFile;
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
                        xml.write(nameOfXMLFile, model);
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
      //  newMenu.add(textFileItem);
        textFileItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialog = new JFileChooser();
                dialog.showOpenDialog(frame);
                File file = dialog.getSelectedFile();
                String nameOfText = file.getPath();
                try {
                    TextReader textReader = new TextReader();
                    TextModel rusModel = textReader.read(nameOfText, new Russian());
                    model.setRusModel(rusModel);
                    rusPanel.setModel(rusModel);
                    rusPanel.repaint();
                } catch (ReaderException ex) {
                    ex.showError();
                }
            }
        });    
         
        JMenuItem audioFileItem = new JMenuItem("Audio file");
        audioFileItem.setFont(font);
      //  newMenu.add(audioFileItem);
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
                File file = dialog.getSelectedFile();
                String nameOfXMLFile = file.getPath();
                XMLReader xmlReader;
                try {
                    xmlReader = new XMLReader();
                    Model m = xmlReader.read(nameOfXMLFile);
                    model.getAudioModel().setConcordance(m.getAudioModel().getConcordance());
                    model.getRusModel().setConcordance(m.getRusModel().getConcordance());
                    model.getEngModel().setConcordance(m.getEngModel().getConcordance());
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
        
        JMenu optionMenu = new JMenu("Options");
        optionMenu.setFont(font);
        JCheckBox checkConnect = new JCheckBox("Without connection", model.getUseConc());
        checkConnect.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                model.setUseConc(!model.getUseConc());
            }
        });
        checkConnect.setFont(font);       
        optionMenu.add(checkConnect);
        JButton countItem = new JButton("Count all concordances");
        countItem.setFont(font);    
        countItem .addActionListener(new ActionListener() {          
            
            public void actionPerformed(ActionEvent e) {
                model.getEngModel().countConcordance(model.getRusModel(), model.getUseConc());
                model.getRusModel().countConcordance(model.getEngModel(), model.getUseConc());
            }           
        });
        optionMenu.add(countItem);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
    }
    
    
    public void update (AbstractViewer viewer){
        int rusCurrentSentence = 0 ;
        int engCurrentSentence = 0 ;
        int currentSec = 0 ;
        if (viewer == rusPanel) {
            rusCurrentSentence = model.getRusModel().findSentence(viewer.position);
            model.getRusModel().setCurrentSentence(rusCurrentSentence);
            model.getAudioModel().setCurrentSentence(rusCurrentSentence);
            model.getEngModel().setSentenceFromText(model.getRusModel(),model.getUseConc());
            currentSec = model.getAudioModel().getConcordance().get(rusCurrentSentence);
            engCurrentSentence =  model.getEngModel().getCurrentSentence();
            audioPanel.update((int)(currentSec*frameRate));
       
        } else if (viewer == engPanel){
            engCurrentSentence = model.getEngModel().findSentence(viewer.position);
            model.getEngModel().setCurrentSentence(engCurrentSentence);
            model.getRusModel().setSentenceFromText(model.getEngModel(),model.getUseConc());
            rusCurrentSentence = model.getRusModel().getCurrentSentence();
            currentSec = model.getAudioModel().getConcordance().get(rusCurrentSentence);
            model.getAudioModel().setCurrentSentence(rusCurrentSentence);
            audioPanel.update((int)(currentSec*frameRate));
       
        } else if (viewer == audioPanel){
            currentSec = (int)(viewer.position/frameRate);
            System.out.println("viewer.position " + viewer.position);
            System.out.println("lenAmpl " + model.getAudioModel().getShortAmplitude().length);
            rusCurrentSentence = model.getAudioModel().getConcordance().getSentence(currentSec);
            System.out.println("rusCurrentSentence " + rusCurrentSentence);
            model.getRusModel().setCurrentSentence(rusCurrentSentence);
            model.getEngModel().setSentenceFromText(model.getRusModel(), model.getUseConc());
            engCurrentSentence =  model.getEngModel().getCurrentSentence();
        }
        model.getEngModel().setAnotherCurrentSentence(rusCurrentSentence);
        model.getRusModel().setAnotherCurrentSentence(engCurrentSentence);
        engPanel.update(model.getEngModel().getSentencePosition(engCurrentSentence));
        rusPanel.update(model.getRusModel().getSentencePosition(rusCurrentSentence));
        currPos.setText("Current position:" + 
                        " rus = " + rusCurrentSentence + 
                        ", eng = " + engCurrentSentence + 
                        ", sec = " + currentSec);
    }
    
    public Model getModel(){
        return model;
    }
}

package viewer;

import model.*;
import sound.PlayAudio;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import sun.awt.HorizBagLayout;

public class SoundViewer extends AbstractViewer {
    private SoundModel audioModel;
    private JButton playButton;
    private JButton stopButton;
    private JButton plusButton;
    private JButton minusButton;
    private JButton addConcButton;
    private final int SPEED_CHANGE_SCALE = 20;
    private final int WIDTH = 1800;
    private float frameRate;
    private int speedChangeY = 2000;
    private SoundLine line;
    private Thread thread = new Thread();
    private JSlider slider;
    private PlayAudio play;
    private final SoundViewer THIS = this;
    private int sentenseConc = 0;
    private JTextField tfSentFrom;
    private JTextField tfSentTo;
  
    public void writeAmplitude(GridBagConstraints c){
        line = new SoundLine(audioModel);
        this.add(line, c);
    }

    public SoundViewer(SoundModel model, Viewer viewer) throws InterruptedException {
        super(viewer);
        position = 0;
        audioModel = model;
        line = new SoundLine(audioModel);
        play = new PlayAudio(audioModel, line, THIS);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 250));
        JPanel panelButtons = new JPanel();
        JPanel panelConcordances = new JPanel();        
        JPanel panelLine = new JPanel();
        panelLine.setLayout(new GridLayout(1,1));
        panelLine.add(line);
        initButtons(panelButtons);
        initSlider();
        initPanelConcordances(panelConcordances);
        
        this.addMouseListener(new mouseAdapter());        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;  
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth  = GridBagConstraints.REMAINDER;  
        c.gridx = GridBagConstraints.RELATIVE; 
        c.gridy = GridBagConstraints.RELATIVE; 
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = GridBagConstraints.RELATIVE;
        this.add(panelButtons, c);
      //  c.anchor = GridBagConstraints.NORTH; 
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 20;
     //   c.insets = new Insets(40, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = 0.3;
        this.add(panelLine,c);
        c.weighty = 0;
        this.add(slider, c);
        this.add(panelConcordances, c);
        frameRate = audioModel.getAudioFormat().getFrameRate();
    }

    private void initButtons(JPanel panelButtons) {
        playButton = new JButton();
        stopButton = new JButton();
        plusButton = new JButton();
        minusButton = new JButton();
        
        String pathIcon = "resource/";
        ImageIcon iconPlay = new ImageIcon(pathIcon + "Play16.gif");
        ImageIcon iconStop = new ImageIcon(pathIcon + "Pause16.gif");
        ImageIcon iconPlus = new ImageIcon(pathIcon + "ZoomIn16.gif");
        ImageIcon iconMinus = new ImageIcon(pathIcon + "ZoomOut16.gif");
        
        playButton.setIcon(iconPlay);
        stopButton.setIcon(iconStop);
        plusButton.setIcon(iconPlus);
        minusButton.setIcon(iconMinus);
        
        addListenersToButtons();
        addButtonsToJPanel(panelButtons);
    }

    private void initSlider() {
        slider = new JSlider(0, audioModel.getShortAmplitude().length);
        slider.setValue(audioModel.getStart());
        ChangeListener changeListener = new sliderListener();
        slider.addChangeListener(changeListener);
    }
    

    private void addButtonsToJPanel(JPanel panelButtons) {
        panelButtons.add(playButton);
        panelButtons.add(stopButton);
        panelButtons.add(plusButton);
        panelButtons.add(minusButton);
    }
    
    private void initPanelConcordances(JPanel panelConcordances){
        panelConcordances.setBackground(Colors.concColor);
        addConcButton = new JButton();
        ImageIcon icon = new ImageIcon("resource/concTxtAudio.gif");
        addConcButton.setIcon(icon);
        
        ActionListener addConcListener = new addConcActionListener();
        addConcButton.addActionListener(addConcListener);
        tfSentFrom = new JTextField("1");
        tfSentFrom.setPreferredSize(new Dimension(50, 20));
        
        tfSentTo = new JTextField("0");
        tfSentTo.setPreferredSize(new Dimension(50, 20));
        panelConcordances.add(tfSentFrom);
        panelConcordances.add(addConcButton);
        panelConcordances.add(tfSentTo);
    }
            
    private void addListenersToButtons() {
        ActionListener playListener = new playActionListener();
        playButton.addActionListener(playListener);
        ActionListener stopListener = new stopActionListener();
        stopButton.addActionListener(stopListener);
        ActionListener plusListener = new plusActionListener();
        plusButton.addActionListener(plusListener);
        ActionListener minusListener = new minusActionListener();
        minusButton.addActionListener(minusListener);
    }

    @Override
    public void update(int value) {
        if (value > audioModel.getShortAmplitude().length)
            value = audioModel.getShortAmplitude().length - 1;

        position = value;
        line.setStart(position);
        slider.setValue(position);
        slider.setExtent(10);
        int sec = (int)((float)(position)/frameRate);
        tfSentTo.setText("" + sec);
        audioModel.setCurrentSecond(sec);
        line.setEnd(position + WIDTH * line.getScale());
        line.repaint();
    }

    public class nextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(line.getStart() + speedChangeY);

            line.setStart(line.getStart() + speedChangeY);
            line.setEnd(line.getStart() + speedChangeY + WIDTH);
            line.repaint();
        }
    }

    public class prevActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (line.getStart() > speedChangeY) {
                line.setStart(line.getStart() - speedChangeY);
                line.setEnd(line.getStart() - speedChangeY + WIDTH);
                line.repaint();
            }
        }
    }

    public class mouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            line.setVertX(getMousePosition().x);
            line.repaint();
            position = line.getStart() + getMousePosition().x;
            parent.update(SoundViewer.this);
        }
    }

    public class plusActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            line.setScale(line.getScale() - SPEED_CHANGE_SCALE);
            line.repaint();
        }
    }

    public class sliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            line.setStart(source.getValue());
            line.setEnd(source.getValue() + WIDTH);
            line.repaint();

        }
    }

    public class minusActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            line.setScale(line.getScale() + SPEED_CHANGE_SCALE);
            line.repaint();
        }
    }

    public class stopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (thread.isAlive()) {
                line.setStart(position);
                line.setEnd(position + WIDTH);
                System.out.println("Stop");
                thread.interrupt();
            }
        }
    }
    
    public class playActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!thread.isAlive()){

                thread = new Thread() {
                    @Override
                    public void run() {
                        try{
                            System.out.println("Start");
                            PlayAudio play;
                            play = new PlayAudio(audioModel, line, THIS);
                            System.out.println(position);
                            play.setStart(position);
                            play.playClip();
                        }catch (InterruptedException e){
                            System.out.println("SoundViewer: run");
                        }
                    }
                };
                thread.start();
            }
        }
    }
    
    public class addConcActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int sent = new Integer(tfSentFrom.getText());
            int pos = new Integer(tfSentTo.getText());
            audioModel.getConcordance().set(sent, pos);
            System.out.println("Add " + sent + ":" + pos);
            sentenseConc = sent+1;
            tfSentFrom.setText(""+sentenseConc);
        }
    }

}





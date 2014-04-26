package viewer;

import model.*;
import sound.PlayAudio;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class SoundViewer extends AbstractViewer {

    private SoundModel audioModel;
//    private JButton nextButton;
//    private JButton prevButton;
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
    private JTextField tfSentense;
    private JTextField tfPosition;
    public void writeAmplitude(){
        line = new SoundLine(audioModel);
        this.add(line, BorderLayout.CENTER);
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

        initButtons(panelButtons);
        initSlider();
        initPanelConcordances(panelConcordances);
        
        this.addMouseListener(new mouseAdapter());        
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;  
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth  = GridBagConstraints.REMAINDER;  
        c.gridy = GridBagConstraints.RELATIVE; 
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0.5;
        c.weighty = GridBagConstraints.RELATIVE;
        this.add(panelButtons, c);
        c.anchor = GridBagConstraints.CENTER; 
        c.insets = new Insets(40, 0, 0, 0);
        c.weighty = 0.2;
        this.add(line,c);
        c.weighty = 0;
        this.add(slider, c);
        this.add(panelConcordances);
        frameRate = audioModel.getAudioFormat().getFrameRate();
    }

    private void initButtons(JPanel panelButtons) {
        //nextButton = new JButton("\u2192");
       // prevButton = new JButton("\u2190");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        plusButton = new JButton("+");
        minusButton = new JButton("-");
        addListenersToButtons();
        addButtonsToJPanel(panelButtons);
    }

    private void initSlider() {
        slider = new JSlider(0, audioModel.getShortAmplitude().length);
        slider.setValue(audioModel.getStart());
        ChangeListener changeListener = new sliderListener();
        slider.addChangeListener(changeListener);
    }
    

    private void addButtonsToJPanel(JPanel buttons) {
      //  buttons.add(prevButton);
       // buttons.add(nextButton);
        buttons.add(playButton);
        buttons.add(stopButton);
        buttons.add(plusButton);
        buttons.add(minusButton);
    }
    
    private void initPanelConcordances(JPanel panelConcordances){
        addConcButton = new JButton("Add concordance");
        ActionListener addPointListener = new addConcActionListener();
        addConcButton.addActionListener(addPointListener);
        JLabel labelSentense = new JLabel("Sentense: ");
         tfSentense = new JTextField("00001");
     
        JLabel labelPosition = new JLabel("Second: ");
        tfPosition = new JTextField("00000");
        panelConcordances.add(addConcButton);
        panelConcordances.add(labelSentense);
        panelConcordances.add(tfSentense);
        panelConcordances.add(labelPosition);
        panelConcordances.add(tfPosition);
    }
    
        
    private void addListenersToButtons() {
//        ActionListener nextListener = new nextActionListener();
//        nextButton.addActionListener(nextListener);
//        ActionListener prevListener = new prevActionListener();
//        prevButton.addActionListener(prevListener);
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
//        System.out.println(value);
        if (value > audioModel.getShortAmplitude().length)
            value = audioModel.getShortAmplitude().length - 1;

 //       System.out.println("Sound position " + position);
        position = value;
        line.setStart(position);
        slider.setValue(position);
        slider.setExtent(10);
        int sec = (int)((float)(position)/frameRate);
        tfPosition.setText(""+ sec);
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
            //line.setEnd(line.getEnd() + speedChangeY);
            line.repaint();
        }
    }

    public class prevActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (line.getStart() > speedChangeY) {
                line.setStart(line.getStart() - speedChangeY);
                line.setEnd(line.getStart() - speedChangeY + WIDTH);
                //line.setEnd(line.getEnd() - speedChangeY);
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
//            speedChangeY -=  SPEED_CHANGE_SCALE;
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
//            speedChangeY += SPEED_CHANGE_SCALE;
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
            int sent = new Integer(tfSentense.getText());
            int pos = new Integer(tfPosition.getText());
            audioModel.getConcordance().set(sent, pos);
            System.out.println("Add " + sent + ":" + pos);
            sentenseConc = sent+1;
            tfSentense.setText(""+sentenseConc);
        }
    }

}





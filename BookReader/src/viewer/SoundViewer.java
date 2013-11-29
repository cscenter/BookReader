package viewer;

import model.*;
import sound.PlayAudio;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class SoundViewer extends AbstractViewer{

    private SoundModel audioModel;
//    private JButton nextButton;
//    private JButton prevButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton plusButton;
    private JButton minusButton;
    private final int SPEED_CHANGE_SCALE = 80;
    private final int WIDTH = 1800;
    private int speedChangeY = 2000;
    private SoundLine line;
    private Thread thread = new Thread();
    private JSlider slider;
    private PlayAudio play;


    public void writeAmplitude(){
        line = new SoundLine(audioModel);
        this.add(line, BorderLayout.CENTER);
    }

    public SoundViewer(SoundModel model, Viewer viewer) throws InterruptedException {
        super(viewer);
        position = 0;
        audioModel = model;
        play = new PlayAudio(audioModel);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 250));
        JPanel buttons = new JPanel();

        initButtons();
        addListenersToButtons();
        addButtonsToJPanel(buttons);
        initSlider();

        this.add(slider, BorderLayout.SOUTH);
        this.addMouseListener(new mouseAdapter());
        this.add(buttons, BorderLayout.NORTH);
        writeAmplitude();
    }

    private void initButtons() {
        //nextButton = new JButton("\u2192");
       // prevButton = new JButton("\u2190");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        plusButton = new JButton("+");
        minusButton = new JButton("-");
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
//        TODO: delete value += 5;

        System.out.println(value);
        if (value > audioModel.getShortAmplitude().length)
            value = audioModel.getShortAmplitude().length - 1;

        int positionFromSilence =  value;
        line.setStart(positionFromSilence);
        slider.setValue(positionFromSilence);
        line.setEnd(positionFromSilence + WIDTH * line.getScale());
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
            speedChangeY -=  SPEED_CHANGE_SCALE;
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
            speedChangeY += SPEED_CHANGE_SCALE;
            line.setScale(line.getScale() + SPEED_CHANGE_SCALE);
            line.repaint();
        }
    }

    public class stopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            play.setRun(false);
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
                            System.out.println("Start play");
                            play.setRun(true);
                            while(play.getRun()) {
                                play.setStart(line.getStart() + position);
                                play.setEnd(line.getStart() + 160000 + position);
                                play.playClip();
                            }
                            System.out.println("Stop play");
                        }catch (InterruptedException e){
                            System.out.println("SoundViewer: run");
                        }
                    }
                };
                thread.start();
            }
        }

    }

}





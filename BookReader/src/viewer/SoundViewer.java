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
    private int speedChangeScale = 8;
    private int speedChangeY = 2000;
    private  SoundLine line;
    private Thread thread = new Thread();
    private JSlider slider;

    public void writeAmplitude(){
        line = new SoundLine(audioModel);
        this.add(line, BorderLayout.CENTER);
    }

    public SoundViewer(SoundModel model, Viewer viewer){
        super(viewer);
        position = 0;
        audioModel = model;
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
        value += 5;
        int positionFromSilence =  audioModel.getSilence()[value];
        line.setStart(positionFromSilence);
        slider.setValue(positionFromSilence);
        line.setEnd(positionFromSilence + 1800 * line.getScale());
        line.repaint();
    }

    public class nextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(line.getStart() + speedChangeY);

            line.setStart(line.getStart() + speedChangeY);
            line.setEnd(line.getStart() + speedChangeY + 1600);
            //line.setEnd(line.getEnd() + speedChangeY);
            line.repaint();
        }
    }

    public class prevActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (line.getStart() > speedChangeY) {
                line.setStart(line.getStart() - speedChangeY);
                line.setEnd(line.getStart() - speedChangeY + 1600);
                //line.setEnd(line.getEnd() - speedChangeY);
                line.repaint();
            }
        }
    }

    public class mouseAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            position = calculateNumberOfSilence((double) (line.getStart() + position));
            parent.update(SoundViewer.this);
            line.setVertX(getMousePosition().x);
            line.repaint();
//            printSilence();
        }


        int calculateNumberOfSilence(double time) {
            Integer[] arrSilence = audioModel.getSilence();
            for (int i = arrSilence.length - 1; i >= 0; i--) {
                if (arrSilence[i] < time) {
                    return i + 1;
                }
            }
            return -1;
        }

        void printSilence() {

            Integer[] arrSilence = audioModel.getSilence();
            for (int i = 0; i < arrSilence.length; i++) {
                System.out.println(arrSilence[i]);
            }

        }

    }

    public class plusActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            speedChangeY -=  speedChangeScale * 10;
            line.setScale(line.getScale() - speedChangeScale);
            line.repaint();
        }
    }

    public class sliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            line.setStart(source.getValue());
            line.setEnd(source.getValue() + 1600);
            line.repaint();

        }
    }

    public class minusActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            speedChangeY += speedChangeScale * 10;
            line.setScale(line.getScale() + speedChangeScale);
            line.repaint();
        }
    }

    public class stopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (thread.isAlive()) {
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
                            PlayAudio play;
                            play = new PlayAudio(audioModel);
                            play.setStart(line.getStart() + position);
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

}





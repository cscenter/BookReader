package viewer;

import model.*;
import sound.PlayAudio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SoundViewer extends AbstractViewer{

    private SoundModel audioModel;
    private JButton nextButton;
    private JButton prevButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton plusButton;
    private JButton minusButton;
    private int speedChangeScale = 8;
    private int speedChangeY = 200;
    private  SoundLine line;

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
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                line.setVertX(getMousePosition().x);
                line.repaint();
                position = getMousePosition().x;
               // parent.update(SoundViewer.this);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        this.add(buttons, BorderLayout.NORTH);
        writeAmplitude();
    }

    private void initButtons() {
        nextButton = new JButton("\u2192");
        prevButton = new JButton("\u2190");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        plusButton = new JButton("+");
        minusButton = new JButton("-");
    }

    private void addButtonsToJPanel(JPanel buttons) {
        buttons.add(prevButton);
        buttons.add(nextButton);
        buttons.add(playButton);
        buttons.add(stopButton);
        buttons.add(plusButton);
        buttons.add(minusButton);
    }

    private void addListenersToButtons() {
        ActionListener nextListener = new nextActionListener();
        nextButton.addActionListener(nextListener);
        ActionListener prevListener = new prevActionListener();
        prevButton.addActionListener(prevListener);
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
    public void update(int position) {
        position += 14000;
        line.setStart(position );
//        line.setEnd(position + 1800);
        line.repaint();
    }

    public class nextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            line.setStart(line.getStart() + speedChangeY);
            //line.setEnd(line.getEnd() + speedChangeY);
            line.repaint();
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

    public class minusActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            speedChangeY += speedChangeScale * 10;
            line.setScale(line.getScale() + speedChangeScale);
            line.repaint();
        }
    }

    public class prevActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (line.getStart() - speedChangeY > 0) {
                line.setStart(line.getStart() - speedChangeY);
                //line.setEnd(line.getEnd() - speedChangeY);
                line.repaint();
            }
        }
    }

    public class stopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    public class playActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            PlayAudio play;
            try {
                play = new PlayAudio(audioModel);
                play.setStart(line.getStart() + position);
                play.playClip();
            } catch (InterruptedException e1) {
                System.out.println("SoundViewer playActionListener");
            }

        }
    }
}





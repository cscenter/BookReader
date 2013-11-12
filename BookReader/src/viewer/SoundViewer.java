package viewer;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 15.10.13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
import model.*;
import reader.SoundReader;
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
        nextButton = new JButton("\u2192");
        prevButton = new JButton("\u2190");
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");

        ActionListener nextListener = new nextActionListener();
        nextButton.addActionListener(nextListener);
        ActionListener prevListener = new prevActionListener();
        prevButton.addActionListener(prevListener);
        ActionListener playListener = new playActionListener();
        playButton.addActionListener(playListener);
        ActionListener stopListener = new stopActionListener();
        playButton.addActionListener(stopListener);

        buttons.add(prevButton, BorderLayout.EAST);
        buttons.add(playButton, BorderLayout.CENTER);
        buttons.add(nextButton, BorderLayout.WEST);
        buttons.add(stopButton, BorderLayout.SOUTH);




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

    @Override
    public void update(int position) {
        position += 14000;
        line.setStart(position );
        line.setEnd(position + 1800);
        line.repaint();
    }

    public class nextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            line.setStart(line.getStart() + 200);
            line.setEnd(line.getEnd() + 200);
            line.repaint();
        }
    }

    public class prevActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            line.setStart(line.getStart() - 200);
            line.setEnd(line.getEnd() - 200);
            line.repaint();
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





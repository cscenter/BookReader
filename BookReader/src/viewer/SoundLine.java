package viewer;
import model.SoundModel;
import sound.SoundFindSilence;
import javax.swing.*;
import java.awt.*;

class SoundLine extends JPanel{

    private short shortAmplitudeArr[];
    private SoundModel audioModel;
    private int start;
    private Graphics2D g2d;
    private int vertX;
    private boolean paintVert = false;
    private int scale = 1;

    public SoundLine(SoundModel model){
        audioModel = model;
        shortAmplitudeArr = model.getShortAmplitude();
        setStart(model.getFrom());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int i;

        g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.drawLine(0, 100, 10000, 100);
        if (paintVert) {
            g2d.setStroke(new BasicStroke(4,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.drawLine(vertX, -200, vertX, 200);
            paintVert = false;
        }
        g2d.setStroke(new BasicStroke(1,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.setColor(Color.BLUE);
        addSpectogram(g2d);


    }

    private void addSpectogram(Graphics2D g2d) {
        boolean pauses[] = audioModel.getBooleanPauses();
        int distance = 5;
        for (int i = start; i < shortAmplitudeArr.length - (2 * scale); i += scale){
            int countPause = 0;
            for (int j = i; j < i + scale; j++) {
                if (!pauses[i / SoundFindSilence.getLengthFrame()]) {
                    countPause++;
                }
            }
            if (countPause > scale * 2 / 3) {
                g2d.setColor(Color.GREEN);
            } else {
                g2d.setColor(Color.BLUE);
            }
            int y1 = 0;
            int y2 = 0;

            for (int j = i; j < i + scale; j++) {
                y1 += shortAmplitudeArr[j];
                y2 += shortAmplitudeArr[j + scale];
            }

            g2d.drawLine(distance, ((int) (y1 * 0.001) + 100),
                    distance + 1, ((int) (y2 * 0.001) + 100));
            distance += 1;
        }
    }

    public void setVertX(int value){
        vertX = value;
        paintVert = true;
    }

    public void setScale(int value){
        if (value < 1) {
            value = 1;
        }
        scale = value;
    }

    public int getScale(){
        return scale;
    }

    public int getStart(){
        return start;
    }

    public void setStart(int value){
        if ( (value < 0) || (value >= shortAmplitudeArr.length) ){
            start = 0;
        }
        else{
            start = value;
        }
    }
}
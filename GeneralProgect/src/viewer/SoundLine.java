package viewer;



import model.SoundModel;
import javax.swing.*;
import java.awt.*;

class SoundLine extends JPanel{

    private short shortAmplitudeArr[];
    private SoundModel audioModel;
    private int start;
    private int end;
    private Graphics2D g2d;
    private int vertX;
    private boolean paintVert = false;

    public SoundLine(SoundModel model){
        audioModel = model;
        shortAmplitudeArr = model.getShortAmplitude();
        setStart(model.getFrom());
        setEnd(model.getTo());
    }

    public void setStart(int value){
        if ( (value < 0) || (value >= shortAmplitudeArr.length) ){
            start = 0;
        }
        else{
            start = value;
        }
    }

    public void setEnd(int value){
        if ( (value < 0) || (value >= shortAmplitudeArr.length) ){
            end = shortAmplitudeArr.length;
        }
        else{
            end = value;
        }
    }
    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int i;
        int distance = 5;
        g2d = (Graphics2D) g;
        boolean pauses[] = audioModel.getBooleanPauses();
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
        for (i = start; i < end - 1; i += 1){
            if (pauses[i / SoundFindPauses.getLengthFrame()]) {
                g.setColor(Color.GREEN);
            }
            else {
                g.setColor(Color.BLUE);
            }
            g.drawLine(distance, ( (int)(shortAmplitudeArr[i]*0.001) + 100) ,
                    distance + 1, ((int)(shortAmplitudeArr[i + 1]*0.001) + 100));
            distance += 1;
        }
    }

    public void setVertX(int value){
        vertX = value;
        paintVert = true;
    }

    public boolean getPaintVert(){
        return paintVert;
    }

    public int getVertX(){
        return vertX;
    }

}
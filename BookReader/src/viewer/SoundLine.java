package viewer;
import model.SoundModel;
import javax.swing.*;
import java.awt.*;

public class SoundLine extends JPanel{

    private short shortAmplitudeArr[];
    private SoundModel audioModel;
    private int start;
    private int end;
    private Graphics2D g2d;
    private int vertX;
    private boolean paintVert = false;
    private int scale = 1;
    private final double SCALE_Y = 0.005;
    private final int OFFSET_Y = 100;
    private  final int LENGTH_FRAME = 100;
    public SoundLine(SoundModel model){
        audioModel = model;
        shortAmplitudeArr = model.getShortAmplitude();
        super.setBackground(Colors.backEqualizerColor);
        setStart(model.getStart());
        setEnd(model.getEnd());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(Colors.lineColor);
        g2d.drawLine(0, 100, 10000, 100); // Draw the axis Y
        if (paintVert) {
            g2d.setStroke(new BasicStroke(4,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.drawLine(vertX, -200, vertX, 200);
            paintVert = false;
        }
        int x = 10;
        int shift = 180;
        for (int i = start; i < end - 100; i += shift) {
            g2d.setColor(Colors.markerColor);
            g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.drawLine(x, -200, x, 200);
            g2d.setColor(Colors.equalizerColor);
            g2d.drawString(""+(i/audioModel.getAudioFormat().getFrameRate()), x, 2 * OFFSET_Y);
            x += shift;

        }
        g2d.setStroke(new BasicStroke(1,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.setColor(Colors.backEqualizerColor);
        addSpectogram(g2d);
    }

    private void addSpectogram(Graphics2D g2d) {
        int distance = 0;
        end = scale * (end - 2);
        if (end > shortAmplitudeArr.length)
            end = shortAmplitudeArr.length - 2 * scale;
        for (int i = start; i < end; i += scale){
            int countPause = 0;            
            g2d.setColor(Colors.equalizerColor); 
            int y1 = 0;
            int y2 = 0;
            for (int j = i; j < i + scale; j++) {
                y1 += shortAmplitudeArr[j];
                y2 += shortAmplitudeArr[j + scale];
            }
            y1 /= scale;
            y2 /= scale;
            g2d.drawLine(distance, ((int) (y1 * SCALE_Y) + OFFSET_Y),
                    distance + 1, ((int) (y2 * SCALE_Y) + OFFSET_Y));
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
    public void setEnd(int value){
        if ( (value < 0) || (value >= shortAmplitudeArr.length) ){
            end = shortAmplitudeArr.length;
        }
        else{
            end = value;
        }
    }
}
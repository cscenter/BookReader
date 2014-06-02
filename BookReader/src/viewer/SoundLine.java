package viewer;
import model.SoundModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.Concordance;
import model.Point;

public class SoundLine extends JPanel{

    private short shortAmplitudeArr[];
    private SoundModel audioModel;
    private int start;
    private int end;
    private Graphics2D g2d;
    private int vertX;
    private boolean paintVert = false;
    private int scale = 1;
    private float frameRate;
    private final double SCALE_Y = 0.005;
    private final int OFFSET_Y = 100;
    private  final int LENGTH_FRAME = 100;
    public SoundLine(SoundModel model){
        audioModel = model;
        frameRate = audioModel.getAudioFormat().getFrameRate();
        shortAmplitudeArr = model.getShortAmplitude();
        super.setBackground(Colors.backEqualizerColor);
        setStart(model.getStart());
        setEnd(model.getEnd());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(Colors.markerColor);
        g2d.drawLine(0, 100, 10000, 100); // Draw the axis Y
        if (paintVert) {
            g2d.setColor(Colors.lineColor);
            g2d.setStroke(new BasicStroke(4,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.drawLine(vertX, -200, vertX, 200);            
            g2d.drawString(""+((vertX*scale+start)/frameRate), vertX, 2 * OFFSET_Y);
            paintVert = false;
            g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        }
        int x = 10;
      //  x=0;
        int shift = 180;
        for (int i = start; i < end - 100; i += scale*shift) {
            g2d.setColor(Colors.markerColor);
//            g2d.setStroke(new BasicStroke(2,
//                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.drawLine(x, -200, x, 200);
       //     g2d.setColor(Colors.concColor);
            g2d.drawString(""+(i/frameRate), x, 2 * OFFSET_Y);
            x += shift;

        }
        g2d.setColor(Colors.markerColor);
             
              
        g2d.setStroke(new BasicStroke(1,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.setColor(Colors.backEqualizerColor);
        addSpectogram(g2d);
    }

    private void addSpectogram(Graphics2D g2d) {
        int distance = 0;
        int shiftX = 1, shiftY = 2;
        end = scale * (end - 2);
        if (end > shortAmplitudeArr.length)
            end = shortAmplitudeArr.length - 2 * scale;
        ArrayList<Point> conc = audioModel.getConcordance().getConc();
        int sent = audioModel.getConcordance().getSentence((int)(start/frameRate));
        g2d.setColor(Colors.getSentColor(sent));
//        g2d.setStroke(new BasicStroke(4,
//                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        for (int i = start; i < end; i += scale){
            if (sent+1 < conc.size() && conc.get(sent+1).getValueSentence()*frameRate < i){
                sent++;
                g2d.setColor(Colors.getSentColor(sent));
                int shift = 2;
              //  g2d.setColor(Colors.concColor);
                g2d.setStroke(new BasicStroke(4,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g2d.drawString(""+sent, distance+shift, OFFSET_Y/2);
                g2d.drawLine(distance, -200, distance, 200);
                g2d.drawString(""+(i/frameRate), distance+shift, 2 * OFFSET_Y);
                g2d.setStroke(new BasicStroke(1,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
    
            }
//            g2d.setStroke(new BasicStroke(2,
//                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            int countPause = 0;      
            
            int y1 = 0;
            int y2 = 0;
            for (int j = i; j < i + scale; j++) {
                y1 += shortAmplitudeArr[j];
                y2 += shortAmplitudeArr[j + scale];
            }
            y1 /= scale;
            y2 /= scale;
                  
            g2d.setColor(Colors.equalizerColor);
            g2d.drawLine(distance, ((int) (y1 * SCALE_Y) + OFFSET_Y),
                    distance + 1, ((int) (y2 * SCALE_Y) + OFFSET_Y));
            
//            g2d.setStroke(new BasicStroke(1,
//                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            g2d.setColor(Colors.getSentColor(sent));
            g2d.drawLine(distance+shiftX, ((int) (y1 * SCALE_Y) + OFFSET_Y+shiftY),
                    distance + 1+shiftX, ((int) (y2 * SCALE_Y) + OFFSET_Y+shiftY));
            g2d.setColor(Colors.equalizerColor);
            g2d.drawLine(distance+2*shiftX, ((int) (y1 * SCALE_Y) + OFFSET_Y+2*shiftY),
                    distance + 1+2*shiftX, ((int) (y2 * SCALE_Y) + OFFSET_Y+2*shiftY));
            
            distance += 1;
        }
//        g2d.setStroke(new BasicStroke(2,
//                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
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
    
    public int getEnd(){
        return end;
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
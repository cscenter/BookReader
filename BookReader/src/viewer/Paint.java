package viewer;

import model.SoundModel;
import sound.SoundFindSilence;

import javax.swing.*;
import java.awt.*;

import java.util.List;

/**
 * Oskina Olga
 * SPBGPU
 * 2013
 */
public class Paint extends JPanel{

        private short shortAmplitudeArr[];
        private SoundModel audioModel;
        private int start;
        private int end;
        private Graphics2D g2d;
        private int vertX;
        private boolean paintVert = false;
        private int scale = 1;
        private final double SCALE_Y = -0.025;
        private final int OFFSET_Y = 400;

        private List<Double> eMaxArr;
        private List<Double> eMinArr;
        private List<Double> thresholdArr;
        private List<Double> energy;


        public Paint(SoundModel model){
            audioModel = model;
            shortAmplitudeArr = model.getShortAmplitude();
            setStart(model.getStart());
            setEnd(model.getEnd());

            eMaxArr = audioModel.getEMaxArr();
            eMinArr = audioModel.getEMinArr();
            thresholdArr = audioModel.getThresholdArr();
            energy = audioModel.getEnergy();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.drawLine(0, OFFSET_Y, 10000, OFFSET_Y); // Draw the axis Y

            int distance = 0;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            int scale = 100;
            int endPaint = end * scale;
            if (endPaint > eMaxArr.size())
                endPaint = eMaxArr.size();
            for (int i = start; i < endPaint - 2*scale; i += scale) {

                double y1 = 0;
                double y2 = 0;
                for (int j = i; j < i + scale; j++) {
                    y1 += eMaxArr.get(j / 250);
                }
                for (int j = i + scale; j < i + 2*scale; j++) {
                    y2 += eMaxArr.get(j / 250);
                }
                y1 /= scale;
                y2 /= scale;


                g2d.drawLine(distance, (int)( y1* SCALE_Y + OFFSET_Y),
                        distance + 1, (int)( y2 * SCALE_Y + OFFSET_Y));
                distance += 1;
            }



//          thresholdArr
            distance = 0;
            g2d.setColor(Color.GREEN);

            g2d.setStroke(new BasicStroke(3,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            for (int i = start; i < endPaint - 2*scale; i += scale) {

                double y1 = 0;
                double y2 = 0;
                for (int j = i; j < i + scale; j++) {
                    y1 += thresholdArr.get(j / 250);
                }
                for (int j = i + scale; j < i + 2*scale; j++) {
                    y2 += thresholdArr.get(j / 250);
                }
                y1 /= scale;
                y2 /= scale;


                g2d.drawLine(distance, (int)( y1* SCALE_Y + OFFSET_Y),
                        distance + 1, (int)( y2 * SCALE_Y + OFFSET_Y));
                distance += 1;

            }
                    distance = 0;
            g2d.setColor(Color.ORANGE);

            g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            for (int i = start; i < endPaint - 2*scale; i += scale) {

                double y1 = 0;
                double y2 = 0;
                for (int j = i; j < i + scale; j++) {
                    y1 += energy.get(j / 250);
                }
                for (int j = i + scale; j < i + 2*scale; j++) {
                    y2 += energy.get(j / 250);
                }
                y1 /= scale;
                y2 /= scale;


                g2d.drawLine(distance, (int)( y1* SCALE_Y + OFFSET_Y),
                        distance + 1, (int)( y2 * SCALE_Y + OFFSET_Y));
                distance += 1;
            }

//          eMIN
            distance = 0;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(1,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            for (int i = start; i < endPaint - 2*scale; i += scale) {

                double y1 = 0;
                double y2 = 0;
                for (int j = i; j < i + scale; j++) {
                    y1 += eMinArr.get(j / 250);
                }
                for (int j = i + scale; j < i + 2*scale; j++) {
                    y2 += eMinArr.get(j / 250);
                }
                y1 /= scale;
                y2 /= scale;


                g2d.drawLine(distance, (int)( y1* SCALE_Y + OFFSET_Y),
                        distance + 1, (int)( y2 * SCALE_Y + OFFSET_Y));
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




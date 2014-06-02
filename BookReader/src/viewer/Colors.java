package viewer;

import java.awt.Color;



public class Colors {
    
    public final static Color concColor = new Color(49, 35, 35);
    public final static Color markerColor = new Color(255,127,39);
    public final static Color markerColorLight = new Color(255,127+80,39+80);
    public final static Color backEqualizerColor = new Color(118,18,29);                    //124,132,157 (175,13,67) - (255,127,39)
    public final static Color equalizerColor = new Color(49, 35, 35);
    public final static Color equalizerColor1 = new Color(255, 27, 98);
    public final static Color equalizerColor2 = new Color(0,255,252);
    public final static Color lineColor = new Color(35,144,49);
    
    public static Color getSentColor(int i){
        if (i%2 == 1)
            return equalizerColor1;
        else
            return equalizerColor2;                 
    }
}

package model;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 15.10.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */

public class SoundModel extends AbstractModel{
    private short shortAmplitude[];
    private boolean pauses[];
    private int from;
    private int to;

    public SoundModel(short[] audio){
        setShortAmplitude(audio);
    }





    public short[] getShortAmplitude(){
        return shortAmplitude;
    }

    public void setShortAmplitude(short arr[]){
        shortAmplitude = arr;
    }

    public int getFrom(){
        return from;
    }

    public void setFrom(int value){
        from = value;
    }

    public int getTo(){
        return to;
    }

    public void setTo(int value){
        to = value;
    }

    public boolean[] getPauses(){
        return pauses;
    }

    public void setPauses(boolean arr[]){
        pauses = arr;
    }


}

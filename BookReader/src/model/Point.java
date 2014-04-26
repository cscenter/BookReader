package model;

import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Р В РІР‚С”Р В РЎвЂ�Р В Р’В·Р В Р’В°
 * Date: 07.12.13
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement
public class Point {
    @XmlAttribute(name="keySentence")
    private int keySentence;
    private int valueSentence;
    public Point(){}
    public Point(int key, int value){
        keySentence = key;
        valueSentence = value;
    }
    
    public int getKeySentence() {
        return keySentence;
    }

    public void setKeySentence(int keySentence) {
        keySentence = keySentence;
    }
 
    public int getValueSentence() {
        return valueSentence;
    }

    public void setValueSentence(int valueSentence) {
        valueSentence = valueSentence;
    }
}

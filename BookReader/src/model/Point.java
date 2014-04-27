package model;

import javax.xml.bind.annotation.*;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement
public class Point {
    @XmlAttribute(name="keySentence")
    private int keySentence;
    @XmlAttribute(name="valueSentence")
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

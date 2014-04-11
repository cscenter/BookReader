/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Internet
 */
@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement
public class Concordance {    
    @XmlAttribute
    public String fName = "";
    @XmlAttribute
    public String language = "";
    public ArrayList <Integer> conc;
    
    public Concordance(){
        conc = new ArrayList <>();
        conc.add(0);
    }
    
    public Concordance(String fName){
        this.fName = fName;
        conc = new ArrayList <>();
        conc.add(0);
    }
   
    public int get(int key){
        if (conc.size() <= key){
            redefine(key);
        }
        return conc.get(key);
    }
    
    public void set(int key, int value){
        if (conc.size() <= key){
            redefine(key);
        }
        conc.add(key, value);
    }
    
    
    private void redefine(int key){
        int lastKey = conc.size() - 1;
            int val = conc.get(lastKey);
            while (lastKey <= key){
                conc.add(val);
                lastKey++;
            }
    }
    
    public int getSentence(int sec){
        int l = 0;
        int r = conc.size();
        int j;
        int currentSent = 0;
        while (r-l > 1){
            j = (r+l)/2;
            if (conc.get(j) >= sec) r = j;
            else {
                l = j;
                currentSent = conc.get(j);
            }
        }
        return currentSent;
    }
    
}

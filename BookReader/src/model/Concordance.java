/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Internet
 */
@XmlAccessorType( XmlAccessType.FIELD)
@XmlRootElement
public class Concordance {    
    @XmlElement(name = "conc")
    private ArrayList <Point> conc;
    
    public Concordance(){
        init();
    }
    
    private void init(){
        conc = new ArrayList <>();
        conc.add(new Point(0,0));
    }
   
    public int get(int key){
        if (conc.size() <= key){
            redefine(key);
        }
        return get_(key);
    }
    
    public void set(int key, int value){
        if (conc.size() <= key){
            redefine(key);
        }
        add_(key, value);
    }
    
//    @XmlElement
    public ArrayList<Point> getConc(){
        return conc;
    }
    
  //  @XmlElement
    public void setConc(ArrayList<Point> conc){
        this.conc = conc;
    }
    
    
    private int get_(int key){
        return conc.get(key).getValueSentence();
    }
    
    private void add_(int key, int value){
        if (key < conc.size())
            set_(key,value);
        else
            conc.add(new Point(key, value));
    }
    
    private void set_(int key, int value){
        conc.set(key, new Point(key, value));
    }
    
    private void redefine(int key){
        int lastKey = conc.size() - 1;
            int val = get_(lastKey);
            while (lastKey < key){
                lastKey++;
                add_(lastKey, val);
            }
    }
    
    public int getSentence(int sec){
        int l = 0;
        int r = conc.size();
        int j;
        int currentSent = 0;
        while (r-l > 1){
            j = (r+l)/2;
            if (get_(j) >= sec) r = j;
            else {
                l = j;
            }
        }
        currentSent = l;
        return currentSent;
    }    
}

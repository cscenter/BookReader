package model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType( XmlAccessType.NONE)
@XmlRootElement
public class AbstractModel {

    protected int currentSentence;
    protected int currentPause;
    protected int currentPosition;
    protected int currentSecond;
    protected Integer[] pauses;
    protected Integer[] sentences;
    @XmlElement
    protected Concordance concordance;
    
    public Concordance getConcordance(){
        return concordance;
    }

    public void setConcordance(Concordance concordance){
        this.concordance = concordance;
    }
        
    public int getCurrentSentence() {
        return currentSentence;
    }

    public void setCurrentSentence(int currentSentence) {
        this.currentSentence = currentSentence;
    }
    
    public int getCurrentSecond() {
        return currentSecond;
    }

    public void setCurrentSecond(int currentSecond) {
        this.currentSecond = currentSecond;
    }
    
    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPause() {
        return currentPause;
    }

    public void setCurrentPause(int currentPause) {
        this.currentPause = currentPause;
    }

    public int findSentence(int position){
        int l = 0;
        int r = sentences.length;
        int j;
        while(r-l>1){
            j = (r+l)/2;
            if (sentences[j] >= position) 
                r = j;
            else  l = j;
        }
        return l;
    }

    public int findPause(int position){
        int l = 0;
        int r = pauses.length;
        int j;
        while(r-l>1){
            j = (r+l)/2;
            if(pauses[j]>=position) r = j;
            else  l = j;
        }
        return l;
    }

    public void setSentenceFromSound(int currentPause){
        int position = getPausePosition(currentPause);
        this.currentSentence = findSentence(position);
    }

    public int setPauseFromText(int currentPause){
        this.currentPause = currentPause;
        return  currentPause;
    }

    public int getPausePosition(int number){
        if(this.pauses.length <= number)
            return  this.pauses[pauses.length-1];
        return this.pauses[number];
    }

    public int getSentencePosition(int number){
        if(this.sentences.length <= number)
            return  this.sentences[sentences.length-1];
        return this.sentences[number];
    }

    public void setPauses(Integer[] arrPauses){
        pauses = arrPauses;
    }
    public Integer[] getSentences() {
        return sentences;
    }

    public void setSentences(Integer[] sentences) {
        this.sentences = sentences;
    }

    public Integer[] getPauses() {
        return pauses;
    }

    public double findPercent(int sentence){
        double position = getSentencePosition(sentence);
        return position/this.sentences[this.sentences.length-1]*100;
    }
    public void setSentencesFromPercent(double percent){
        int position = (int)percent*this.sentences[this.sentences.length-1]/100;
        this.setCurrentSentence(findSentence(position));
    }   

}


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
    protected Integer[] pauses;
    protected Integer[] sentences;
    @XmlElement
    public Concordance concordance;

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

    /**
     * Р СњР В°РЎвЂ¦Р С•Р Т‘Р С‘РЎвЂљ Р Р…Р С•Р С�Р ВµРЎР‚ Р В±Р В»Р С‘Р В¶Р В°Р в„–РЎв‚¬Р ВµР С–Р С• РЎРѓР В»Р ВµР Р†Р В° Р С—РЎР‚Р ВµР Т‘Р В»Р С•Р В¶Р ВµР Р…Р С‘РЎРЏ Р С—Р С• Р С—Р С•Р В»Р С•Р В¶Р ВµР Р…Р С‘РЎР‹ Р С”РЎС“РЎР‚РЎРѓР С•РЎР‚Р В°.
     * @param position
     * @return
     */
    public int findSentence(int position){
        int l = 0;
        int r = sentences.length;
        int j;
        while(r-l>1){
            j = (r+l)/2;
            if(sentences[j]>=position) r = j;
            else  l = j;
        }
        return l;
    }

    /**
     * Р СњР В°РЎвЂ¦Р С•Р Т‘Р С‘РЎвЂљ Р Р…Р С•Р С�Р ВµРЎР‚ Р В±Р В»Р С‘Р В¶Р В°Р в„–РЎв‚¬Р ВµР в„– РЎРѓР В»Р ВµР Р†Р В° Р С—Р В°РЎС“Р В·РЎвЂ№ Р С—Р С• Р С—Р С•Р В»Р С•Р В¶Р ВµР Р…Р С‘РЎР‹ Р С�РЎвЂ№РЎв‚¬Р С‘
     * @param position
     * @return
     */
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

    /**
     * Р С›Р С—РЎР‚Р ВµР Т‘Р ВµР В»РЎРЏР ВµРЎвЂљ Р Р…Р С•Р С�Р ВµРЎР‚ Р С—РЎР‚Р ВµР Т‘Р В»Р С•Р В¶Р ВµР Р…Р С‘РЎРЏ Р С—Р С• Р Р…Р С•Р С�Р ВµРЎР‚РЎС“ Р С—Р В°РЎС“Р В·РЎвЂ№.
     * @param currentPause
     * @return
     */
    public void setSentenceFromSound(int currentPause){
        int position = getPausePosition(currentPause);
        this.currentSentence = findSentence(position);
    }

    /**
     * Р Р€РЎРѓРЎвЂљР В°Р Р…Р В°Р Р†Р В»Р С‘Р Р†Р В°Р ВµРЎвЂљ Р С—Р В°РЎС“Р В·РЎС“.
     * @param currentPause
     * @return
     */
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


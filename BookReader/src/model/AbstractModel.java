package model;

public class AbstractModel {

    protected int currentSentence;
    protected int currentPause;
    protected Integer[] pauses;

    protected Integer[] sentences;

    public int getCurrentSentence() {
        return currentSentence;
    }

    public void setCurrentSentence(int currentSentence) {
        this.currentSentence = currentSentence;
    }

    public int getCurrentPause() {
        return currentPause;
    }

    public void setCurrentPause(int currentPause) {
        this.currentPause = currentPause;
    }

    /**
     * Находит номер ближайшего слева предложения по положению курсора.
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
     * Находит номер ближайшей слева паузы по положению мыши
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
     * Определяет номер предложения по номеру паузы.
     * @param currentPause
     * @return
     */
    public void setSentenceFromSound(int currentPause){
        int position = getPausePosition(currentPause);
        this.currentSentence = findSentence(position);
    }

    /**
     * Устанавливает паузу.
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


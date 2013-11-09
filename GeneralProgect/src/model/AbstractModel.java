package model;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 08.11.13
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractModel {


    protected int currentSentence;
    protected int currentPause;

    protected Integer[] pauses;

    protected int[] sentences;


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
        //поиск
        return 0;
    }

    /**
     * Находит номер ближайшей слева паузы по положению мыши
     * @param position
     * @return
     */
    public int findPause(int position){
        //поиск
        return 0;
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
     * Определяет номер предложения по номеру предложения в другом тексте.
     * @param currentSentence
     * @return
     */
    public void setSentenceFromText(int currentSentence){
        this.currentSentence = currentSentence;
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
        //return this.pauses[number];
        return 0;
    }

    public int getSentencePosition(int number){
       // return this.sentences[number];
        return 0;
    }


    public  void setPauses(Integer[] arrPauses){
        pauses = arrPauses;
    }


}

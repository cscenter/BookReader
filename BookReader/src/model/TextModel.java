package model;

import exception.ReaderException;
import reader.Language;
import translate.Request;

import java.util.ArrayList;

import static translate.Search.DEVIATION;
import static translate.Search.search;

public class TextModel extends AbstractModel{

    private String text;
    private Language language;
    private ArrayList <Point> controlPoints;

    public ArrayList<Point> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(ArrayList<Point> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLanguage(){
        return language;
    }



    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSubstring(){
        int begin, end;
        if(this.getCurrentSentence() >= this.getSentences().length) return null;
        if(this.getCurrentSentence() >= DEVIATION){
            begin = getSentences()[getCurrentSentence() - DEVIATION];
            if(getCurrentSentence() < getSentences().length)
                end =  getSentences()[getCurrentSentence()+1];
            else end =  getSentences()[getCurrentSentence()];
        }else{
            begin = 0;
            if(getSentences().length > DEVIATION + 1)
                end =  getSentences()[DEVIATION + 1];
            else end = getSentences()[getSentences().length -1];
        }
        return getText().substring(begin, end);
    }

    public void setSentenceFromText(TextModel anotherModel){
        this.currentSentence = anotherModel.getCurrentSentence(); //!!!!

        String translate = null;
        try {
            translate = new Request(anotherModel.getLanguage().getName(),
                        this.getLanguage().getName(),
                        anotherModel.getSubstring()).sendGet();
        } catch (ReaderException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        this.currentSentence = search(translate.toLowerCase(), this);
    }
}

package model;

import exception.ReaderException;
import reader.Language;
import translate.Request;
import translate.Search;

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

    public String getSubstring(int position){
        int begin, end;
        if(position > this.getSentences().length) return null;
        if(position >= Search.DEVIATION){
            begin = getSentences()[position - Search.DEVIATION];
            if(position < getSentences().length)
                end =  getSentences()[position+1];
            else end =  getSentences()[position];
        }else{
            begin = 0;
            if(getSentences().length > Search.DEVIATION + 1)
                end =  getSentences()[Search.DEVIATION + 1];
            else end = getSentences()[getSentences().length -1];
        }
        return getText().substring(begin, end);
    }

    public void setSentenceFromText(TextModel anotherModel){
       this.currentSentence = anotherModel.getControlPoint(anotherModel.getCurrentSentence()).getValueSentence()+
                              anotherModel.getCurrentSentence()-
                              anotherModel.getControlPoint(anotherModel.getCurrentSentence()).getKeySentence();
        //this.currentSentence = anotherModel.getCurrentSentence();

        String translate = null;
        try {
            translate = new Request(anotherModel.getLanguage().getName(),
                        this.getLanguage().getName(),
                        anotherModel.getSubstring(anotherModel.getCurrentSentence())).sendGet();
        } catch (ReaderException e) {
        }

        this.currentSentence = search(translate, this);
    }

    public Point getControlPoint(int sentence){
        int l = 0;
        int r = controlPoints.size();
        int j;
        Point currentPoint = new Point(0,0);
        while(r-l>1){
            j = (r+l)/2;
            if(controlPoints.get(j).getKeySentence()>=sentence) r = j;
            else{
                l = j;
                currentPoint = controlPoints.get(j);
            }
        }
        return currentPoint;
    }

}

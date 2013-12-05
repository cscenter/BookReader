package model;

import reader.Language;
import reader.NewTextReader;
import translate.Request;

import java.io.FileInputStream;
import java.io.IOException;

import static translate.Search.search;

public class TextModel extends AbstractModel{
    private String text;
    private Language language;

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

    public void setSentenceFromText(TextModel anotherModel){
        this.currentSentence = anotherModel.getCurrentSentence();
        int begin, end;
        if(anotherModel.getCurrentSentence() >= 5){
            begin = anotherModel.getSentences()[anotherModel.getCurrentSentence() - 5];
            if(anotherModel.getCurrentSentence() < anotherModel.getSentences().length)
                end =  anotherModel.getSentences()[anotherModel.getCurrentSentence()+1];
            else end =  anotherModel.getSentences()[anotherModel.getCurrentSentence()];
        }else{
            begin = 0;
            if(this.getSentences().length > 6)
                end =  anotherModel.getSentences()[6];
            else end = anotherModel.getSentences()[anotherModel.getSentences().length -1];
        }


        String translate = null;
        try {
            translate = new Request(anotherModel.getLanguage().getName(),
                    this.getLanguage().getName(),
                    anotherModel.getText().substring(begin, end)).sendGet();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        this.currentSentence = search(translate.toLowerCase(), this);
    }
}

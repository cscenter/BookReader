package model;

import reader.ReaderException;
import reader.Language;
import translate.Request;
import translate.Search;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import static translate.Search.search;

@XmlAccessorType( XmlAccessType.NONE )
@XmlRootElement
public class TextModel extends AbstractModel{
    @XmlAttribute(name="fileName")
    private String fileName;
    private String text;
    private Language language;
    private int anotherCurrentSentence=0;
    private ArrayList <Point> controlPoints;
    
    public TextModel(){}
    
    public TextModel(String fileName){        
        controlPoints = new ArrayList<Point>();
        controlPoints.add(new Point(0,0));
        this.fileName = fileName;
        setConcordance(new Concordance());
    }
    
    public ArrayList<Point> getControlPoints() {
        return controlPoints;
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
    
    public void setControlPoints(ArrayList<Point> controlPoints) {
        this.controlPoints = controlPoints;
    }
    
    public String getText() {
        return text;
    }
    
    public void setAnotherCurrentSentence(int otherSent){
        anotherCurrentSentence = otherSent;
    }
    
    public int getAnotherCurrentSentence(){
        return anotherCurrentSentence;
    }

    public String getSubstring(int position){
        int begin, end;
        if (position > this.getSentences().length) return null;
        if (position >= Search.DEVIATION){
            begin = getSentences()[position - Search.DEVIATION];
            if (position < getSentences().length)
                end =  getSentences()[position+1];
            else end =  getSentences()[position];
        } else {
            begin = 0;
            if (getSentences().length > Search.DEVIATION + 1)
                end =  getSentences()[Search.DEVIATION + 1];
            else end = getSentences()[getSentences().length -1];
        }
        return getText().substring(begin, end);
    }
    
    public void setSentenceFromText(TextModel anotherModel, boolean useConc){
        if (useConc) {
         //   this.currentSentence = getConcordance().get(anotherModel.getCurrentSentence());
            this.currentSentence = anotherModel.getConcordance().get(anotherModel.getCurrentSentence());
            return;
        }
        this.currentSentence = anotherModel.getControlPoint(anotherModel.getCurrentSentence()).getValueSentence()+
                                anotherModel.getCurrentSentence()-
                                anotherModel.getControlPoint(anotherModel.getCurrentSentence()).getKeySentence();
        String translate = null;
        try {
            translate = new Request(anotherModel.getLanguage().getName(),
                        this.getLanguage().getName(),
                        anotherModel.getSubstring(anotherModel.getCurrentSentence())).sendGet();
        } catch (ReaderException e) {
            e.showError();
            this.currentSentence = getConcordance().get(anotherModel.getCurrentSentence());
        }

        this.currentSentence = search(translate, this);
    }
    
    public void countConcordance(TextModel anotherModel, boolean useConc){
        for (int sent=0; sent+1<getSentences().length; sent++){
            this.setCurrentSentence(sent);
            anotherModel.setSentenceFromText(this, false);
            int sentTranslate =  anotherModel.getCurrentSentence();
            this.getConcordance().set(sent, sentTranslate);
        }
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

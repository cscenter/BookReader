package model;

import java.beans.Transient;
import javax.xml.bind.annotation.*;

@XmlRootElement
public class Model {
    private TextModel rusModel;
    private TextModel engModel;
    private SoundModel audioModel;

    public Model(){}
    
    public Model(SoundModel audioModel, TextModel rusModel, TextModel engModel){
        this.rusModel = rusModel;
        this.engModel = engModel;
        this.audioModel = audioModel;
    }

    public TextModel getRusModel(){
        return rusModel;
    }
    
    public void setRusModel(TextModel rusModel){
        this.rusModel = rusModel;
    }

    public TextModel getEngModel(){
        return engModel;
    }
    
    
    public void setEngModel(TextModel engModel){
        this.engModel = engModel;
    }

    public SoundModel getAudioModel(){
        return audioModel;
    }
    
    public void setAudioModel(SoundModel audioModel){
        this.audioModel = audioModel;
    }
}

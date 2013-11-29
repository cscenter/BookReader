package model;

import reader.English;
import reader.Russian;

public class Model {
    private TextModel rusModel;
    private TextModel engModel;
    private SoundModel audioModel;

    public Model(short[] audio, TextModel rusModel, TextModel engModel){
        this.rusModel = rusModel;
        this.engModel = engModel;
        audioModel = new SoundModel(audio);
    }

    public TextModel getRusModel(){
        return rusModel;
    }

    public TextModel getEngModel(){
        return engModel;
    }

    public SoundModel getAudioModel(){
        return audioModel;
    }
}

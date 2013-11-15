package model;

import reader.English;
import reader.Russian;

public class Model {
    private TextModel rusModel;
    private TextModel engModel;
    private SoundModel audioModel;

    public Model(String[] rusText, String[] engText, short[] audio){
        rusModel = new TextModel(rusText);
        engModel = new TextModel(engText);
        audioModel = new SoundModel(audio);
    }

    public Model(short[] audio, String pathToRusFile, String pathToEngFile){
        rusModel = new TextModel(pathToRusFile, new Russian());
        engModel = new TextModel(pathToEngFile, new English());
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

package model;

public class Model {
    private TextModel rusModel;
    private TextModel engModel;
    private SoundModel audioModel;

    public Model(String[] rusText, String[] engText, short[] audio){
        rusModel = new TextModel(rusText);
        engModel = new TextModel(engText);
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

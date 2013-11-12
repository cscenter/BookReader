package model;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 * Date: 26.10.13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
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

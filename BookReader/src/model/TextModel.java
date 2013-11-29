package model;

import reader.Language;
import reader.NewTextReader;

import java.io.FileInputStream;
import java.io.IOException;

public class TextModel extends AbstractModel{
    public String[] Arr;
    public String text;

    private static String fileRead(String pathToFile) {
        String text = null;
        try {
            FileInputStream in = new FileInputStream(pathToFile);
            byte[] dataString = new byte[in.available()];
            in.read(dataString);
            text = new String(dataString);
            in.close();
        } catch (IOException e) {

        }
        return text;
    }

    public TextModel(String[] text){
        Arr = text;
    }

    public TextModel(String pathToFile, Language language){
        text = fileRead(pathToFile);
        NewTextReader.tokenizer(text, language);
        pauses = NewTextReader.getPauses();
        sentences = NewTextReader.getSentences();
    }

    public String getText(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Arr.length; i++ ){
            sb.append(i+" ");
            sb.append(Arr[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}

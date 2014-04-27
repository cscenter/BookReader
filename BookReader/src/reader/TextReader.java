package reader;
import java.io.FileInputStream;
import java.io.IOException;
import model.Point;
import model.TextModel;
import translate.Request;
import translate.Search;

import java.util.ArrayList;

import static translate.Search.search;

public class TextReader {
    private static final char[] endSymbols = {'.','!','?'};
    private static final char[] pauseSymbols = {',',';',':'};
    private StringBuilder lastWord = new StringBuilder();
    public static int DIST = 300;
    private TextModel textModel;

    public  TextModel getModel(){
        return textModel;
    }
    
    public TextModel read(String pathToFile, Language language)throws ReaderException{
        ArrayList<Integer> listSentences = new ArrayList<Integer>();
        ArrayList<Integer> listPauses = new ArrayList<Integer>();
        listPauses.add(0);
        listSentences.add(0);
        int start = 0;
        StringBuilder sb = new StringBuilder();
        textModel = new TextModel(pathToFile);
        textModel.setText(fileRead(pathToFile));
        
        for (int i = 0; i <  textModel.getText().length();i++){
            if (!Character.isLetter(textModel.getText().charAt(i))){
                if (start != i-1 && sb.length()!= 0){
                    lastWord.setLength(0);
                    lastWord.append(sb.substring(start));
                }
                start = i+1;
                if (checkEndSymbol(textModel.getText().charAt(i)))
                    if (checkEnd(textModel.getText(), i, lastWord.toString(), language)){
                        listSentences.add(i);
                        listPauses.add(i);
                    }
            }
            sb.append(textModel.getText().charAt(i));
        }
        textModel.setPauses(listPauses.toArray(new Integer[listPauses.size()]));
        textModel.setSentences(listSentences.toArray(new Integer[listSentences.size()]));
        textModel.setLanguage(language);
        return textModel;
    }
    
    public static String fileRead(String pathToFile) throws ReaderException {
        String text = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(pathToFile);
            byte[] dataString = new byte[in.available()];
            in.read(dataString);
            text = new String(dataString);
        } catch (IOException e) {
            throw new ReaderException(e);
        } finally {
            try {
                if(in != null) in.close();
            } catch (IOException e) { }
        }
        return text;
    }

    public void setControlPoints(TextModel anotherModel) throws ReaderException {
        ArrayList<Point> controlPoints = new ArrayList<Point>();
        int anotherIndex = 0;
        for (int i = 0;
            i < textModel.getSentences().length &&
            anotherIndex < anotherModel.getSentences().length;
            i = i + DIST){

            String buf = textModel.getSubstring(i);
            String translate = new Request(textModel.getLanguage().getName(),
                    anotherModel.getLanguage().getName(),
                    buf).sendGet();

            anotherModel.setCurrentSentence(anotherIndex);
            anotherIndex = search(translate.toLowerCase(), anotherModel);

            controlPoints.add(new Point(i, anotherIndex));
            anotherIndex += DIST;
        }
        textModel.setControlPoints(controlPoints);
    }


    private static boolean checkEndSymbol(char ch){
        for (char endSymbol : endSymbols) {
            if (ch == endSymbol) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkEnd(String text, int i, String lastWord, Language language)   {
            for (i++; i < text.length();i++){
                if (text.charAt(i) == ' ')
                    continue;
                if (text.charAt(i) == '.')
                    return false;
                if (text.charAt(i) == '\n')
                    return true;
                if (Character.isUpperCase(text.charAt(i))){
                    if(lastWord.length()==1 && Character.isUpperCase(lastWord.charAt(0)))
                        return false;
                    if (language.isName(lastWord))
                        return false;
                    return true;
            }
        }
        return true;
    }
}

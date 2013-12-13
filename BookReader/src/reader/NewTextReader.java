package reader;
import exception.ReaderException;
import model.Point;
import model.TextModel;
import translate.Request;
import translate.Search;

import java.util.ArrayList;

import static translate.Search.search;

public class NewTextReader {

    private static final char[] endSymbols = {'.','!','?'};
    private static final char[] pauseSymbols = {',',';',':'};
    private StringBuilder lastWord = new StringBuilder();

    public static int DIST = 100;

    private TextModel textModel = new TextModel();

    public  TextModel getModel(){
        return textModel;
    }



    public void tokenizer(String pathToFile, Language language)throws ReaderException{
        ArrayList<Integer> listSentences = new ArrayList<Integer>();
        ArrayList<Integer> listPauses = new ArrayList<Integer>();
        listPauses.add(0);
        listSentences.add(0);
        int start = 0;
        StringBuilder sb = new StringBuilder();
        textModel.setText(FileReader.fileRead(pathToFile));

        for(int i = 0; i <  textModel.getText().length();i++){
            if (!Character.isLetter(textModel.getText().charAt(i))){
                if (start != i-1 && sb.length()!= 0){
                    lastWord.setLength(0);
                    lastWord.append(sb.substring(start));
                }
                start = i+1;

                if(сheckEndSymbol(textModel.getText().charAt(i)))
                    if(сheckEnd(textModel.getText(), i, lastWord.toString(), language)){
                        listSentences.add(i);
                        listPauses.add(i);
                    }
                if(сheckPauseSymbol(textModel.getText().charAt(i)))
                    if(language.сheckPause(textModel.getText(), lastWord.toString(), i)){
                        listPauses.add(i);
                    }
            }
            sb.append(textModel.getText().charAt(i));

        }
        textModel.setPauses(listPauses.toArray(new Integer[listPauses.size()]));
        textModel.setSentences(listSentences.toArray(new Integer[listSentences.size()]));
        textModel.setLanguage(language);
    }

    public void setControlPoints(TextModel anotherModel) throws ReaderException {
        ArrayList<Point> controlPoints = new ArrayList<Point>();

        int anotherIndex = 0;
        for(int i = 0; i < textModel.getSentences().length; i = i + DIST){
            Point currentPoint = new Point();
            textModel.setCurrentSentence(i);
            anotherModel.setCurrentSentence(anotherIndex);
            String buf = textModel.getSubstring();
            if(buf == null) break;
            String translate = new Request(textModel.getLanguage().getName(),
                    anotherModel.getLanguage().getName(),
                    buf).sendGet();

            anotherIndex = search(translate.toLowerCase(), anotherModel);

            currentPoint.setKeySentence(i);
            currentPoint.setValueSentence(anotherIndex);
            controlPoints.add(currentPoint);
            anotherIndex += DIST;
        }
        textModel.setControlPoints(controlPoints);
    }


    private static boolean сheckEndSymbol(char ch){
        for (char endSymbol : endSymbols) {
            if (ch == endSymbol) {
                return true;
            }
        }
        return false;
    }

    private static boolean сheckPauseSymbol(char ch){
        for (char pauseSymbol : pauseSymbols) {
            if (ch == pauseSymbol) {
                return true;
            }
        }
        return false;
    }

    private static boolean сheckEnd(String text, int i, String lastWord, Language language)   {

            for (i++; i < text.length();i++){
                if(text.charAt(i) == ' ')  continue;
                if(text.charAt(i) == '.')  return false;
                if(text.charAt(i) == '\n')  return true;
                if (Character.isUpperCase(text.charAt(i))){
                    if(lastWord.length()==1 && Character.isUpperCase(lastWord.charAt(0)))
                        return false;
                    if(language.isName(lastWord)) return false;
                    else return true;
            }
        }
        return true;
    }

}

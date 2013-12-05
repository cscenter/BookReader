package reader;
import exception.ReaderException;
import model.TextModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTextReader {

    private static final char[] endSymbols = {'.','!','?'};
    private static final char[] pauseSymbols = {',',';',':'};
    private static StringBuilder lastWord = new StringBuilder();

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

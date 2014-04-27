package translate;

import model.TextModel;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Р вЂєР С‘Р В·Р В°
 * Date: 30.11.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class Search {
    public static final int DEVIATION = 5;
    public static final int RANGE = 50;

    public static int search (String translate, TextModel originalModel){
        int sentence = originalModel.getCurrentSentence();
        int maxPercent = 0;
        int maxNumber = sentence;
        int currentPercent;
        if (sentence < DEVIATION) return sentence;
        for (int i = sentence - RANGE; i < sentence + RANGE; i++){
            if(i < DEVIATION) i = DEVIATION;
            int begin = originalModel.getSentencePosition(i - DEVIATION);
            int end = originalModel.getSentencePosition(i + 1);
            String original = originalModel.getText().substring(begin, end);
            currentPercent = compare(translate, original);
            if(maxPercent <= currentPercent){
                maxPercent = currentPercent;
                maxNumber = i;
            }
        }
        return maxNumber;
    }

    public static int compare(String translate, String original){
        HashMap<String, Integer> translateMap = stringParser(translate);

        String lastWord;
        int start = 0;
        int value;
        int matches = 0;
        double count = 0;
        for (int i = 0; i <  original.length();i++){
            if (!Character.isLetter(original.charAt(i))){
                if (start < i-1){
                    lastWord = original.substring(start,i);
                    count++;
                    if(translateMap.containsKey(lastWord)){
                        matches++;
                        value = translateMap.get(lastWord);
                        if (value == 1)  translateMap.remove(lastWord);
                        else translateMap.put(lastWord,value - 1);
                    }
                }
                start = i+1;
            }
        }
        return matches;//(int) (matches/count*100);
    }

    public static HashMap<String, Integer> stringParser(String text){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String lastWord;
        int start = 0;
        Integer value;
        for (int i = 0; i <  text.length();i++){
            if (!Character.isLetter(text.charAt(i))){
                if (start < i-1){
                    lastWord = text.substring(start,i);
                    value = map.get(lastWord);
                    if(value != null) map.put(lastWord, value+1);
                    else map.put(lastWord, 1);
                }
                start = i+1;
            }
        }
        return map;
    }

}

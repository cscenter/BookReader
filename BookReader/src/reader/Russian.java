package reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 15.11.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Russian extends Language {

    public Russian(){
        setName("ru");
    }
    private static String[] beforeName = {"г","ул","пр","ш"};
    @Override
    public boolean isName(String word) {
        for (String before : beforeName) {
            if (word.equals(before)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean сheckPause(String text, String lastWord, int i){
        Pattern p = Pattern.compile("[,|:|;|!|.|\\?]\\s*[а-я]*[А-Я]*\\s*[,|:|;|!|.|\\?]");
        Matcher m = p.matcher(text.substring(i-20, i+1));
        if(m.find())
            if(m.group(m.groupCount()).contains(lastWord)) return false;

        return true;
    }
}

package reader;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 15.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
    public abstract class Language {

    static private String[] beforeName;

    public abstract boolean сheckPause(String text, String lastWord, int i);

    public abstract boolean isName(String word);
}

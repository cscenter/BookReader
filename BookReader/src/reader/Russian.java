package reader;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 15.11.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Russian extends Language {

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
}

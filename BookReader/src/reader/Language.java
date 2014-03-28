package reader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 15.11.13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType( XmlAccessType.PROPERTY )
@XmlRootElement
    public abstract class Language {

    static private String[] beforeName;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
    }

    public abstract boolean сheckPause(String text, String lastWord, int i);

    public abstract boolean isName(String word);
}

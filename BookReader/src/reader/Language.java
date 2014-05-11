package reader;

public abstract class Language {

    static private String[] beforeName;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
       this.name = name;
    }

    public abstract boolean isName(String word);
}

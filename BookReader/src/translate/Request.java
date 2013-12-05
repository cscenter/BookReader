package translate;

import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;


/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 29.11.13
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class Request {

    private static final Integer SUCCESS = 200;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String KEY  = "trnsl.1.1.20131128T200212Z.bda7682286c9d53f.6ed7d502ace051fef1ce0a62b6c0d8b4ae8c0079";
    private String languageFrom;

    private String languageTo;

    private String text;
    private Integer[] textNum;

    Request(){}
    public Request(String languageFrom, String languageTo, String text){
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
        this.text = text;
    }

    public String getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String sendGet() throws Exception {

        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=" +  KEY +
                "&text=" + URLEncoder.encode(this.text,"UTF-8") +
                "&lang=" + this.languageFrom +"-" + this.languageTo +
                "&format=plain";

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        if(responseCode != SUCCESS) throw new Exception("Response Code : " + responseCode);

        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine = in.readLine();

        JSONParser parser = new JSONParser();
        Object ob = parser.parse(inputLine);
        JSONObject jsonObj = (JSONObject) ob;

        in.close();

        return jsonObj.get("text").toString();
    }

}

package model;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 26.10.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class TextModel {
    public String[] Arr;

    public TextModel(String[] text){
        Arr = text;
    }

    public String getText(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Arr.length; i++ ){
            sb.append(i+" ");
            sb.append(Arr[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}

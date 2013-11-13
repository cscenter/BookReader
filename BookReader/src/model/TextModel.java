package model;

public class TextModel extends AbstractModel{
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

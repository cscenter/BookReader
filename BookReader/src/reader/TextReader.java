package reader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TextReader {

    private static String[] beforeName = {"Mr", "Mrs", "St","г","ул","пр","ш"};
    private static char[] end = {'.','!','?'};
    private static StringBuilder lastWord = new StringBuilder();
    private static String fileRead(String pathToFile) {
        String text = null;
        try {
            FileInputStream in = new FileInputStream(pathToFile);
            byte[] dataString = new byte[in.available()];
            in.read(dataString);
            text = new String(dataString);
            in.close();
        } catch (IOException e) {

        }
        return text;
    }

    public static  String[] Tokenizer (String pathToFile) {
        String text = fileRead(pathToFile);
        if(text == null) return null;
        ArrayList<String> list = new ArrayList<String>();
        int start = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < text.length();i++){
          /*  if (!(  (text.charAt(i) >= 'a'&& text.charAt(i) <= 'z')||
                    (text.charAt(i) >= 'а'&& text.charAt(i) <= 'я')||
                    (text.charAt(i) >= 'A'&& text.charAt(i) <= 'Z')||
                    (text.charAt(i) >= 'А'&& text.charAt(i) <= 'Я'))) {
                if (start != i-1 && sb.length()!= 0){
                    lastWord.setLength(0);
                    lastWord.append(sb.substring(start));
                }
                start = i+1;
            }      */
            if (text.charAt(i)== '\n'){
                continue;
            }
            sb.append(text.charAt(i));

            for(int j = 0; j < end.length; j++){
                if(text.charAt(i)== end[j]) {
                    if(Check(text,i)){
                        list.add(sb.toString());
                        sb.setLength(0);
                    }
                }
            }
        }
        return list.toArray(new String[list.size()]) ;
    }
    private static boolean Check (String text,int i)   {
        if (text.charAt(i) == '.') {
            int flag  = 0;
            for (int j= i-1; j > 0;j--) {
                if(text.charAt(j) >= '0'&&text.charAt(j) <= '9' ) {
                    flag = 1;
                    continue;
                }
                if( flag == 1)
                    if (text.charAt(j) == '\n') return false;
                    else if((text.charAt(j) != ' ')) break;

                if(     (text.charAt(j) >= 'A'&& text.charAt(j) <= 'Z')||
                        //(text.charAt(j) >= 'А'&& text.charAt(j) <= 'Я')) {
                        (text.charAt(j) >= 'A'&& text.charAt(j) <= 'Z')) {
                    if ((text.charAt(--j) >= 'A'&& text.charAt(j) <= 'Z')||
                            //(text.charAt(j) >= 'А'&& text.charAt(j) <= 'Я'))
                        (text.charAt(j) >= 'A'&& text.charAt(j) <= 'Z'))
                        return true;
                    else return false;
                }
                if ((flag != 2)&&(text.charAt(i) != ' '))break;
            }
            for (i++; i < text.length();i++){
                if(text.charAt(i) == ' ')  continue;
                if(text.charAt(i) == '.')  return false;
                if(text.charAt(i) == '\n')  return true;
                if(text.charAt(i) >= '0'&&text.charAt(i) <= '9' ) return false;
                if(     (text.charAt(i) >= 'a'&& text.charAt(i) <= 'z')||
                        (text.charAt(i) >= '\u1072'&& text.charAt(i) <= '\u1103')) return false;
//                        (text.charAt(i) >= 'a'&& text.charAt(i) <= 'z')) return false;
                if ((text.charAt(i) >= 'A'&& text.charAt(i) <= 'Z')||
                        (text.charAt(i) >= '\u1040'&& text.charAt(i) <= '\u1071'))
//                        (text.charAt(i) >= 'A'&& text.charAt(i) <= 'Z'))
                    if(notName()) return true;
                else return false;
            }
        }
        return true;
    }

    private static boolean notName() {
      //  for (int j = 0; j < beforeName.length;j++)
       //     if (lastWord.equals(beforeName[j])) return false;
        return true;
    }

}

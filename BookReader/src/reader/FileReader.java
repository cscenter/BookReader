package reader;

import exception.ReaderException;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 30.11.13
 * Time: 0:36
 * To change this template use File | Settings | File Templates.
 */
public class FileReader {
    public static String fileRead(String pathToFile) throws ReaderException {
        String text = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(pathToFile);
            byte[] dataString = new byte[in.available()];
            in.read(dataString);
            text = new String(dataString);
        } catch (IOException e) {
            throw new ReaderException(e);
        }finally {
            try {
                if(in != null) in.close();
            } catch (IOException e) { }
        }
        return text;
    }
}

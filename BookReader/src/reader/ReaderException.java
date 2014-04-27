package reader;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Oskina Olga
 * SPBGPU
 * 2013
 */
public class ReaderException extends Exception {

    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderException(Throwable cause) {
        super(cause);
    }
    
    public void showError() {
        JOptionPane optionPane = new JOptionPane(getMessage(), JOptionPane.ERROR_MESSAGE);    
        JDialog dialog = optionPane.createDialog("Failure");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

}

package viewer;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Р В РІР‚С”Р В РЎвЂ�Р В Р’В·Р В Р’В°
 * Date: 26.10.13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class TextViewer  extends AbstractViewer {
    private JTextArea text;
    private JScrollPane scroll;
    private Object marker;

    public TextViewer(String text, Viewer viewer){
        super(viewer);
        marker = null;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.text = new JTextArea();
        scroll = new JScrollPane(this.text,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        scroll.setPreferredSize(new Dimension(300, 250));

        this.add(scroll);
        this.text.setText(text);

        this.text.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                JTextArea editArea = (JTextArea) e.getSource();
                position = editArea.getCaretPosition();
                parent.update(TextViewer.this);
            }
        });
    }

    @Override
    public void update(int position) {
        try {
            int line =  text.getLineOfOffset(position);
            System.out.println("position: "+ position+ " caretpos: "+ text.getCaretPosition() +" line num: "+line);
 //           scroll.getVerticalScrollBar().setValue(line);
            if (marker == null)
                marker = text.getHighlighter().addHighlight(position, position+10,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
            else {
                text.getHighlighter().changeHighlight(marker,position, position+10);  
            }
            System.out.println(text.getVisibleRect());
            int y = line*50000/text.getLineCount();
            int line_ = position/30;
            int y1 = line_*scroll.getHeight()/16;
            int numSignsInLine = 40;
            int heightLine = scroll.getHeight()/16;
            int y2 = position/numSignsInLine * heightLine;
            int y3 = line * text.getRows()*16/text.getLineCount();
            text.scrollRectToVisible(new Rectangle(0,(y+y1+y2)/3, 1, 10));
            System.out.println(text.getVisibleRect());
        }catch (BadLocationException exc){};
    }
}

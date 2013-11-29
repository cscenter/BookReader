package viewer;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
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
            //this.add(Box.createRigidArea(new Dimension(5 ,0)));
            this.text.setText(text);

            this.text.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    JTextArea editArea = (JTextArea) e.getSource();
                    position = editArea.getCaretPosition();
                    parent.update(TextViewer.this);
                }
            });

          /*  count =0;
            ob = null;

            ruText.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    JTextArea editArea = (JTextArea)e.getSource();

                    int linenum = 1;
                    int columnnum = 1;


                    try {
                        int caretpos = editArea.getCaretPosition();
                        linenum = editArea.getLineOfOffset(caretpos);

                        columnnum = caretpos - editArea.getLineStartOffset(linenum);


                        linenum += 1;
                    }
                    catch(exception ex) { }

                    try{
                        engText.setCaretPosition(engText.getLineStartOffset(linenum-1));
                        Highlighter.HighlightPainter painter;
                        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

                        if (count>0)
                            engText.getHighlighter().changeHighlight(ob,engText.getLineStartOffset(linenum-1),
                                    engText.getLineEndOffset(linenum-1));

                        else
                            ob = engText.getHighlighter().addHighlight(engText.getLineStartOffset(linenum-1),
                                    engText.getLineEndOffset(linenum-1),painter );
                        count++;
                    }catch (BadLocationException exc) {};

                    System.out.println(linenum);
                }
            });        */

        }

    @Override
    public void update(int position) {
        try{
            int line =  text.getLineOfOffset(position);
            System.out.println("position: "+ position+ " caretpos: "+ text.getCaretPosition() +" line num: "+line);
            //scroll.getVerticalScrollBar().setValue(line);
            if (marker == null)
                marker = text.getHighlighter().addHighlight(position, position+10,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
            else
                text.getHighlighter().changeHighlight(marker,position, position+10);
        }catch (BadLocationException exc){};

    }
}

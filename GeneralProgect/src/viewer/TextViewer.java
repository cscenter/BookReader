package viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Лиза
 * Date: 26.10.13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class TextViewer  extends JPanel {
        JTextArea Text;
        JScrollPane Scroll;
        int count ;
        Object ob;

        public TextViewer(String text){
            super(null);
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            Text = new JTextArea();
            Scroll = new JScrollPane(Text,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            //Text.setLineWrap(true);
            //Text.setWrapStyleWord(true);
            Scroll.setPreferredSize(new Dimension(200, 250));

            this.add(Scroll);
            this.add(Box.createRigidArea(new Dimension(5 ,0)));
            Text.setText(text);

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
                    catch(Exception ex) { }

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



}

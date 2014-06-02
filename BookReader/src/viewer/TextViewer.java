package viewer;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;


public class TextViewer  extends AbstractViewer {
    private JTextArea text;
    private JScrollPane scroll;
    private JButton addConcButton;
    private JButton findConcButton;
    private JButton translateButton;
    private Object marker;
    private TextModel textModel;    
    private JTextField tfSentOwn;
    private JTextField tfSentConc;
    private int sentenseConc=0;    
    private boolean scrollUpdate=false;

    public TextViewer(TextModel textModel, Viewer viewer){
        super(viewer);
        this.textModel = textModel;
        marker = null;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.text = new JTextArea();
        scroll = new JScrollPane(this.text,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);
        scroll.setPreferredSize(new Dimension(300, 250));

        this.add(scroll);
        this.text.setText(textModel.getText());
        JPanel panelConcordances = new JPanel();
        panelConcordances.setBackground(Colors.concColor);
        initPanelConcordances(panelConcordances);
        this.add(panelConcordances);

        this.text.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (scrollUpdate)
                    return;
                JTextArea editArea = (JTextArea) e.getSource();
                position = editArea.getCaretPosition();
                parent.update(TextViewer.this);
            }
        });
    }
    
    private void initPanelConcordances(JPanel panelConcordances){
        addConcButton = new JButton(new ImageIcon("resource/concTxtTxt.gif"));
        findConcButton = new JButton(new ImageIcon("resource/Find16.gif"));
        translateButton = new JButton(new ImageIcon("resource/Search16.gif"));     
        
        findConcButton.setMargin(new Insets(0, 0, 0, 0));
        translateButton.setMargin(new Insets(0, 0, 0, 0)); 
        Dimension sizeQuButton = new Dimension(18,18);        
        findConcButton.setSize(sizeQuButton);
        translateButton.setSize(sizeQuButton);
                
        addConcButton.addActionListener(new TextViewer.addConcActionListener());
        findConcButton.addActionListener(new TextViewer.findConcActionListener());
        translateButton.addActionListener(new TextViewer.translateActionListener());
        
        tfSentOwn = new JTextField("0");
        tfSentOwn.setPreferredSize(new Dimension(50, 20));
        tfSentConc = new JTextField("0");
        tfSentConc.setPreferredSize(new Dimension(50, 20));
        panelConcordances.add(findConcButton);
        panelConcordances.add(translateButton);
        panelConcordances.add(tfSentOwn);
        panelConcordances.add(addConcButton);
        panelConcordances.add(tfSentConc);
        
    }
    
    public class translateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            int sentenseOwn = new Integer(tfSentOwn.getText());
            System.out.println("&? " + sentenseOwn + ":" + sentenseConc + " " + textModel.getLanguage().getName());
            position = textModel.getSentencePosition(sentenseOwn) + 5; 
            parent.getModel().setUseConc(Boolean.FALSE);
            parent.update(TextViewer.this);
            parent.getModel().setUseConc(Boolean.TRUE);
        }
    }
    
    public class findConcActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int sentenseOwn = new Integer(tfSentOwn.getText());
            sentenseConc = textModel.getConcordance().get(sentenseOwn);
            tfSentConc.setText("" + sentenseConc);
            System.out.println("? " + sentenseOwn + ":" + sentenseConc + " " + textModel.getLanguage().getName());
            position = textModel.getSentencePosition(sentenseOwn) + 5; 
            parent.update(TextViewer.this);
        }
    }
        
    public class addConcActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int sentenseOwn = new Integer(tfSentOwn.getText());
            sentenseConc = new Integer(tfSentConc.getText());
            textModel.getConcordance().set(sentenseOwn, sentenseConc);
            System.out.println("Add " + sentenseOwn + ":" + sentenseConc + " " + textModel.getLanguage().getName());
        }
    }
    
    public void setSentenseConc(int sentenseConc){
        this.sentenseConc = sentenseConc;
    }

    @Override
    public void update(int position) {
        try {
            int line =  text.getLineOfOffset(position);
            System.out.println("position: "+ position+ " caretpos: "+ text.getCaretPosition() +" line num: "+line);
            int sent = textModel.getCurrentSentence();
            int positionShift = textModel.getSentences()[sent+1];
            System.out.println("positionShift: "+positionShift);
            if (position > 0)
                position += 2;
//            marker = text.getHighlighter().addHighlight(position, positionShift,
//                        new DefaultHighlighter.DefaultHighlightPainter(Colors.getSentColor(sent)));
            if (marker == null)
                marker = text.getHighlighter().addHighlight(position, positionShift,
                        new DefaultHighlighter.DefaultHighlightPainter(Colors.markerColorLight));
            else {
                text.getHighlighter().changeHighlight(marker,position, positionShift);  
            }            
            scrollUpdate = true;
         //   text.setCaretPosition(Math.min(positionShift+20, text.getTabSize()-1));  
            text.setCaretPosition(positionShift+20);
            scrollUpdate = false;
            tfSentConc.setText("" + textModel.getAnotherCurrentSentence()); 
            tfSentOwn.setText("" + textModel.getCurrentSentence()); 
       } catch (BadLocationException exc){};
    }
}

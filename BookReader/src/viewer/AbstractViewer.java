package viewer;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;

public abstract class AbstractViewer extends JPanel {
    protected int position;
    protected Viewer parent;

    public AbstractViewer(Viewer parent){
        super(null);
        this.parent = parent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
     
    public Viewer getViewer(){
        return parent;
    }
    
    public abstract void update(int position);
    
}

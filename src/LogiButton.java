import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class LogiButton extends JButton implements ActionListener, PuzzleButton {

    private Logi l;
    private int row;
    private int col;


    public LogiButton (Logi l, int row, int col) {
        this.l = l;
        this.row = row;
        this.col = col;
        this.addActionListener(this);
    }

    @Override
    public void setEmpty() {
        this.setText("");
    }

    @Override
    public void setCorrect() {
        if (l.getPos(row,col).getValue() == 0)
            this.setText("");
        else
            this.setText(Integer.toString(l.getPos(row, col).getValue()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Hashtable<Integer,String> dict = l.readColorToDict("logicolors.txt");
        l.setPos(row, col);
        Spot c = l.getPos(row, col);
        if (c.getValue() > 5) { //shade button
            this.setText("");
        } else if (c.getValue() > 0) {
            this.setText(dict.get(c.getValue()));
            c.setValue(c.getValue() + 1);
        } else {
            this.setText("");
        }

    }
}

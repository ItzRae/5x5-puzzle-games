import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HitoriButton extends JButton implements ActionListener, PuzzleButton {

    private Hitori hit;
    private int row;
    private int col;

    public HitoriButton(Hitori hit, int row, int col) {
        this.hit = hit;
        this.row = row;
        this.col = col;

        this.addActionListener(this);
    }

    public void setCorrect() {
        this.setBackground(Color.lightGray);
        this.setOpaque(true);
        this.repaint();
    }

    public void setEmpty() {
        this.setBackground(Color.white);
        this.setOpaque(true);
        this.repaint();
    }

    // shaded button
    @Override
    public void actionPerformed(ActionEvent e) {
        hit.setPos(row, col);
        if (hit.getPos(row, col).getValue() == 1) { //shade button
            this.setBackground(Color.lightGray);
        } else {
            this.setBackground(Color.white);
        }
    }
}

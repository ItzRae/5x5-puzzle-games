import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KakurasuButton extends JButton implements ActionListener, PuzzleButton {

    private Kakurasu k;
    private int row;
    private int col;
    
    public KakurasuButton(Kakurasu k, int row, int col) {
        this.k = k;
        this.row = row;
        this.col = col;
        this.addActionListener(this);
    }

    @Override
    public void setCorrect() {
        this.setBackground(Color.gray);
        this.setOpaque(true);
        this.repaint();
    }

    @Override
    public void setEmpty() {
        this.setBackground(Color.white);
        this.setOpaque(true);
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            k.setPos(row, col);
            if (k.getPos(row, col).getValue() == 1) { //shade button
                this.setBackground(Color.gray);
            } else {
                this.setBackground(Color.white);
            }
        }
    }


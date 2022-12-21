import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reset extends JButton implements ActionListener {
    private Puzzle game;

    public Reset(Puzzle game) {
        this.game = game;
        addActionListener(this);
        setText("Reset");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.clearGrid();
    }
}

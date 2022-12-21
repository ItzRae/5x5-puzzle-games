import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Check extends JButton implements ActionListener {
    private Puzzle g;

    public Check(Puzzle g) {
        this.g = g;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        g.solved();
    }

}

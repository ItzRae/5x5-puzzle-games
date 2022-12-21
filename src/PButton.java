import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class PButton extends JButton implements ActionListener {
        private String title;
        private MainFrame frame;
        private int gameNum;

    public PButton(String text) {
        this.title = text;

        setVisible(true);
        setOpaque(true);

        setContentAreaFilled(false);
//        setBackground(new Color(125, 108, 161));
        setFont(new Font("Georgia",Font.TRUETYPE_FONT, 16));
        setPreferredSize(new Dimension(200, 80));
        setText(title);
    }

    public PButton(MainFrame fr, int g) {
        this.frame = fr;
        this.gameNum = g;
        setText(title);
//        setBounds(20, 20, 250, 100);

    }

    public String getText() {
        return title;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameNum == 0) {
            frame.getContentPane().removeAll();
            frame.createFrame();
            frame.setVisible(true);

            frame.validate();
            frame.repaint();
//            System.out.println("yes");
        }


    }
}

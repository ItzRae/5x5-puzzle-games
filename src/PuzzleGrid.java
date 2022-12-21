import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

//check and init methods


public class PuzzleGrid extends JFrame{

    private JButton[] buttons;
    private int buttonCount = 25;

    public PuzzleGrid() {

    }

    public void init() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 800));

        Container ct = this.getContentPane();
        
        JLayeredPane lpane = new JLayeredPane();
        
//        ct.setLayout(new FlowLayout());
        JPanel grid = new JPanel();
        grid.setBounds(140, 55, 500, 500);
        grid.setLayout(new GridLayout(5, 5));
        grid.setBackground(Color.gray);

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        bPanel.setBounds(0,560, 800, 200);

        // reset button
        JButton reset = new JButton("Reset");
        bPanel.add(reset, BorderLayout.CENTER);

        //J

        buttons = new JButton[buttonCount];
        for (int i = 0; i < buttonCount; i++) {
            JButton jb = new JButton();
            grid.add(jb);

            buttons[i] = jb;

        }

//        ct.add(reset);
        lpane.add(grid, Integer.valueOf(0), 0);
        lpane.add(bPanel, Integer.valueOf(1), 0);
        ct.add(lpane);
    }


}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//background stuff https://tips4java.wordpress.com/2008/10/12/background-panel/

public class MainFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    PButton button1, button2, button3;
    JButton start;
    JLabel title;
    JTextArea desc;
    JLabel imgcont;
    JPanel footer;
    String p1 = "Hitori";
    String p2 = "Logi-5";
    String p3 = "Kakurasu";
    String p = "";


    public void createFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(800, 500));


        //initialize panes/panels


        JLayeredPane lpane = new JLayeredPane();
        JPanel mainPanel = new JPanel();
        JPanel display = new JPanel();
        footer = new JPanel();

        button1 = new PButton(p1);
        button1.addActionListener(this);

        // button for puzzle 2
        button2 = new PButton(p2);
        button2.addActionListener(this);

        //button for puzzle 3
        button3 = new PButton(p3);
        button3.addActionListener(this);

        start = new JButton("Start Game");
        start.addActionListener(this);
        start.setEnabled(false);
        lpane.setBounds(0, 0, 800, 400);

        // main panel
//        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setPreferredSize(new Dimension(800, 500));
        mainPanel.setBackground(new Color(227, 211, 228)); //bublegum pink
        mainPanel.setBounds(0, 0, 800, 500);
        mainPanel.setOpaque(true);

        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);

        // display panel
        title = new JLabel("Welcome to Rachel's Final Project!");
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setBounds(80, 120, 200, 100);

        imgcont = new JLabel();
        imgcont.setBounds(130, 250, 540, 130);
        imgcont.setBackground(Color.red);
        ImageIcon img = new ImageIcon(new ImageIcon("puzzleanimation.gif").getImage().getScaledInstance(140, 110, Image.SCALE_DEFAULT));
        imgcont.setIcon(img);

        desc = new JTextArea("You will be solving some puzzles! Your goal is to solve and complete a puzzle of your choosing." +
                " Please click each puzzle to read their descriptions, pick one you want to play, then select Start Game to get started.\n");
        desc.setFont(new Font("Georgia", Font.PLAIN, 14));
        desc.setBounds(130, 140, 580, 200);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);



        display.setBackground(Color.WHITE);
        display.setBounds(80, 120, 600, 250);
        display.setOpaque(true);



        // footer panel
        footer.setBounds(0,400, 800, 100);
        footer.add(start);
        footer.setOpaque(false);

        display.add(title);
        display.add(desc);
        display.add(imgcont);

        // add buttons to main panel


        lpane.add(mainPanel, Integer.valueOf(0), 0);
        lpane.add(display, Integer.valueOf(1), 0);
        lpane.add(footer, Integer.valueOf(2), 0);

        container.add(lpane, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == button1 || source == button2 || source == button3) start.setEnabled(true);

        if (source == button1) {
            p = button1.getText();
            title.setText(button1.getText() + " Rules");
            desc.setText("• Shade squares so no unshaded digit or letter is repeated in a row or column.\n" +
                    "• Shaded square must not touch any other shaded square horizontally or vertically.\n" +
                    "• ! You must be able to 'travel' from any unshaded square to any other unshaded square (in any direction).");
            ImageIcon hitori = new ImageIcon(new ImageIcon("hitori.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            imgcont.setIcon(hitori);
        } else if (source == button2) {
            p = button2.getText();
            title.setText(button2.getText() + " Rules");
            desc.setText("• Each row and column must have each color, appearing only once.\n" +
                    "• Each color must also appear once within each shape.\n");
            ImageIcon logi = new ImageIcon(new ImageIcon("logi5.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            imgcont.setIcon(logi);
        } else if (source == button3) {
            p = button3.getText();
            title.setText(button3.getText() + " Rules");
            desc.setText("• Squares are marked or unmarked. \n" +
                    "• The clues on the right and on the bottom and are the totals for the respective rows and cols.\n" +
                    "•The numbers on the top and on the left are the values for each of the squares.\n" +
                    "• Marking a square will add that square's value to both the row's total and the column's total. ");
            ImageIcon k = new ImageIcon(new ImageIcon("kakurasu.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            imgcont.setIcon(k);
        }

        // when start is pressed
        if (source == start) {

            if (p == p1){
                Puzzle hitori = new Hitori(this);
                hitori.initGrid();
//                hitori.init();
//                hitori.pack();
//                hitori.setVisible(true);
                this.setVisible(false);
            }
            else if (p == p2){
                Logi logi = new Logi(this);
                logi.initGrid();
//                logi.pack();
//                logi.setVisible(true);
                this.setVisible(false);
            }
            if (p == p3) {
                Puzzle kakurasu = new Kakurasu(this);
                kakurasu.initGrid();
                this.setVisible(false);
            }
        }

    }


}

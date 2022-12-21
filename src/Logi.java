import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

//use numbers but display as letters
public class Logi extends JFrame implements Puzzle {

    private MainFrame fr;
    private JLabel label;

    private Spot[][] grid = new Spot[5][5];
    private LogiButton[][] buttons = new LogiButton[5][5];
    private int[][] spots = {
            {0,5,0,0,0},
            {3,0,0,1,0},
            {0,0,0,0,0,},
            {0,0,0,2,0},
            {0,0,0,0,4}
    };
    private int[][] sections = {
            {1,1,2,2,2},
            {1,3,3,2,4},
            {1,1,3,2,4},
            {5,3,3,4,4},
            {5,5,5,5,4}
    };
    public Hashtable<Integer, String> dict;



    public Logi(MainFrame fr) {
        this.fr = fr;
        dict = readColorToDict("logicolors.txt");

    }

    public Hashtable<Integer, String> readColorToDict(String fn) {
        Hashtable<Integer, String> dict = new Hashtable<>();
        File f = new File(fn);
        try {
            Scanner sc = new Scanner(f);
            int count = 1;
            while (sc.hasNextLine()) {
                if (sc.hasNextLine()) {
                    String s = sc.next();
                    dict.put(count, s);
                    count++;
                } else sc.next();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Logi file not found. Closing");
            System.exit(1);
            e.printStackTrace();
        }
        return dict;
    }

    public String numToColor(Hashtable<Integer, String> d, int i) {
        if (d.get(i) != null) return d.get(i);
        return null;
    }

    
    @Override
    public void initGrid() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 800));
        this.setVisible(true);
        this.pack();

        Container ct = this.getContentPane();
        JLayeredPane lpane = new JLayeredPane();

        Font gridFont = new Font("Serif",Font.BOLD, 36);
        Font labelFont = new Font("Serif",Font.BOLD, 16);

        JPanel box = new JPanel();
        box.setBounds(140, 55, 500, 500);
        box.setLayout(new GridLayout(5, 5));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                JButton jb = new LogiButton(this, i, j);

                if (sections[i][j] == 1) {
                    jb.setBackground(new Color(245, 216, 252));
                } else if (sections[i][j] == 2) {
                    jb.setBackground(new Color(202, 205, 236));
                } else if (sections[i][j] == 3) {
                    jb.setBackground(new Color(161, 213, 178 ));
                } else if (sections[i][j] == 4) {
                    jb.setBackground(new Color(249, 244, 202));
                } else {
                    jb.setBackground(new Color(139, 190, 199 ));
                }

                // if there is a given number, display it
                if (spots[i][j] != 0) {
                    jb.setText(dict.get(spots[i][j]));
                    jb.setEnabled(false);
                    // fills grid with 0's in spots that are not given
                    grid[i][j] = new Spot(spots[i][j]);
                }else {
                    grid[i][j] = new Spot(0);
                }


                jb.setOpaque(true);
                jb.setBorderPainted(false);
                jb.setFont(gridFont);






                buttons[i][j] = (LogiButton) jb;
                box.add(jb);
            }
        }

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new FlowLayout());
        bPanel.setBounds(0,560, 800, 200);

        // reset button
        JButton reset = new Reset(this);
        reset.setFont(labelFont);
        bPanel.add(reset, BorderLayout.CENTER);

        JButton solve = new Solver(this, grid, getPossibles());
        solve.setText("Solve Puzzle");
        solve.setFont(labelFont);
        bPanel.add(solve, BorderLayout.CENTER);

        JButton check = new Check(this);
        check.setText("Check");
        check.setFont(labelFont);
        bPanel.add(check, BorderLayout.CENTER);

        JButton menu = new JButton("Menu");
        menu.addActionListener(new PButton(fr, 0));
        menu.setFont(labelFont);
        bPanel.add(menu, BorderLayout.CENTER);

        label = new JLabel("");
        label.setFont(labelFont);
        bPanel.add(label);

        lpane.add(box, Integer.valueOf(0), 0);
        lpane.add(bPanel, Integer.valueOf(1), 0);
        ct.add(lpane);

        ct.revalidate();
        ct.repaint();

    }


    @Override
    public void clearGrid() {
        for (int row = 0; row < grid.length; row++) { //for each row
            for (int col = 0; col < grid[0].length; col++) {//for each col
                grid[row][col].setValue(0); // empty value
                if (buttons[row][col] != null && spots[row][col]==0) buttons[row][col].setEmpty(); // set jb colors back to white
            }
        }
        //clears any existing messages
        label.setText("");
    }

    @Override
    public void fillGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (isSelected(row, col)) {
                    buttons[row][col].setCorrect();
                } else {
                    buttons[row][col].setEmpty();
                }
            }
        }
    }

    public boolean isSelected(int r, int c) {
        if (grid[r][c].getValue() != 0) return true;
        else return false;
    }

    @Override
    public void setPos(int r, int c) {

        if (grid[r][c].getValue() == 0) { //blank
            grid[r][c].setValue(1); // to be "B"
        } else {
            int co = grid[r][c].getValue();
            grid[r][c].setValue(co++);
        }
        if (grid[r][c].getValue() > 5) {
            grid[r][c].setValue(0);
        }

    }

    public Spot getPos(int r, int c) { return grid[r][c]; }


    @Override
    public boolean checkConstraints() {
        if (checkNow() && !checkDuplicateSection()) return true;
        return false;
    }

    @Override
    public boolean checkNow() {
        return !checkDuplicates();
    }

    public boolean checkDuplicateSection() {
        int[] values = getPossibles();

        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid[row].length; row++) {
                int s = sections[row][col];

                // iterate through each element in int array
                for (int v : values) { // 1
                    int[] repeat = new int[6];
                    for (int i = 0; i < repeat.length; i++) { //0
                        if (s == v) { //if current spot is section 1
                            repeat[i] = grid[row][col].getValue(); //add value of current spot to repeat at index 1-1
                        }
                    }

                    for (int k = 0; k < repeat.length - 1; k++) {
                        for (int l = k + 1; l < repeat.length; l++) {

                            if (repeat != null) {
                                if (repeat[k] == repeat[l]) {
                                    return true;

                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }


    public boolean checkDuplicates() {
        int[] temp1 = new int[grid.length + 1];
        int[] temp2 = new int[grid.length + 1];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                temp1[col] = grid[row][col].getValue();
                temp2[col] = grid[col][row].getValue();
            }

            if (hasDuplicates(temp1) || hasDuplicates(temp2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDuplicates(int[] list) {
        for(int row = 0; row < list.length -1; row++) {
            for(int col = row+1; col < list.length; col++) {

                if(list != null && list[row] != 0 && list[col] != 0) {
                    if(list[row]==list[col]){
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public void solved() {
        if (this.checkNow()) {
            label.setText("Keep going!");
            if (this.checkConstraints()) {
                label.setText("Solved!");
                JOptionPane.showMessageDialog(null, "The Puzzle is solved!");
            }
        }
        else {
            label.setText("Wrong!");
        }
    }


    public int[] getPossibles() {
        return new int[]{1, 2, 3, 4, 5};
    }

}

//make constraint classes and make instances of those classes in the game classes

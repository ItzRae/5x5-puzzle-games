import javax.swing.*;
import java.awt.*;

public class Kakurasu extends JFrame implements Puzzle {

    private MainFrame fr;
    private JLabel label;

    private Spot[][] grid = new Spot[5][5];

    private int[] rowTotal = {2, 4, 9, 12, 6};
    private int[] colTotal = {9, 13, 8, 9, 4};
    private KakurasuButton[][] kb = new KakurasuButton[5][5];




    public Kakurasu() {}

    public Kakurasu(MainFrame fr) {
        this.fr = fr;
    }

    @Override
    public void initGrid() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 800));
        this.setVisible(true);
        this.pack();

        Container ct = this.getContentPane();
//        JLayeredPane lpane = new JLayeredPane();
        JPanel center = new JPanel(new GridLayout(grid.length+2,grid.length+2,5,5));
        center.setBackground(Color.PINK);
        ct.add(center, BorderLayout.CENTER);

        Font gridFont = new Font("Serif",Font.BOLD, 36);
        Font labelFont = new Font("Serif",Font.BOLD, 16);

//        ct.setLayout(new FlowLayout());
//        JPanel box = new JPanel();
//        box.setBounds(140, 55, 500, 500);
//        box.setLayout(new GridLayout(5, 5));
////        box.setBackground(Color.gray);
//
//        JPanel bPanel = new JPanel();
//        bPanel.setLayout(new FlowLayout());
//        bPanel.setBounds(0,560, 800, 200);
//
//
        JPanel bPanel = new JPanel(new FlowLayout());
        bPanel.setBackground(Color.WHITE);
        ct.add(bPanel, BorderLayout.SOUTH);

        int l = 0;
        int l1 = 0;
        int row = 1;
        int col = 1;
        for (int i = 0; i < grid.length+2; i++) {
            for (int j = 0; j < grid.length + 2; j++) {
                if (i == 0 && j == 0 || i == grid.length + 1 && j == 0 || i == grid.length + 1 && j == grid.length + 1 || i == 0 && j == grid.length + 1) {
                    center.add(Box.createVerticalGlue());
                }
                else if (i == 0) {
                    JLabel num = new JLabel (Integer.toString(row++)); // sets row label of values
                    num.setFont(gridFont);
                    num.setHorizontalAlignment(JLabel.CENTER);
                    num.setVerticalAlignment(JLabel.BOTTOM);
                    center.add(num);
                }
                else if (j == 0) {
                    JLabel num = new JLabel (Integer.toString(col++)); // sets col label of values
                    num.setFont(gridFont);
                    num.setHorizontalAlignment(JLabel.RIGHT);
                    num.setVerticalAlignment(JLabel.CENTER);
                    center.add(num);
                }
                else if (j == grid.length+1) {
                    JLabel num = new JLabel(Integer.toString(rowTotal[l++])); // adds rowtotal values to label
                    num.setFont(gridFont);
                    num.setHorizontalAlignment(JLabel.LEFT);
                    num.setVerticalAlignment(JLabel.CENTER);
                    center.add(num);
                }
                else if (i == grid.length+1) {
                    JLabel num = new JLabel(Integer.toString(colTotal[l1++])); // adds coltotal labels
                    num.setFont(gridFont);
                    num.setHorizontalAlignment(JLabel.CENTER);
                    num.setVerticalAlignment(JLabel.TOP);
                    center.add(num);
                }

                else {
                    JButton jb = new KakurasuButton(this, i-1, j-1); // creates button for grid (excluding row + col of given nums)
                    jb.setBorderPainted(false);
                    jb.setOpaque(true);
                    jb.setBackground(Color.white);
                    jb.setFont(gridFont);
                    center.add(jb);
                    grid[i-1][j-1] = new Spot(0);
                    kb[i-1][j-1] = (KakurasuButton) jb; // add buttons to grid

                }
            }
        }
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

//        lpane.add(center, Integer.valueOf(0), 00);
//        lpane.add(bPanel, Integer.valueOf(1), 0);
//        ct.add(center, BorderLayout.CENTER);
        ct.revalidate();
        ct.repaint();
    }

    @Override
    public void clearGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col].setValue(0);
                if (kb[row][col] != null) { kb[row][col].setEmpty(); }
            }
        }
        label.setText("");
    }

    @Override
    public void fillGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isShaded(row, col)) {
                    kb[row][col].setCorrect();
                } else {
                    kb[row][col].setEmpty();
                }
            }
        }
    }

    public boolean isShaded(int r, int c) {
        if (grid[r][c].getValue() == 1) return true;
        else return false;
    }
    
    @Override
    public void setPos(int r, int c) {
        if (grid[r][c].getValue() == 0) {
            grid[r][c].setValue(1);
        } else {
            grid[r][c].setValue(0);
        }
    }

    public Spot getPos(int r, int c) { return grid[r][c]; }

    @Override
    public boolean checkConstraints() {
        for (int row = 0; row < grid.length; row++) {
            int rowSum = 0; //have to do this for each row
            for (int col = 0; col < grid[0].length; col++) {
//                temp1[col] = getPos(row, col);
                if (isShaded(row, col))
                    rowSum = rowSum + (col + 1);
            }
            if (rowSum != rowTotal[row])
                return false;
        }

        for (int col = 0; col < grid[0].length; col++) {
            int colSum = 0; //have to do this for each row
            for (int row = 0; row < grid.length; row++) {
                if (isShaded(row, col))
                    colSum = colSum + (row + 1);
            }
            if (colSum != colTotal[col])
                return false;
        }

        return true;
    }

    @Override
    public boolean checkNow() {
//        Spot[] temp1 = new Spot[grid.length];
//        Spot[] temp2 = new Spot[grid.length];

        for(int row = 0; row < grid.length; row++) {
            int rowSum = 0; //have to do this for each row
            for (int col = 0; col < grid[0].length; col++) {
//                temp1[col] = getPos(row, col);
                if (isShaded(row, col))
                        rowSum = rowSum + col + 1;
                }
            if(rowSum > rowTotal[row])
                return false;
        }

        for(int col = 0; col < grid[0].length; col++) {
            int colSum = 0; //have to do this for each row
            for (int row = 0; row < grid.length; row++) {
                if (isShaded(row, col))
                    colSum = colSum + row + 1;
            }
            if(colSum > colTotal[col])
                return false;
        }

        return true;
    }
//
//    public int getRowSum (Spot[] list) {
//        // CHECK ROWS
//        int rowSum = 0;
//        for (int row = 0; row < list.length; row++) {
//            for (int col = 0; col < grid[0].length; col++) {
//                if (isShaded(row, col)) {
//                    rowSum = rowSum + (col + 1);
//                } // gets sum of shaded spots
//            }
//        }
//        return rowSum;
//    }
//
//    public int getColSum (Spot[] list) {
//        // CHECK ROWS
//        int colSum = 0;
//        for (int row = 0; row < list.length; row++) {
//            for (int col = 0; col < grid[0].length; col++) {
//                if (isShaded(row, col)) {
//                    colSum = colSum + (row + 1);
//                }// gets sum of shaded spots
//            }
//
//        }
//        return colSum;
//    }

    @Override
    public void solved() {
        if (this.checkNow()) {
            label.setText("You're getting there! Keep going :)");
            if (this.checkConstraints()) {
                label.setText("Puzzle Solved!");
                JOptionPane.showMessageDialog(null, "The Puzzle is solved!");
            }
        }
        else {
            label.setText("Try Again!");
        }
    }

    public int[] getPossibles() {
        return new int[]{0, 1};
    }
}

import java.awt.*;
import javax.swing.*;

public class Hitori extends JFrame implements Puzzle {

    private MainFrame fr;
    private JLabel label;

    private Spot[][] grid = new Spot[5][5];
    private int[][] spots = {
            {1,5,3,1,2},
            {5,4,1,3,4},
            {3,4,3,1,5},
            {4,4,2,3,3},
            {2,1,5,4,4}
    };
    private HitoriButton[][] hb = new HitoriButton[5][5];

    public Hitori(MainFrame fr) {
        this.fr = fr;

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

//        ct.setLayout(new FlowLayout());
        JPanel box = new JPanel();
        box.setBounds(140, 55, 500, 500);
        box.setLayout(new GridLayout(5, 5));
//        box.setBackground(Color.gray);

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

        // set up Hitori grid w/ hb
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                JButton jb = new HitoriButton(this, i, j);
                jb.setBackground(Color.white);
                jb.setFont(gridFont);
                jb.setText(Integer.toString(spots[i][j]));
                jb.setBorderPainted(false);
                jb.setOpaque(true);

                grid[i][j] = new Spot(0);
                hb[i][j] = (HitoriButton) jb;
                box.add(jb);
            }

        }

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
                if (hb[row][col] != null) hb[row][col].setEmpty(); // set jb colors back to white
            }
        }
        //clears any existing messages
        label.setText("");
    }

    @Override
    public void fillGrid() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (isShaded(row, col)) {
                    hb[row][col].setCorrect();
                } if(!isShaded(row,col))
                    hb[row][col].setEmpty();
                }
            }
    }


    @Override
    public void setPos(int r, int c) {
        if (grid[r][c].getValue() == 0) {
            grid[r][c].setValue(1); // to be shaded
        } else {
            grid[r][c].setValue(0);
        }
    }

    public Spot getPos(int r, int c) {
        return grid[r][c];
    }

    public boolean isShaded(int r, int c) {
        if (grid[r][c].getValue() == 1) return true;
        else return false;
    }

    public boolean checkNeighbor() {
        boolean r = false;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                //check top left corner
                if (row == 0 && col == 0) {
                    if (isShaded(row, col) && isShaded(row + 1, col)) r = true; //below
                    if (isShaded(row, col) && isShaded(row, col + 1)) r = true; //to the right
                }
                else if (row == grid.length - 1 && col == 0) { // bottom left corner
                    if (isShaded(row - 1, col) && isShaded(row, col)) r = true; //top
                    if (isShaded(row, col + 1) && isShaded(row, col)) r = true; //right
                }
                else if (row == grid.length - 1 && col == grid[row].length - 1) { //bottom right corner
                    if (isShaded(row - 1,col) && isShaded(row, col)) r = true;
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                }
                else if (row == 0 && col == grid[row].length - 1) { //top right corner
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                    if (isShaded(row + 1, col) && isShaded(row,col)) r = true;
                } else if (row == 0) {//top row of grid
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                    if(isShaded(row, col + 1) && isShaded(row, col)) r = true;
                    if (isShaded(row + 1, col) && isShaded(row, col)) r = true;
                } else if (row == grid.length - 1) { //last row of grid
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                    if (isShaded(row, col + 1) && isShaded(row, col)) r = true;
                    if (isShaded(row - 1, col) && isShaded(row, col)) r = true;
                } else if (col == 0) { //left edge of grid
                    if(isShaded(row, col + 1) && isShaded(row, col)) r = true;
                    if (isShaded(row - 1, col) && isShaded(row, col)) r = true;
                    if(isShaded(row + 1, col) && isShaded(row, col)) r = true;
                } else if (col == grid[row].length - 1) { //right edge of grid
                    if (isShaded(row + 1, col) && isShaded(row, col)) r = true;
                    if (isShaded(row - 1, col) && isShaded(row, col)) r = true;
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                } else { // any grid button not on the edge/corner
                    if (isShaded(row + 1, col) && isShaded(row, col)) r = true;
                    if (isShaded(row - 1, col) && isShaded(row, col)) r = true;
                    if (isShaded(row, col + 1) && isShaded(row, col)) r = true;
                    if (isShaded(row, col - 1) && isShaded(row, col)) r = true;
                }
            }
        }
        return r;
    }

    public boolean checkDuplicates() {
        for(int row = 0; row < grid.length; row++) {
            int[] repeat = new int[6];
            for(int col = 0; col < grid.length; col++) {
                if(!isShaded(row,col))
                    repeat[spots[row][col]]++;
            }
            for(int i = 1; i < repeat.length; i++) {
                if (repeat[i] > 1) {
                    return false;
                }
            }
        }
        for(int col = 0; col < grid[0].length; col++) {
            int[] repeat = new int[6];
            for(int row = 0; row < grid.length; row++) {
                if(!isShaded(row,col))
                    repeat[spots[row][col]]++;
            }
            for(int i = 1; i < repeat.length; i++) {
                if (repeat[i] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkConstraints() {
        if (checkNow() || !checkDuplicates()) return false;

        return true;
    }

    @Override
    public boolean checkNow() {
        return !checkNeighbor();
    }

    public int[] getPossibles() {
        return new int[]{0, 1};
    }

    @Override
    public void solved() {
        if (this.checkNow()) {
            label.setText("Keep going!");
            if (this.checkConstraints()) {
                label.setText("Puzzle Solved!");
                JOptionPane.showMessageDialog(null, "The Puzzle is solved!");
            }
        }
        else {
            label.setText("Not Quite!");
        }
    }


}

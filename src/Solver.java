import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Solver extends JButton implements ActionListener {

    private Puzzle g;
    private Spot[][] grid;
    private int[] possibleValues;

    public Solver(Puzzle g, Spot[][] grid, int[] possibleValues) {
        this.g = g;
        this.grid = grid;
        this.possibleValues = possibleValues;
        addActionListener(this);
    }

    public boolean label(Spot[][] grid, Puzzle g, int[] possibleValues, int row, int col) {
        if (row == grid.length) {
            return g.checkConstraints();
        }

        for (int v : possibleValues) {
            grid[row][col].setValue(v);
            if (g.checkNow()) {
                int newcol = col + 1;
                int newrow = row;
                if (newcol == grid[row].length) {
                    newcol = 0;
                    newrow = row + 1;
                }
                if (label(grid, g, possibleValues, newrow, newcol)) {
                    return true;
                }
            }
        }
        
        grid[row][col].setValue(0);
        return false;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        g.clearGrid(); //clears board
        int col = 0;
        int row = 0;
        boolean gResult = label(grid, g, possibleValues, row, col);
        if (gResult) {
            g.fillGrid();
            g.solved();
        }
    }
}

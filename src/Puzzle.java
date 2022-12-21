public interface Puzzle {

    public abstract void initGrid();

    public abstract void clearGrid();

    public abstract  void fillGrid();

    // set each button
    public abstract void setPos(int r, int c);

    // check constraints
    public abstract boolean checkConstraints();

    public abstract boolean checkNow();

    public abstract void solved();
}


import java.awt.*;

public class GameRun {
    public static void main (String[]args) {
        MainFrame frame = new MainFrame();
        frame.createFrame();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.pack();
        frame.repaint();
    }
}

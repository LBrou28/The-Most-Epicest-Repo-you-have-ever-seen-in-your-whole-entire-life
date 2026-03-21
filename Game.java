import javax.swing.*;
import Input.InputHandler;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chuck Norris Against Saddness");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(panel);
        frame.setVisible(true);
        

        panel.startgame();
    }
}

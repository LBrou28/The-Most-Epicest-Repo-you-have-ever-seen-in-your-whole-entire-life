import javax.swing.*;


public class Game{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chuck Norris Against Saddness"); 
        Gamepanel panel = new Gamepanel(); 

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.add(panel);

        panel.startgame();
    }

}

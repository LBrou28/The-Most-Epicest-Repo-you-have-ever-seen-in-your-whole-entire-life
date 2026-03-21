import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class StartPanel extends JPanel {
    public StartPanel(JFrame frame) {
        this.setLayout(new GridBagLayout());
        JButton chuckStartButton = new JButton("Start Game");

        chuckStartButton.addActionListener((ActionEvent e) -> {
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            gamePanel.requestFocusInWindow();
            gamePanel.startgame();
        });

        this.add(chuckStartButton);
    }
}

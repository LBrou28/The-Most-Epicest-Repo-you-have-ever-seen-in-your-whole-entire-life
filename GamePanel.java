import Entities.Enemies.*;
import Entities.Player;
import Input.InputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    BufferedImage chuck;

    Thread gameThread;
    boolean running;
    ArrayList<Enemy> enemies = new ArrayList<>();
    private InputHandler input;
    private Player player;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        input = new InputHandler();
        this.addKeyListener(input);
        this.setFocusable(true);

        player = new Player(input);

        try {
            chuck = ImageIO.read(new File("src/assets/Chuck.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startgame() {
        enemies.add(new ZynDemon());
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {
            player.update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (chuck != null) {
        g.drawImage(chuck, player.x, player.y, player.width, player.height, null);
        drawEnemies(g);
    } else{
        System.out.println("yo code trash ma boy");
    }
}

public void drawEnemies(Graphics g) {
    for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).draw(g);
    }
    Enemy.checkCollision(enemies, player);
}

}

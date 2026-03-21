import Entities.Enemies.*;
import Entities.Player;
import Entities.Projectile;
import Input.InputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    BufferedImage chuck;
    BufferedImage grass;

    Thread gameThread;
    boolean running;
    ArrayList<Enemy> enemies = new ArrayList<>();
    private InputHandler input;
    private Player player;
     private ArrayList<Projectile> projectiles = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        input = new InputHandler();
        this.addKeyListener(input);
        this.setFocusable(true);

        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        player = new Player(input, projectiles);

        try {
            chuck = ImageIO.read(new File("src/assets/Chuck.png"));
            grass = ImageIO.read(new File("src/assets/grass.jpg"));
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

            // Update projectiles
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = projectiles.get(i);
                p.update();

                // Remove if off-screen
                if (p.isOffScreen(this.getWidth())) {
                    projectiles.remove(i);
                    i--;
                }
            }
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

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    if (grass != null) {
        g.drawImage(grass, (int)(-player.x + centerX), (int)(-player.y + centerY), null);
    }


    if (chuck != null) {
        g.drawImage(chuck, centerX - 16, centerY - 32, 32, 64, null);
        drawEnemies(g);
        drawProjectiles(g); //draw my projectiles
    } else{
        System.out.println("yo code trash ma boy");
    }
}

//method for projectiles
private void drawProjectiles(Graphics g) {
    for (Projectile p : projectiles) {
        p.draw(g);
    }
}

public void drawEnemies(Graphics g) {
    for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).draw(g);
        Enemy.checkCollision(enemies.get(i), player);
    }
    
}

}

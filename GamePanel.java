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

    BufferedImage chuck, grass;

    Thread gameThread;
    boolean running;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();

    private InputHandler input;
    private Player player;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        input = new InputHandler();
        this.addKeyListener(input);
        this.setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        player = new Player(input, projectiles, enemies);

        try {
            chuck = ImageIO.read(new File("src/assets/Chuck.png"));
            grass = ImageIO.read(new File( "src/assets/grass.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startgame() {
        enemies.add(new ZynDemon());
        enemies.add(new FireSpitter());
        enemies.add(new SpeedyGonzales());
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) {

            player.update();

            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = projectiles.get(i);
                p.update();

                if (p.isOffScreen(player.x, player.y, getWidth(), getHeight())) {
                    projectiles.remove(i);
                    i--;
                }
            }

            repaint();

            try { Thread.sleep(16); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    if (grass != null) {
    double scale = 0.5; //smaller = more zoomed out

    int tileWidth = (int)(grass.getWidth() * scale);
    int tileHeight = (int)(grass.getHeight() * scale);

    int offsetX = (int)(-player.x * scale + centerX) % tileWidth;
    int offsetY = (int)(-player.y * scale + centerY) % tileHeight;

    if (offsetX > 0) offsetX -= tileWidth;
    if (offsetY > 0) offsetY -= tileHeight;

    for (int x = offsetX; x < getWidth(); x += tileWidth) {
        for (int y = offsetY; y < getHeight(); y += tileHeight) {
            g.drawImage(grass, x, y, tileWidth, tileHeight, null);
        }
    }
}


    for (Enemy e : enemies) {
    e.draw(g, player, getWidth(), getHeight());
    e.attack(player);

    if (Enemy.checkCollision(e, player)) {
        System.out.println("collision located");
    }
}

    for (Projectile p : projectiles) {
        p.draw(g, player.x, player.y, centerX, centerY);
    }

    if (chuck != null) {
        g.drawImage(chuck, centerX - 16, centerY - 32, 50, 90, null);
    }
}
}

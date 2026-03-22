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
            grass = ImageIO.read(new File("src/assets/grass.jpg"));
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

            for (Enemy e : enemies) {
                e.update(player);
            }

            separateEnemiesFromPlayer();
            separateEnemies();

            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = projectiles.get(i);
                p.update();

                if (p.isOffScreen(player.x, player.y, getWidth(), getHeight())) {
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

    private void separateEnemiesFromPlayer() {
        for (Enemy e : enemies) {
            double enemyCenterX = e.getX() + e.getWidth() / 2.0;
            double enemyCenterY = e.getY() + e.getHeight() / 2.0;

            double playerCenterX = player.x + player.width / 2.0;
            double playerCenterY = player.y + player.height / 2.0;

            double dx = enemyCenterX - playerCenterX;
            double dy = enemyCenterY - playerCenterY;

            double distance = Math.sqrt(dx * dx + dy * dy);

            double minDistance = (e.getWidth() + player.width) / 2.0;

            if (distance == 0) {
                dx = 1;
                dy = 0;
                distance = 1;
            }

            if (distance < minDistance) {
                double overlap = minDistance - distance;

                dx /= distance;
                dy /= distance;

                e.setX(e.getX() + dx * overlap);
                e.setY(e.getY() + dy * overlap);
            }
        }
    }

    private void separateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = i + 1; j < enemies.size(); j++) {
                Enemy a = enemies.get(i);
                Enemy b = enemies.get(j);

                double aCenterX = a.getX() + a.getWidth() / 2.0;
                double aCenterY = a.getY() + a.getHeight() / 2.0;
                double bCenterX = b.getX() + b.getWidth() / 2.0;
                double bCenterY = b.getY() + b.getHeight() / 2.0;

                double dx = bCenterX - aCenterX;
                double dy = bCenterY - aCenterY;

                double distance = Math.sqrt(dx * dx + dy * dy);

                double minDistance = (a.getWidth() + b.getWidth()) / 2.0;

                if (distance == 0) {
                    dx = 1;
                    dy = 0;
                    distance = 1;
                }

                if (distance < minDistance) {
                    double overlap = minDistance - distance;

                    dx /= distance;
                    dy /= distance;

                    a.setX(a.getX() - dx * overlap / 2.0);
                    a.setY(a.getY() - dy * overlap / 2.0);

                    b.setX(b.getX() + dx * overlap / 2.0);
                    b.setY(b.getY() + dy * overlap / 2.0);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (grass != null) {
            double scale = 0.75;

            int tileWidth = (int) (grass.getWidth() * scale);
            int tileHeight = (int) (grass.getHeight() * scale);

            int offsetX = (int) (-player.x * scale + centerX) % tileWidth;
            int offsetY = (int) (-player.y * scale + centerY) % tileHeight;

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
        }

        for (Projectile p : projectiles) {
            p.draw(g, player.x, player.y, centerX, centerY);
        }

        if (chuck != null) {
            g.drawImage(chuck, centerX - 16, centerY - 32, 50, 90, null);
        }
    }
}
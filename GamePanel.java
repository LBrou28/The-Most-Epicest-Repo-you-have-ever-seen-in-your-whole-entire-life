import Entities.Enemies.*;
import Entities.EnemySpawn;
import Entities.Player;
import Entities.PowerUp;
import Entities.PowerUpType;
import Entities.Projectile;
import Entities.UpgradeManager;
import Entities.PlayerHealth;
import Entities.Upgrade;
import Entities.UpgradeType;
import Entities.UpgradeManager;
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
    ArrayList<PowerUp> powerUps = new ArrayList<>();

    long lastPowerUpSpawnTime = 0;
    long powerUpSpawnCooldown = 8000;
    Random random = new Random();

    private InputHandler input;
    private Player player;

    private EnemySpawn spawner;
    private boolean gameOver = false;

    private boolean choosingUpgrade = false;
    private ArrayList<Upgrade> currentUpgrades = new ArrayList<>();

    private boolean gameWon = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        input = new InputHandler();
        this.addKeyListener(input);
        this.setFocusable(true);
        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        player = new Player(input, projectiles, enemies);

        spawner = new EnemySpawn(player, enemies, 800, 600);

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

    private void spawnRandomPowerUp() {
    PowerUpType[] types = PowerUpType.values();
    PowerUpType randomType = types[random.nextInt(types.length)];

    double spawnX = player.x + random.nextInt(401) - 200;
    double spawnY = player.y + random.nextInt(401) - 200;

    powerUps.add(new PowerUp(spawnX, spawnY, randomType));
    }

    @Override
public void run() {
    while (running) {
        if (!gameOver && !choosingUpgrade && !gameWon) {
            player.update();
            spawner.update();
        }
        if (!gameOver && !gameWon) {
            player.update();
        }

        long currentTime = System.currentTimeMillis();

        if (player.getHealth().isDead()) {
            gameOver = true;
        }

        if (player.getPERMA().isMaxed()) {
            gameWon = true;
        }
        if (!gameOver) {
        if (currentTime - lastPowerUpSpawnTime > powerUpSpawnCooldown) {
            spawnRandomPowerUp();
            lastPowerUpSpawnTime = currentTime;
        }

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp p = powerUps.get(i);

            if (p.collidesWith(player)) {
                p.apply(player);
                powerUps.remove(i);
                i--;
            }
        }

        for (Enemy e : enemies) {
            e.update(player);

            if (Enemy.checkCollision(e, player)) {
            e.attack(player);  
            player.takeDamage(5);
        }
}

        separateEnemiesFromPlayer();
        separateEnemies();

        spawner.update();

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update();

            if (p.isOffScreen(player.x, player.y, getWidth(), getHeight())) {
                projectiles.remove(i);
                i--;
            }
        }
    }
        repaint();

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    if (choosingUpgrade) {
        if (input.up) selectUpgrade(0);
        if (input.left) selectUpgrade(1);
        if (input.right) selectUpgrade(2);
    }   

    if (gameOver && input.restart) {
        resetGame();
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

            double minDistance = (e.getWidth() + player.width) / 2.0 - 15;

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

    private void openUpgradeMenu() {
        choosingUpgrade = true;
        currentUpgrades.clear();

        for (int i = 0; i < 3; i++) {
        currentUpgrades.add(UpgradeManager.getRandomUpgrade());
        }
    }
    private void resetGame() {
        enemies.clear();
        projectiles.clear();
        powerUps.clear();

        player = new Player(input, projectiles, enemies);
        spawner = new EnemySpawn(player, enemies, getWidth(), getHeight());

        gameOver = false;
        gameWon = false;    
    }
    private void selectUpgrade(int index) {
        if (index >= 0 && index < currentUpgrades.size()) {
            player.applyUpgrade(currentUpgrades.get(index));
            choosingUpgrade = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    if (grass != null) {
        double scale = 0.5;

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

        if (Enemy.checkCollision(e, player)) {
            player.takeDamage(5); 
}
    }

    for (PowerUp p : powerUps) {
        p.draw(g, player.x, player.y, centerX, centerY);
    }

    for (Projectile p : projectiles) {
    p.draw(g, player.x, player.y, centerX, centerY);
}


    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(new Color(100, 100, 100, 80));

    int radius = (int) player.getShootRadius();
    int diameter = radius * 2;

    g2.fillOval(centerX - radius, centerY - radius, diameter, diameter);

    
    if (chuck != null) {
    g.drawImage(chuck, centerX - 16, centerY - 32, 50, 90, null);
    }

    if (chuck != null) {
    g.drawImage(chuck, centerX - 16, centerY - 32, 50, 90, null);
    }
    if (choosingUpgrade) {
    Graphics2D g4 = (Graphics2D) g;

    // dark overlay
    g4.setColor(new Color(0, 0, 0, 200));
    g4.fillRect(0, 0, getWidth(), getHeight());

    g4.setFont(new Font("Arial", Font.BOLD, 20));

    for (int i = 0; i < currentUpgrades.size(); i++) {
        Upgrade up = currentUpgrades.get(i);

        int boxX = 150 + i * 200;
        int boxY = getHeight() / 2 - 50;

        // box
        g4.setColor(Color.GRAY);
        g4.fillRect(boxX, boxY, 150, 100);

        g4.setColor(Color.WHITE);
        g4.drawRect(boxX, boxY, 150, 100);

        // text
        g4.drawString((i+1) + ": " + up.name, boxX + 10, boxY + 30);
        g4.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        g4.drawString(up.description, boxX + 10, boxY + 60);

        g4.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        }
    }

    player.getHealth().draw(g);
    
    player.getPERMA().draw(g);

    if (gameOver) {
    Graphics2D g3 = (Graphics2D) g;

    g3.setColor(new Color(0, 0, 0, 180));
    g3.fillRect(0, 0, getWidth(), getHeight());

    g3.setColor(Color.RED);
    g3.setFont(new Font("Comic Sans MS", Font.BOLD, 50));

    String text = "Chuck lost his balance...\r\n" +"\r\n" + "But growth is never linear.\r\n" + "\r\n" + "Press R to continue the journey.";
    int textWidth = g3.getFontMetrics().stringWidth(text);

    g3.drawString(text, getWidth()/2 - textWidth/2, getHeight()/2 - 20);

    g3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    String restart = "Press R to Restart";
    int rWidth = g3.getFontMetrics().stringWidth(restart);

    g3.drawString(restart, getWidth()/2 - rWidth/2, getHeight()/2 + 20);
    }
    
}
}
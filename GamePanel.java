import Entities.Enemies.*;
import Entities.EnemySpawn;
import Entities.Player;
import Entities.PowerUp;
import Entities.PowerUpType;
import Entities.Projectile;
import Entities.Upgrade;
import Entities.UpgradeManager;
import Input.InputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
    private boolean gameWon = false;

    private boolean choosingUpgrade = false;
    private ArrayList<Upgrade> currentUpgrades = new ArrayList<>();

    private SadnessChuck boss = null;
    private boolean bossTriggered = false;
    private boolean bossIncoming = false;
    private boolean bossSpawned = false;

    private long bossIncomingStartTime = 0;
    private long bossIncomingDuration = 2500;

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

    private void triggerBossPhase() {
        bossTriggered = true;
        bossIncoming = true;
        bossSpawned = false;
        bossIncomingStartTime = System.currentTimeMillis();
        spawner.setBossMode(true);
    }

    private void spawnBoss() {
        double bossX = player.x + 450;
        double bossY = player.y - 200;

        boss = new SadnessChuck(bossX, bossY);
        enemies.add(boss);

        bossIncoming = false;
        bossSpawned = true;
    }

    @Override
    public void run() {
        while (running) {
            if (!gameOver && !choosingUpgrade && !gameWon) {
                player.update();

                if (!bossIncoming) {
                    spawner.update();
                }

                long currentTime = System.currentTimeMillis();

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
                    }
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

                if (!bossTriggered && player.getPERMA().isMaxed()) {
                    triggerBossPhase();
                }

                if (bossIncoming && currentTime - bossIncomingStartTime >= bossIncomingDuration) {
                    spawnBoss();
                }

                if (bossSpawned && boss != null && boss.isDead()) {
                    boss = null;
                    bossSpawned = false;
                    gameWon = true;
                }

                if (player.getHealth().isDead()) {
                    gameOver = true;
                }
            }

            if (choosingUpgrade) {
                if (input.up) selectUpgrade(0);
                if (input.left) selectUpgrade(1);
                if (input.right) selectUpgrade(2);
            }

            if ((gameOver || gameWon) && input.restart) {
                resetGame();
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

        boss = null;
        bossTriggered = false;
        bossIncoming = false;
        bossSpawned = false;
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

        player.getHealth().draw(g);
        player.getPERMA().draw(g);

        if (bossIncoming) {
            Graphics2D gWarn = (Graphics2D) g;
            gWarn.setFont(new Font("Arial", Font.BOLD, 36));
            gWarn.setColor(Color.RED);

            String text = "BOSS INCOMING!";
            int textWidth = gWarn.getFontMetrics().stringWidth(text);
            gWarn.drawString(text, getWidth() / 2 - textWidth / 2, 50);
        }

        if (bossSpawned && boss != null && !boss.isDead()) {
            Graphics2D gBoss = (Graphics2D) g;

            int barX = 100;
            int barY = 20;
            int barWidth = getWidth() - 200;
            int barHeight = 28;

            gBoss.setColor(Color.DARK_GRAY);
            gBoss.fillRect(barX, barY, barWidth, barHeight);

            double ratio = boss.getHealthValue() / boss.getMaxHealthValue();
            int filledWidth = (int) (barWidth * ratio);

            gBoss.setColor(Color.RED);
            gBoss.fillRect(barX, barY, filledWidth, barHeight);

            gBoss.setColor(Color.WHITE);
            gBoss.drawRect(barX, barY, barWidth, barHeight);
            gBoss.setFont(new Font("Arial", Font.BOLD, 18));
            gBoss.drawString("SADNESS CHUCK", barX + 10, barY + 20);
        }

        if (choosingUpgrade) {
            Graphics2D g4 = (Graphics2D) g;

            g4.setColor(new Color(0, 0, 0, 200));
            g4.fillRect(0, 0, getWidth(), getHeight());

            g4.setFont(new Font("Arial", Font.BOLD, 20));

            for (int i = 0; i < currentUpgrades.size(); i++) {
                Upgrade up = currentUpgrades.get(i);

                int boxX = 150 + i * 200;
                int boxY = getHeight() / 2 - 50;

                g4.setColor(Color.GRAY);
                g4.fillRect(boxX, boxY, 150, 100);

                g4.setColor(Color.WHITE);
                g4.drawRect(boxX, boxY, 150, 100);

                g4.drawString((i + 1) + ": " + up.name, boxX + 10, boxY + 30);
                g4.setFont(new Font("Arial", Font.PLAIN, 14));
                g4.drawString(up.description, boxX + 10, boxY + 60);

                g4.setFont(new Font("Arial", Font.BOLD, 20));
            }
        }

        if (gameOver) {
            Graphics2D g3 = (Graphics2D) g;

            g3.setColor(new Color(0, 0, 0, 180));
            g3.fillRect(0, 0, getWidth(), getHeight());

            g3.setColor(Color.RED);
            g3.setFont(new Font("Arial", Font.BOLD, 32));

            String line1 = "Chuck lost his balance...";
            String line2 = "But growth is never linear.";
            String line3 = "Press R to continue the journey.";

            int w1 = g3.getFontMetrics().stringWidth(line1);
            int w2 = g3.getFontMetrics().stringWidth(line2);
            int w3 = g3.getFontMetrics().stringWidth(line3);

            g3.drawString(line1, getWidth() / 2 - w1 / 2, getHeight() / 2 - 40);
            g3.drawString(line2, getWidth() / 2 - w2 / 2, getHeight() / 2);
            g3.drawString(line3, getWidth() / 2 - w3 / 2, getHeight() / 2 + 40);
        }

        if (gameWon) {
            Graphics2D gWin = (Graphics2D) g;

            gWin.setColor(new Color(0, 0, 0, 180));
            gWin.fillRect(0, 0, getWidth(), getHeight());

            gWin.setColor(Color.GREEN);
            gWin.setFont(new Font("Arial", Font.BOLD, 30));

            String text = "You have achieved true self-happiness";
            int textWidth = gWin.getFontMetrics().stringWidth(text);
            gWin.drawString(text, getWidth() / 2 - textWidth / 2, getHeight() / 2);

            gWin.setFont(new Font("Arial", Font.BOLD, 18));
            String restart = "Press R to play again";
            int restartWidth = gWin.getFontMetrics().stringWidth(restart);
            gWin.drawString(restart, getWidth() / 2 - restartWidth / 2, getHeight() / 2 + 35);
        }
    }
}
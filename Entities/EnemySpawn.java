package Entities;

import Entities.Enemies.*;
import java.util.ArrayList;
import java.util.Random;

public class EnemySpawn {

    private ArrayList<Enemy> enemies;
    private Player player;

    private int screenWidth;
    private int screenHeight;

    private long lastSpawnTime = 0;

    private long baseSpawnDelay = 900;
    private final long minSpawnDelay = 200;

    private long lastDifficultyIncrease = 0;
    private final long difficultyInterval = 10000;
    private final long spawnStep = 50;

    private boolean bossMode = false;
    private final long bossExtraDelay = 600;

    private Random random = new Random();

    public EnemySpawn(Player player, ArrayList<Enemy> enemies, int screenWidth, int screenHeight) {
        this.player = player;
        this.enemies = enemies;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastDifficultyIncrease >= difficultyInterval) {
            baseSpawnDelay = Math.max(minSpawnDelay, baseSpawnDelay - spawnStep);
            lastDifficultyIncrease = currentTime;
        }

        long currentSpawnDelay = getCurrentSpawnDelay();

        if (currentTime - lastSpawnTime >= currentSpawnDelay) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }
    }

    private long getCurrentSpawnDelay() {
        if (bossMode) {
            return baseSpawnDelay + bossExtraDelay;
        }
        return baseSpawnDelay;
    }

    public void setBossMode(boolean bossMode) {
        this.bossMode = bossMode;
    }

    private void spawnEnemy() {
        int margin = 150;
        double spawnX = 0;
        double spawnY = 0;

        double playerCenterX = player.x + player.width / 2.0;
        double playerCenterY = player.y + player.height / 2.0;

        boolean validSpawn = false;

        while (!validSpawn) {
            int side = random.nextInt(4);

            switch (side) {
                case 0: // top
                    spawnX = player.x + random.nextInt(screenWidth + 200) - (screenWidth / 2 + 100);
                    spawnY = player.y - screenHeight / 2.0 - margin;
                    break;

                case 1: // bottom
                    spawnX = player.x + random.nextInt(screenWidth + 200) - (screenWidth / 2 + 100);
                    spawnY = player.y + screenHeight / 2.0 + margin;
                    break;

                case 2: // left
                    spawnX = player.x - screenWidth / 2.0 - margin;
                    spawnY = player.y + random.nextInt(screenHeight + 200) - (screenHeight / 2 + 100);
                    break;

                case 3: // right
                    spawnX = player.x + screenWidth / 2.0 + margin;
                    spawnY = player.y + random.nextInt(screenHeight + 200) - (screenHeight / 2 + 100);
                    break;
            }

            double dx = spawnX - playerCenterX;
            double dy = spawnY - playerCenterY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 300) {
                validSpawn = true;
            }
        }

        Enemy newEnemy = createRandomEnemy(spawnX, spawnY);
        enemies.add(newEnemy);
    }

    private Enemy createRandomEnemy(double x, double y) {
        int roll = random.nextInt(100);
        Enemy e;

        if (roll < 8) {
            e = new FireSpitter();
        } else if (roll < 38) {
            e = new SpeedyGonzales();
        } else if (roll < 58) {
            e = new LonelyGhost();
        } else if (roll < 73) {
            e = new DoubtPhantom();
        } else if (roll < 86) {
            e = new Sadness();
        } else {
            e = new ZynDemon();
        }

        e.setX(x);
        e.setY(y);

        return e;
    }
}
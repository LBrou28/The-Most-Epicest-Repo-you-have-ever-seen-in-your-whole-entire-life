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
    private long spawnDelay = 1200;      // starts slower
    private long minSpawnDelay = 300;    // cap so it never becomes infinite
    private long lastDifficultyIncrease = 0;
    private long difficultyInterval = 10000; // every 10 sec
    private long spawnStep = 75;         // reduce delay by 75 ms each step

    private Random random = new Random();

    public EnemySpawn(Player player, ArrayList<Enemy> enemies, int screenWidth, int screenHeight) {
        this.player = player;
        this.enemies = enemies;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        // Spawn enemy when enough time has passed
        if (currentTime - lastSpawnTime >= spawnDelay) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }

        // Increase difficulty over time, but stop at minSpawnDelay
        if (currentTime - lastDifficultyIncrease >= difficultyInterval) {
            spawnDelay = Math.max(minSpawnDelay, spawnDelay - spawnStep);
            lastDifficultyIncrease = currentTime;
        }
    }

    private void spawnEnemy() {
        int margin = 150; // farther outside the screen
        int side = random.nextInt(4);

        double spawnX = 0;
        double spawnY = 0;

        double playerCenterX = player.x + player.width / 2.0;
        double playerCenterY = player.y + player.height / 2.0;

        boolean validSpawn = false;

        while (!validSpawn) {
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

            // extra safety so enemies never spawn too close
            if (distance > 250) {
                validSpawn = true;
            } else {
                side = random.nextInt(4);
            }
        }

        Enemy newEnemy = createRandomEnemy(spawnX, spawnY);
        enemies.add(newEnemy);
    }

    private Enemy createRandomEnemy(double x, double y) {
        int roll = random.nextInt(100);
        Enemy e;

        if (roll < 8) {
            e = new FireSpitter();        // less common
        } else if (roll < 38) {
            e = new SpeedyGonzales();     // more common
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
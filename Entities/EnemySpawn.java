package Entities;

import Entities.Enemies.*;
import java.util.*;

public class EnemySpawn {

    private ArrayList<Enemy> enemies;
    private Player player;

    private int screenWidth;
    private int screenHeight;

    private long lastSpawnTime = 0;
    private long spawnDelay = 1000; // 1 second

    private Random random = new Random();

    public EnemySpawn(Player player, ArrayList<Enemy> enemies, int screenWidth, int screenHeight) {
        this.player = player;
        this.enemies = enemies;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSpawnTime > spawnDelay) {
            spawnEnemy();
            lastSpawnTime = currentTime;
        }
    }

    private void spawnEnemy() {

        int margin = 100; 

        int side = random.nextInt(3); 

        double spawnX = 0;
        double spawnY = 0;

        switch (side) {
            case 0:
                spawnX = player.x + random.nextInt(screenWidth) - screenWidth / 2;
                spawnY = player.y - screenHeight / 2 - margin;
                break;

            case 1:
                spawnX = player.x + random.nextInt(screenWidth) - screenWidth / 2;
                spawnY = player.y + screenHeight / 2 + margin;
                break;

            case 2: 
                spawnX = player.x - screenWidth / 2 - margin;
                spawnY = player.y + random.nextInt(screenHeight) - screenHeight / 2;
                break;

            case 3: 
                spawnX = player.x + screenWidth / 2 + margin;
                spawnY = player.y + random.nextInt(screenHeight) - screenHeight / 2;
                break;
        }

        Enemy newEnemy = createRandomEnemy(spawnX, spawnY);
        enemies.add(newEnemy);
    }

    private Enemy createRandomEnemy(double x, double y) {
        int type = random.nextInt(5);

        Enemy e;

        switch (type) {
            case 0: e = new ZynDemon(); break;
            case 1: e = new FireSpitter(); break;
            case 2: e = new SpeedyGonzales(); break;
            case 3: e = new LonelyGhost(); break;
            default: e = new DoubtPhantom(); break;
        }       

        e.setX(x);
        e.setY(y);

        return e;
    }
}
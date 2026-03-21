package Entities;

import Entities.Enemies.Enemy;
import Input.InputHandler;
import java.util.ArrayList;

public class Player {
<<<<<<< HEAD
=======
    public int x, y, width = 40, height = 80;
    int speed = 3;
    double dashSpeed = 10;
>>>>>>> eb13a574ef3175428bd7a7009c1e4d0efd4a0111

    public int x = 100, y = 100;

    private InputHandler input;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;

    private long lastShotTime = 0;
    private long fireRate = 500; // milliseconds

    private boolean isDashing = false;
    private int dashSpeed = 12;
    private int normalSpeed = 4;

    private long dashDuration = 150; // ms
    private long dashCooldown = 500; // ms

    private long dashStartTime = 0;
    private long lastDashTime = 0;
    public Player(InputHandler input, ArrayList<Projectile> projectiles, ArrayList<Enemy> enemies) {
        this.input = input;
        this.projectiles = projectiles;
        this.enemies = enemies;
    }

    public void update() {

    long currentTime = System.currentTimeMillis();

    // Start dash
    if (input.dash && currentTime - lastDashTime > dashCooldown) {
        isDashing = true;
        dashStartTime = currentTime;
        lastDashTime = currentTime;
    }

    // Movement
    int speed = isDashing ? dashSpeed : normalSpeed;

    if (input.up) y -= speed;
    if (input.down) y += speed;
    if (input.left) x -= speed;
    if (input.right) x += speed;

    // End dash
    if (isDashing && currentTime - dashStartTime > dashDuration) {
        isDashing = false;
    }

    // Auto shooting
    if (currentTime - lastShotTime > fireRate) {
        shoot();
        lastShotTime = currentTime;
    }

        if (currentTime - lastShotTime > fireRate) {
            shoot();
            lastShotTime = currentTime;
        }

    }

    private void shoot() {
    Enemy target = getNearestEnemy();

    if (target != null) {
        int playerCenterX = x + 20;
        int playerCenterY = y + 40;

        double dx = target.getCenterX() - playerCenterX;
        double dy = target.getCenterY() - playerCenterY;

        projectiles.add(new Projectile(playerCenterX, playerCenterY, dx, dy));
    }
}

    private Enemy getNearestEnemy() {
    Enemy closest = null;
    double minDist = Double.MAX_VALUE;

    for (Enemy e : enemies) {
        double dist = Math.hypot(e.getX() - x, e.getY() - y);

        if (dist < minDist) {
            minDist = dist;
            closest = e;
        }
    }

    return closest;
}
}
package Entities;
<<<<<<< HEAD
=======

>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
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

<<<<<<< HEAD
    ArrayList<Enemy> enemies;

    ArrayList<Projectile> projectiles;
=======
    public int x = 100, y = 100;
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8

    private InputHandler input;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;

    private long lastShotTime = 0;
    private long fireRate = 500; // milliseconds

<<<<<<< HEAD
    long lastShotTime = 0;
    long shootCooldown = 500; // milliseconds
    double shootRadius = 250;

    InputHandler input;

    public Player(InputHandler input, ArrayList<Projectile> projectiles, ArrayList<Enemy> enemies) {
    this.input = input;
    this.projectiles = projectiles;
    this.enemies = enemies;
    x = 100;
    y = 100;
=======
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
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
    }

    public void update() {

<<<<<<< HEAD
    double dx = 0;
    double dy = 0;

    if (input.up) dy -= 1;
    if (input.down) dy += 1;
    if (input.left) dx -= 1;
    if (input.right) dx += 1;

    //for projectiles
    long currentTime = System.currentTimeMillis();

    Enemy closestEnemy = getClosestEnemyInRange();

    if (closestEnemy != null && currentTime - lastShotTime > shootCooldown) {
    shoot(closestEnemy);
    lastShotTime = currentTime;
    }

    // START DASH
    if (input.dash && !isDashing && (currentTime - lastDashTime > dashCooldown)) {
=======
    long currentTime = System.currentTimeMillis();

    // Start dash
    if (input.dash && currentTime - lastDashTime > dashCooldown) {
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
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

<<<<<<< HEAD
    private Enemy getClosestEnemyInRange() {
    Enemy closestEnemy = null;
    double closestDistance = Double.MAX_VALUE;

    double playerCenterX = x + width / 2.0;
    double playerCenterY = y + height / 2.0;

    for (Enemy enemy : enemies) {
        double enemyCenterX = enemy.getX() + enemy.getWidth() / 2.0;
        double enemyCenterY = enemy.getY() + enemy.getHeight() / 2.0;

        double dx = enemyCenterX - playerCenterX;
        double dy = enemyCenterY - playerCenterY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < shootRadius && distance < closestDistance) {
            closestDistance = distance;
            closestEnemy = enemy;
        }
    }

    return closestEnemy;
}

private void shoot(Enemy enemy) {
    double playerCenterX = x + width / 2.0;
    double playerCenterY = y + height / 2.0;

    double enemyCenterX = enemy.getX() + enemy.getWidth() / 2.0;
    double enemyCenterY = enemy.getY() + enemy.getHeight() / 2.0;

    double dx = enemyCenterX - playerCenterX;
    double dy = enemyCenterY - playerCenterY;

    double length = Math.sqrt(dx * dx + dy * dy);

    if (length != 0) {
        dx /= length;
        dy /= length;

        projectiles.add(new Projectile(playerCenterX, playerCenterY, dx, dy));
    }
}
}   
=======
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
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8

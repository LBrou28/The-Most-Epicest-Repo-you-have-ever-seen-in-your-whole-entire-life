package Entities;
import Entities.Enemies.Enemy;
import Input.InputHandler;
import java.util.ArrayList;

public class Player {
    public int x, y, width = 40, height = 80;
    int speed = 3;
    double dashSpeed = 10;

    ArrayList<Enemy> enemies;

    ArrayList<Projectile> projectiles;

    boolean isDashing = false;
    long dashTime = 0;
    long dashDuration = 150; // milliseconds

    long lastDashTime = 0;
    long dashCooldown = 5000; // milliseconds

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
    }

    public void update() {

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
        isDashing = true;
        dashTime = currentTime;
        lastDashTime = currentTime;
    }

    // END DASH
    if (isDashing && (currentTime - dashTime > dashDuration)) {
        isDashing = false;
    }

    double currentSpeed = isDashing ? dashSpeed : speed;

    if (dx != 0 || dy != 0) {
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        x += dx * currentSpeed;
        y += dy * currentSpeed;
    }
}

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
package Entities;

import Entities.Enemies.Enemy;
import Input.InputHandler;
import java.util.ArrayList;

public class Player {

    public int x, y, width = 32, height = 64;
    int speed = 3;
    double dashSpeed = 10;

    ArrayList<Projectile> projectiles;
    ArrayList<Enemy> enemies;

    boolean isDashing = false;
    long dashTime = 0;
    long dashDuration = 150;

    long lastDashTime = 0;
    long dashCooldown = 500;

    long lastShotTime = 0;
    long shootCooldown = 500;
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

        long currentTime = System.currentTimeMillis();

        // DASH
        if (input.dash && !isDashing && (currentTime - lastDashTime > dashCooldown)) {
            isDashing = true;
            dashTime = currentTime;
            lastDashTime = currentTime;
        }

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

        // AUTO AIM SHOOT
        Enemy closestEnemy = getClosestEnemyInRange();

        if (closestEnemy != null && currentTime - lastShotTime > shootCooldown) {
            shoot(closestEnemy);
            lastShotTime = currentTime;
        }
    }

    private Enemy getClosestEnemyInRange() {
        Enemy closest = null;
        double closestDist = Double.MAX_VALUE;

        double px = x + width / 2.0;
        double py = y + height / 2.0;

        for (Enemy e : enemies) {
            double ex = e.getX() + e.getWidth() / 2.0;
            double ey = e.getY() + e.getHeight() / 2.0;

            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < shootRadius && dist < closestDist) {
                closestDist = dist;
                closest = e;
            }
        }

        return closest;
    }

    private void shoot(Enemy enemy) {
        double px = x + width / 2.0;
        double py = y + height / 2.0;

        double ex = enemy.getX() + enemy.getWidth() / 2.0;
        double ey = enemy.getY() + enemy.getHeight() / 2.0;

        double dx = ex - px;
        double dy = ey - py;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;

            projectiles.add(new Projectile(px, py, dx, dy));
        }
    }
}
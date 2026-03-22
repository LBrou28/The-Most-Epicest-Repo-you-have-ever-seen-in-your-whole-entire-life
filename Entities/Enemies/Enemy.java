package Entities.Enemies;

import Entities.Player;
import Entities.Projectile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends ImageInterface {
    protected double xPos, yPos, damage, health, speed;
    protected int width, height;

    boolean isAlive;
    BufferedImage enemyImage;
    ArrayList<Projectile> projectiles = new ArrayList<>();

    public Enemy(double xPos, double yPos, double damage, double health) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.damage = damage;
        this.health = health;
    }

    public Enemy() {}

    public void draw(Graphics g, Player player, int panelWidth, int panelHeight) {
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        int screenX = (int) xPos - player.x + centerX;
        int screenY = (int) yPos - player.y + centerY;

        g.drawImage(enemyImage, screenX, screenY, width, height, null);

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update();
            p.draw(g, player.x, player.y, centerX, centerY);

            if (p.checkBulletCollision(player)) {
                projectiles.remove(i);
                i--;
            }
        }
    }

    public void update(Player player) {
        double playerCenterX = player.x + player.width / 2.0;
        double playerCenterY = player.y + player.height / 2.0;

        double enemyCenterX = xPos + width / 2.0;
        double enemyCenterY = yPos + height / 2.0;

        double dx = playerCenterX - enemyCenterX;
        double dy = playerCenterY - enemyCenterY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance != 0) {
            dx /= distance;
            dy /= distance;

            xPos += dx * speed;
            yPos += dy * speed;
        }
    }

    public BufferedImage getSprite() {
        return enemyImage;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(double x) {
        this.xPos = x;
    }

    public void setY(double y) {
        this.yPos = y;
    }

    public void takeDamage(double amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void attack(Player player) {
        player.takeDamage(damage);
    }

    public void removeEnemy(ArrayList<Enemy> enemies, int enemy) {
        enemies.remove(enemy);
    }

    public static boolean checkCollision(Enemy enemy, Player player) {
        int padding = 8;

        double playerLeft = player.x + padding;
        double playerTop = player.y + padding;
        double playerRight = player.x + player.width - padding;
        double playerBottom = player.y + player.height - padding;

        double enemyLeft = enemy.getX() + padding;
        double enemyTop = enemy.getY() + padding;
        double enemyRight = enemy.getX() + enemy.getWidth() - padding;
        double enemyBottom = enemy.getY() + enemy.getHeight() - padding;

        boolean overlapX = playerLeft < enemyRight && playerRight > enemyLeft;
        boolean overlapY = playerTop < enemyBottom && playerBottom > enemyTop;

        return overlapX && overlapY;
    }
}
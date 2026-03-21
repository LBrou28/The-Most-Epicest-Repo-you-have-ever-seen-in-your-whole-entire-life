package Entities.Enemies;
import Entities.Player;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy {

    protected double xPos, yPos, damage, health;
    protected static int width = 32, height = 32;
    BufferedImage enemyImage;

    public Enemy(double xPos, double yPos, double damage, double health) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.damage = damage;
        this.health = health;
    }

    public Enemy() {}

    public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
        int screenX = (int)xPos - playerX + centerX;
        int screenY = (int)yPos - playerY + centerY;

        g.drawImage(enemyImage, screenX, screenY, null);
    }

    public double getX() { return xPos; }
    public double getY() { return yPos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public abstract void attack();

    public static boolean checkCollision(Enemy enemy, Player player) {

    int padding = 8; // 🔥 adjust this number

    // Player bounds (smaller hitbox)
    double playerLeft = player.x + padding;
    double playerTop = player.y + padding;
    double playerRight = player.x + player.width - padding;
    double playerBottom = player.y + player.height - padding;

    // Enemy bounds (smaller hitbox)
    double enemyLeft = enemy.getX() + padding;
    double enemyTop = enemy.getY() + padding;
    double enemyRight = enemy.getX() + enemy.getWidth() - padding;
    double enemyBottom = enemy.getY() + enemy.getHeight() - padding;

    boolean overlapX = playerLeft < enemyRight && playerRight > enemyLeft;
    boolean overlapY = playerTop < enemyBottom && playerBottom > enemyTop;

    return overlapX && overlapY;
}
}
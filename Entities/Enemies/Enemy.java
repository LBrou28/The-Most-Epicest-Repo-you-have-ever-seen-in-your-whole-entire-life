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
        double playerLeft = player.x;
        double playerTop = player.y;
        double playerRight = playerLeft + player.width;
        double playerBottom = playerTop + player.height;

        double enemyLeft = enemy.getX();
        double enemyTop = enemy.getY();
        double enemyRight = enemyLeft + enemy.getWidth();
        double enemyBottom = enemyTop + enemy.getHeight();

        return playerLeft < enemyRight && playerRight > enemyLeft &&
               playerTop < enemyBottom && playerBottom > enemyTop;
    }
}
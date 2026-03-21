package Entities.Enemies; 
import Entities.Player;
import java.awt.*;
import java.awt.image.BufferedImage;
<<<<<<< HEAD
=======
import java.util.*;
>>>>>>> eb13a574ef3175428bd7a7009c1e4d0efd4a0111

public abstract class Enemy {
    protected double xPos, yPos, damage, health; 
    protected static int width, height;
    boolean isAlive;
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
<<<<<<< HEAD
=======
    public void takeDamage(double amount) {
        health -= amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public Rectangle getBounds() {
        if (enemyImage != null) {
            return new Rectangle((int)xPos, (int)yPos, enemyImage.getWidth(), enemyImage.getHeight());
        }
        return new Rectangle((int) xPos, (int)yPos, 32, 32);
    }
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8

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

    public abstract void attack(); 
    
    public void removeEnemy(ArrayList<Enemy> enemies, int enemy) {
        enemies.remove(enemy);
    }

    public static boolean checkCollision(Enemy enemy, Player player) {
        int playerWidth = 32;
        int playerHeight = 64;

        double playerLeft = player.x;
        double playerTop = player.y;
        double playerRight = playerLeft + playerWidth;
        double playerBottom = playerTop + playerHeight;

        double enemyLeft = enemy.getX();
        double enemyTop = enemy.getY();
        double enemyRight = enemyLeft + enemy.getWidth();
        double enemyBottom = enemyTop + enemy.getHeight();

        boolean overlapX = playerLeft < enemyRight && playerRight > enemyLeft;
        boolean overlapY = playerTop < enemyBottom && playerBottom > enemyTop;

        if (overlapX && overlapY) {
            System.out.println("Collision");
            return true;
        }

        return false;
    }
}
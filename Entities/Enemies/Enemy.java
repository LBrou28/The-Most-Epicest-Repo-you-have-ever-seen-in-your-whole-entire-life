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
    public void draw(Graphics g) {
            g.drawImage(enemyImage, (int)xPos, (int)yPos, null);
    }
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
   public static boolean checkCollision(Enemy enemies, Player player) {
    double playerXEnd = player.x + player.width;
    double playerYEnd = player.y + player.height;

    
        Enemy e = enemies;

        double eX = e.getX();
        double eY = e.getY();
        double eXEnd = eX + e.getWidth();
        double eYEnd = eY + e.getHeight();

        boolean overlapX = player.x < eXEnd && playerXEnd > eX;
        boolean overlapY = player.y < eYEnd && playerYEnd > eY;

        if (overlapX && overlapY) {
            System.out.println("Collision");
            return true;
        }
    
    return false;
    }
}

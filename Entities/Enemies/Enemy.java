package Entities.Enemies;
import Entities.Player;
import Entities.Projectile;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.util.*;

    
public abstract class Enemy {
    protected double xPos, yPos, damage, health, speed; 

    protected static int width, height;
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
    public void draw(Graphics g, Player player) {
            g.drawImage(enemyImage, (int)xPos, (int)yPos, width, height, null);
            for (int i = 0; i < projectiles.size(); i++) {
                projectiles.get(i).update();
                projectiles.get(i).draw(g);
                if (projectiles.get(i).checkBulletCollision(player)) {
                    attack(player);
                }
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
    public abstract void attack(Player player); 
    
    public void removeEnemy(ArrayList<Enemy> enemies, int enemy) {
        enemies.remove(enemy);
    }
    public static void moveEnemy() {
        
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

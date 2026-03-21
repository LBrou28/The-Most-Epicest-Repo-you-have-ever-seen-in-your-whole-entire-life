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
                projectiles.get(i).draw(g, (int)xPos,(int) yPos, width, height);
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
   
    public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
        int screenX = (int)xPos - playerX + centerX;
        int screenY = (int)yPos - playerY + centerY;

        g.drawImage(enemyImage, screenX, screenY, null);
    }


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
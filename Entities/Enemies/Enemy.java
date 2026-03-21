package Entities.Enemies;

import java.awt.*;
import java.awt.image.BufferedImage;

    
public abstract class Enemy {
    protected double xPos, yPos, damage, health;
    //Enemy image
    BufferedImage enemyImage;
    
    //ArrayList<Enemy> enemies = new ArrayList<>();
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

    public abstract void attack(); 
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }
    public double getCenterX() {
        return getBounds().getCenterX();
    }

    public double getCenterY() {
        return getBounds().getCenterY();
    }
}

package Entities.Enemies;

import java.awt.*;
import java.util.*;

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
    
    public BufferedImage getSprite() {
        return enemyImage;
    }

    public abstract void attack(); 
    
    
}

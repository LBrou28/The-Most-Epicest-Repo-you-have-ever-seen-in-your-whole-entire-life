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

    public void draw(Graphics g, ArrayList<Enemy> enemies) {
        for(int i = 0; i < enemies.size(); i++) {
            //g.drawImage(enemyImage, (int)xPos, (int)yPos, null);
            g.drawImage(enemyImage, (int)50, (int)50, 100, 100, null);
        }
    }

    public abstract void attack(); 
    
    
}

package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class ZynDemon extends Enemy {
    
    ArrayList<Projectile> projectiles = new ArrayList<>();
    public ZynDemon() {
        super(Math.random() * 250, Math.random() * 250, 5, 5);
        enemyImage = setSprite();
        width = enemyImage.getWidth()/6;
        height = enemyImage.getHeight()/6;
    }

    public void attack(Player player) { }

    public BufferedImage setSprite() {
        BufferedImage enemy = null;
        try {
        enemy = ImageIO.read(getClass().getResourceAsStream("EnemyImages/sadness.png"));
        }   catch (IOException e) {
        e.printStackTrace();
        }
        return enemy;
    }
}
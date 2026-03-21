package Entities.Enemies;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.*;

public class FireSpitter extends Enemy {
    
    public FireSpitter() {
        super(Math.random() * 250, Math.random() * 250, 5, 5);
        enemyImage = setSprite();
        width = enemyImage.getWidth()/6;
        height = enemyImage.getHeight()/6;
    }

    public BufferedImage setSprite() {
        BufferedImage enemy = null;
        try {
        enemy = ImageIO.read(getClass().getResourceAsStream("EnemyImages/demon.png"));
        }   catch (IOException e) {
        e.printStackTrace();
        }
        return enemy;
    }
    
    public void attack(Player player) {
        projectiles.add(new Projectile((int) xPos, (int) yPos, width, height)); 
    }

    
}

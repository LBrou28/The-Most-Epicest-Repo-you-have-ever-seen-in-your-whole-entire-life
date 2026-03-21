package Entities.Enemies;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import Entities.*;

public class SpeedyGonzales extends Enemy {
    
    public SpeedyGonzales() {
        super(Math.random() * 250, Math.random() * 250, 5, 5);
        enemyImage = setSprite();
        width = enemyImage.getWidth();
        height = enemyImage.getHeight();
    }
    
    public BufferedImage setSprite() {
        BufferedImage enemy = null;
        try {
        enemy = ImageIO.read(getClass().getResourceAsStream("EnemyImages/sadghost.png"));
        }   catch (IOException e) {
        e.printStackTrace();
        }
        return enemy;
    }
    public void attack(Player player) {

    }
}

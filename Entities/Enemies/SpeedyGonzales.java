package Entities.Enemies;
import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpeedyGonzales extends Enemy {
    
    public SpeedyGonzales() {
        super(Math.random() * 250, Math.random() * 250, 5, 5);
        enemyImage = setSprite();
        width = enemyImage.getWidth() / 4;
        height = enemyImage.getHeight() / 4;
        speed = 1.25; // fast boi
    }

    public BufferedImage setSprite() {
        BufferedImage enemy = null;
        try {
            enemy = ImageIO.read(getClass().getResourceAsStream("EnemyImages/sadghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enemy;
    }

    @Override
    public void attack(Player player) {
        // no attack yet
    }
}

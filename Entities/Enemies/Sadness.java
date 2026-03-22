package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sadness extends Enemy {

    public Sadness() {
        super(Math.random() * 250, Math.random() * 250, 5, 5);
        enemyImage = setSprite();
        width = enemyImage.getWidth() / 16;
        height = enemyImage.getHeight() / 16;
        speed = 1.0;
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
    public void attack(Player player) {

    }
}
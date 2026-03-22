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
        return sprites.get(3);
    }
    public void attack(Player player) {

    }
}
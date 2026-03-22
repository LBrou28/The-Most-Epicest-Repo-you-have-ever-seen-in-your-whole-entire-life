package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;

public class Sadness extends Enemy {

    public Sadness() {
        super(Math.random() * 250, Math.random() * 250, 5, 6);
        enemyImage = setSprite();
        width = enemyImage.getWidth() / 6;
        height = enemyImage.getHeight() / 6;
        speed = 1.5;
    }
    public BufferedImage setSprite() {
        return sprites.get(3);
    }
    public void attack(Player player) {

    }
}
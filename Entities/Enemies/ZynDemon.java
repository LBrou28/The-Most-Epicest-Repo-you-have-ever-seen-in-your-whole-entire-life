package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;

/* This enemy represents addiction. Drains accomplishment */
public class ZynDemon extends Enemy {

    private long lastDrainTime = 0;

    public ZynDemon() {
        super(Math.random() * 250, Math.random() * 250, 5, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 8;
        height = enemyImage.getHeight() / 8;
        speed = 1.2;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 1200) {
            player.getPERMA().increase("A", -5); 
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(5);
    }
}
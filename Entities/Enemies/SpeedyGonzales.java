package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/* This enemy represents lack of focus. Drains Engagement */
public class SpeedyGonzales extends Enemy {

    private long lastDrainTime = 0;

    public SpeedyGonzales() {
        super(Math.random() * 250, Math.random() * 250, 10, 3);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 4;
        height = enemyImage.getHeight() / 4;
        speed = 3.0;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 800) {
            super.attack(player);
            player.getPERMA().increase("E", -5);
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(4);
    }
}
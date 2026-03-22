package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/* This enemy represents addiction. Drains accomplishment */
public class ZynDemon extends Enemy {

    private long lastDrainTime = 0;

    public ZynDemon() {
        super(Math.random() * 250, Math.random() * 250, 5, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 12;
        height = enemyImage.getHeight() / 12;
        speed = 2.0;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 1200) {
            super.attack(player);
            player.getPERMA().increase("A", -5);
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("EnemyImages/Witch.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
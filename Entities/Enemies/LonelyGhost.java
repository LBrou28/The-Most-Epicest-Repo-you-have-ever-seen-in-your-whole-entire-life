package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/* this enemy represents loniliness. Drains relationships */
public class LonelyGhost extends Enemy {

    private long lastDrainTime = 0;

    public LonelyGhost() {
        super(Math.random() * 250, Math.random() * 250, 10, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 14;
        height = enemyImage.getHeight() / 14;
        speed = 2.0;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 1000) {
            super.attack(player);
            player.getPERMA().increase("R", -5);
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(2);
    }
}
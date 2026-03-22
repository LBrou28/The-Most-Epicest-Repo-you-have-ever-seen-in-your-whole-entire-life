package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/* this enemy represents loniliness. Drains relationships */
public class LonelyGhost extends Enemy {

    private long lastDrainTime = 0;

    public LonelyGhost() {
        super(Math.random() * 250, Math.random() * 250, 2, 25);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 8;
        height = enemyImage.getHeight() / 8;
        speed = 1.5;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 1000) {
            player.getPERMA().increase("R", -5); 
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(2);
    }
}
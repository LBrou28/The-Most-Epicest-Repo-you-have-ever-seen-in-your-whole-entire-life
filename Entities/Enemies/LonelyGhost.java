package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;

/* this enemy represents loniliness. Drains relationships */
public class LonelyGhost extends Enemy {

    private long lastDrainTime = 0;

    public LonelyGhost() {
        super(Math.random() * 250, Math.random() * 250, 2, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 20;
        height = enemyImage.getHeight() / 20;
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
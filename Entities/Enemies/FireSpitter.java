package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;

/*this enemy represents his anger. Drains happiness. */
public class FireSpitter extends Enemy {

    private long lastBurnTime = 0;

    public FireSpitter() {
        super(Math.random() * 250, Math.random() * 250, 3, 9);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 8;
        height = enemyImage.getHeight() / 8;
        speed = 1.0;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastBurnTime > 1000) {
            player.getPERMA().increase("P", -5); 
            lastBurnTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(1);
    }
}
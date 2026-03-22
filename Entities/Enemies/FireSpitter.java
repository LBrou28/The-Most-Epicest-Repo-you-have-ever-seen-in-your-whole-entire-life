package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*this enemy represents his anger. Drains happiness. */
public class FireSpitter extends Enemy {

    private long lastBurnTime = 0;

    public FireSpitter() {
        super(Math.random() * 250, Math.random() * 250, 3, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 8;
        height = enemyImage.getHeight() / 8;
        speed = 1.5;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastBurnTime > 1000) {
            super.attack(player);
            player.getPERMA().increase("P", -5);
            lastBurnTime = time;
        }
    }

    private BufferedImage setSprite() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("EnemyImages/demon.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/*this enemy reduces the meaning of Chuck. */
public class DoubtPhantom extends Enemy {

    private long lastDebuffTime = 0;

    public DoubtPhantom() {
        super(Math.random() * 250, Math.random() * 250, 10, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 12;
        height = enemyImage.getHeight() / 12;
        speed = 2.0;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDebuffTime > 1500) {
            super.attack(player);
            player.getPERMA().increase("M", -5);
            player.setDamage(Math.max(0.5, player.getDamage() - 0.2));
            lastDebuffTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(0);
    }
}
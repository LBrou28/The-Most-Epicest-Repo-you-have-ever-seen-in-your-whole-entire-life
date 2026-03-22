package Entities.Enemies;

import Entities.Player;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SadnessChuck extends Enemy {

    private final double maxBossHealth = 300;
    private long lastAttackTime = 0;

    public SadnessChuck(double x, double y) {
        super(x, y, 12, 300);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 4;
        height = enemyImage.getHeight() / 4;
        speed = 1.2;
    }

    @Override
    public void attack(Player player) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastAttackTime > 700) {
            player.takeDamage(damage);
            player.getPERMA().increase("P", -2);
            player.getPERMA().increase("E", -2);
            player.getPERMA().increase("R", -2);
            player.getPERMA().increase("M", -2);
            player.getPERMA().increase("A", -2);

            lastAttackTime = currentTime;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(6);
    }

    public double getHealthValue() {
        return health;
    }

    public double getMaxHealthValue() {
        return maxBossHealth;
    }
}
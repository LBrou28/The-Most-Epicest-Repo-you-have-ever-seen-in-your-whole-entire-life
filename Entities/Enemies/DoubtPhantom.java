package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;

/*this enemy reduces the meaning of Chuck. */
public class DoubtPhantom extends Enemy {

    private long lastDebuffTime = 0;

    public DoubtPhantom() {
        super(Math.random() * 250, Math.random() * 250, 2, 6);

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 16;
        height = enemyImage.getHeight() / 16;
        speed = 1.5;
    }

@Override
public void attack(Player player) {
    long time = System.currentTimeMillis();

    if (time - lastDebuffTime > 1500) {

        player.getPERMA().increase("M", -5);
        player.setDamage(Math.max(0.5, player.getDamage() - 0.2));

        lastDebuffTime = time;
    }
}

    private BufferedImage setSprite() {
        
            //return ImageIO.read(getClass().getResourceAsStream("EnemyImages/DoubtPhantom.png"));
            return sprites.get(0);
        
    }
}
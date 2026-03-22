package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FireSpitter extends Enemy {

    private long lastBurnTime = 0;
    private long lastFireballTime = 0;

    private ArrayList<Projectile> projectiles; // reference to GamePanel's projectile list

    public FireSpitter(ArrayList<Projectile> projectiles) {
        super(Math.random() * 250, Math.random() * 250, 3, 25);
        this.projectiles = projectiles;

        // initialize sprite
        enemyImage = setSprite();
        width = enemyImage.getWidth() / 8;
        height = enemyImage.getHeight() / 8;
        speed = 1.5;
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        // direct damage to PERMA
        if (time - lastBurnTime > 1000) {
            player.getPERMA().increase("P", -5);
            lastBurnTime = time;
        }

        // spit fireball toward player
        if (time - lastFireballTime > 1000 && projectiles != null) {
            double dx = (player.x + player.width / 2.0) - (this.getX() + this.getWidth() / 2.0);
            double dy = (player.y + player.height / 2.0) - (this.getY() + this.getHeight() / 2.0);
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist == 0) dist = 1;
            dx /= dist;
            dy /= dist;

            projectiles.add(new FireballProjectile(
                this.getX() + this.getWidth() / 2.0,
                this.getY() + this.getHeight() / 2.0,
                dx, dy
            ));

            System.out.print("Enemy fireball\n");

            lastFireballTime = time;
        }
    }

    private BufferedImage setSprite() {
        return ImageInterface.sprites.get(1); // second sprite in the ImageInterface list
    }
}
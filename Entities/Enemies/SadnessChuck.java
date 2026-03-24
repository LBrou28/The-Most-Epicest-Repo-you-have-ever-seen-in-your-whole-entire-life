package Entities.Enemies;

import Entities.Player;
import Entities.Projectile;
import Entities.SadnessProjectile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SadnessChuck extends Enemy {

    private final double maxBossHealth = 300;
    private long lastAttackTime = 0;

    ArrayList<SadnessProjectile> enemyProjectiles;

    BufferedImage[] projectileFrames;

    long lastShotTime = 0;
    long shootCooldown = 800;
    int projectileCount = 5;
    double spreadAngle = 5;

    public SadnessChuck(double x, double y, ArrayList<SadnessProjectile> enemyProjectiles, BufferedImage[] projectileFrames) {

    super(x, y, 12, 300);

    this.enemyProjectiles = enemyProjectiles;
    this.projectileFrames = projectileFrames;

    enemyImage = setSprite(); 

    width = enemyImage.getWidth() / 4;   
    height = enemyImage.getHeight() / 4; 

    speed = 1.2;
}
    @Override
    public void update(Player player) {
        super.update(player);

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime > shootCooldown) {
            shootSpread(player);
            lastShotTime = currentTime;
        }
        if (getHealthValue() < getMaxHealthValue() / 2) {
            shootCooldown = 400; 
            projectileCount = 5;
        }   
    }

private void shootSpread(Player player) {

    int projectileCount = 3;
    double spreadAngle = 15; 

    double sx = getX() + getWidth() / 2.0;
    double sy = getY() + getHeight() / 2.0;

    double px = player.x + player.width / 2.0;
    double py = player.y + player.height / 2.0;


    double dx = px - sx;
    double dy = py - sy;

    double baseAngle = Math.toDegrees(Math.atan2(dy, dx));

    for (int i = 0; i < projectileCount; i++) {

        double offset = spreadAngle * (
            (i - (projectileCount - 1) / 2.0) /
            Math.max(1, (projectileCount - 1))
        );

        double finalAngle = baseAngle + offset;

        double rad = Math.toRadians(finalAngle);
        double newDx = Math.cos(rad);
        double newDy = Math.sin(rad);

        double length = Math.sqrt(newDx * newDx + newDy * newDy);
        if (length != 0) {
            newDx /= length;
            newDy /= length;
        }

        enemyProjectiles.add(
            new SadnessProjectile(sx, sy, newDx, newDy, projectileFrames)
        );
    }
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
        try {
            return ImageIO.read(getClass().getResourceAsStream("EnemyImages/SadnessChuck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getHealthValue() {
        return health;
    }

    public double getMaxHealthValue() {
        return maxBossHealth;
    }
}
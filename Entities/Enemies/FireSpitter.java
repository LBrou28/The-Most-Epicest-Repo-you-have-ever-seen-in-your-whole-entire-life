package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

/*this enemy represents his anger. Drains happiness. */
public class FireSpitter extends Enemy {

    private long lastBurnTime = 0;

    private ArrayList<SadnessProjectile> enemyProjectiles;
    private BufferedImage[] projectileFrames;

    private long lastShotTime = 0;
    private long shootCooldown = 1200;

    BufferedImage[] fireFrames;

    public FireSpitter(ArrayList<SadnessProjectile> enemyProjectiles,
                   BufferedImage[] projectileFrames) {

    super(Math.random() * 250, Math.random() * 250, 10, 6);

    this.enemyProjectiles = enemyProjectiles;
    this.projectileFrames = projectileFrames;

    enemyImage = setSprite();
    fireFrames = loadFireFrames();

    width = enemyImage.getWidth() / 10;
    height = enemyImage.getHeight() / 10;
    speed = 1.5;
}

    @Override
    public void update(Player player) {
        super.update(player);

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime > shootCooldown) {
            shootAtPlayer(player);
            lastShotTime = currentTime;
        }
    }
    
    private void shootAtPlayer(Player player) {

        double sx = getX() + getWidth() / 2.0;
        double sy = getY() + getHeight() / 2.0;

        double px = player.x + player.width / 2.0;
        double py = player.y + player.height / 2.0;

        double dx = px - sx;
        double dy = py - sy;

        double length = Math.sqrt(dx * dx + dy * dy);

        if (length != 0) {
            dx /= length;
            dy /= length;

            enemyProjectiles.add(
                new SadnessProjectile(sx, sy, dx, dy, fireFrames)
            );
        }
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

<<<<<<< HEAD
    private BufferedImage[] loadFireFrames() {
    try {
        BufferedImage sheet = ImageIO.read(
            new java.io.File("src/assets/FSproj.png")
        );

        BufferedImage[] frames = new BufferedImage[4];

        int frameWidth = sheet.getWidth() / 4;
        int frameHeight = sheet.getHeight();

        for (int i = 0; i < 4; i++) {

            BufferedImage raw = sheet.getSubimage(
                i * frameWidth,
                0,
                frameWidth,
                frameHeight
            );

            frames[i] = makeTransparent(raw); 
        }

        return frames;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
=======
    private BufferedImage setSprite() {
        return sprites.get(1);
>>>>>>> 313b645e46dca1ceabff6f2751049cc0e240b95d
    }
    private BufferedImage makeTransparent(BufferedImage img) {

    BufferedImage newImg = new BufferedImage(
        img.getWidth(),
        img.getHeight(),
        BufferedImage.TYPE_INT_ARGB
    );

    for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight(); y++) {

            int rgba = img.getRGB(x, y);

            int r = (rgba >> 16) & 0xff;
            int g = (rgba >> 8) & 0xff;
            int b = rgba & 0xff;

            if (g > 0 && r < 100 && b < 100) {
                newImg.setRGB(x, y, 0x00000000);
            } else {
                newImg.setRGB(x, y, rgba);
            }
        }
    }

    return newImg;
}
private BufferedImage setSprite() {
    try {
        return ImageIO.read(
            getClass().getResourceAsStream("EnemyImages/demon.png")
        );
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
}
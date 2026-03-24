package Entities.Enemies;

import Entities.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class ZynDemon extends Enemy {

    private long lastDrainTime = 0;

    private BufferedImage[] witchFrames;

    private ArrayList<SadnessProjectile> enemyProjectiles;

    private long lastShotTime = 0;
    private long shootCooldown = 900;

    public ZynDemon(ArrayList<SadnessProjectile> enemyProjectiles) {

        super(Math.random() * 250, Math.random() * 250, 5, 6);

        this.enemyProjectiles = enemyProjectiles;

        enemyImage = setSprite();
        width = enemyImage.getWidth() / 12;
        height = enemyImage.getHeight() / 12;
        speed = 2.0;

        witchFrames = loadWitchFrames();
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
                new SadnessProjectile(sx, sy, dx, dy, witchFrames)
            );
        }
    }

    @Override
    public void attack(Player player) {
        long time = System.currentTimeMillis();

        if (time - lastDrainTime > 1200) {
            super.attack(player);
            player.getPERMA().increase("A", -5);
            lastDrainTime = time;
        }
    }

    private BufferedImage setSprite() {
        return sprites.get(5);
    }

    private BufferedImage[] loadWitchFrames() {
    try {
        BufferedImage sheet = ImageIO.read(
            new java.io.File("src/assets/Witchproj.png")
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
}
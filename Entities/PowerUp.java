package Entities;
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
public class PowerUp {
    private double x, y;
    private int width = 20;
    private int height = 20;
    private PowerUpType type;

    private BufferedImage image;

    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;

        this.image = loadImage(type);
    }

    public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
        int screenX = (int)(x - playerX + centerX);
        int screenY = (int)(y - playerY + centerY);

        switch (type) {
            case ATTACK_RADIUS -> g.setColor(Color.BLUE);
            case MOVE_SPEED -> g.setColor(Color.GREEN);
            case ATTACK_SPEED -> g.setColor(Color.YELLOW);
            case DAMAGE -> g.setColor(Color.RED);
        }
        if (image != null) {
            g.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    private BufferedImage loadImage(PowerUpType type) {
    try {
        String path = "";

        switch (type) {
            case ATTACK_RADIUS -> path = "src/assets/power_radius.png";
            case MOVE_SPEED -> path = "src/assets/power_speed.png";
            case ATTACK_SPEED -> path = "src/assets/power_attack.png";
            case DAMAGE -> path = "src/assets/power_damage.png";
        }

        BufferedImage img = ImageIO.read(new File(path));
        return makeTransparent(img);

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

            if (g > 0 && r < 120 && b < 120) {
                newImg.setRGB(x, y, 0x00000000);
            } else {
                newImg.setRGB(x, y, rgba);
            }
        }
    }

    return newImg;
}

    public boolean collidesWith(Player player) {
        return x < player.x + player.width &&
               x + width > player.x &&
               y < player.y + player.height &&
               y + height > player.y;
    }

    public void apply(Player player) {
        switch (type) {
            case ATTACK_RADIUS -> player.setShootRadius(player.getShootRadius() + 50);
            case MOVE_SPEED -> player.setSpeed(player.getSpeed() + 1);
            case ATTACK_SPEED -> player.setShootCooldown(Math.max(100, player.getShootCooldown() - 100));
            case DAMAGE -> player.setDamage(player.getDamage() + 1);
        }
    }
}

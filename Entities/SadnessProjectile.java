package Entities;

import java.awt.image.BufferedImage;
import Entities.Player;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class SadnessProjectile {

    public double x, y;
    public double dx, dy;  
    
    double angle;

    int speed = 5;

    int width = 16;
    int height = 16;

    BufferedImage[] frames;
    int currentFrame = 0;

    long lastFrameTime = 0;
    long frameDelay = 100;

    public SadnessProjectile(double x, double y, double dx, double dy, BufferedImage[] frames) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.frames = frames;

        this.angle = Math.atan2(dy, dx);
    }

        public void update() {
            x += dx * speed;
            y += dy * speed;

            if (frames != null) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastFrameTime > frameDelay) {
                    currentFrame = (currentFrame + 1) % frames.length;
                    lastFrameTime = currentTime;
                }
            }
        }

        public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {

        int screenX = (int)(x - playerX + centerX);
        int screenY = (int)(y - playerY + centerY);

        if (frames == null) return;

        BufferedImage img = frames[currentFrame];

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform old = g2d.getTransform();

        AffineTransform transform = new AffineTransform();

        transform.translate(screenX, screenY);

        transform.rotate(angle);

        transform.translate(-width / 2, -height / 2);

        g2d.drawImage(img, transform, null);

        g2d.setTransform(old);
    }

    public boolean isOffScreen(int playerX, int playerY, int screenWidth, int screenHeight) {
        int screenX = (int)(x - playerX + screenWidth / 2);
        int screenY = (int)(y - playerY + screenHeight / 2);

        return screenX < -50 || screenX > screenWidth + 50 ||
               screenY < -50 || screenY > screenHeight + 50;
    }

    public boolean hitsPlayer(Player player) {
        return x - width / 2 < player.x + player.width &&
               x + width / 2 > player.x &&
               y - height / 2 < player.y + player.height &&
               y + height / 2 > player.y;
    }
}
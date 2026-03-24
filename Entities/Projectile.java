package Entities;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Projectile {

    public double x, y;
    public double dx, dy;
    int speed = 6;
    int width = 30;
    int height = 20;
    
    BufferedImage[] frames;
    int currentFrame = 0;

    long lastFrameTime = 0;
    long frameDelay = 100;

    public Projectile(double x, double y, double dx, double dy, BufferedImage[] frames) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.frames = frames;
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

        if (frames != null) {
            g.drawImage(frames[currentFrame], screenX - width / 2, screenY - height / 2, width, height,null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(screenX - width / 2, screenY - height / 2, width, height);
        }
    }

    public boolean isOffScreen(int playerX, int playerY, int screenWidth, int screenHeight) {
        int screenX = (int)(x - playerX + screenWidth / 2);
        int screenY = (int)(y - playerY + screenHeight / 2);

        return screenX < -50 || screenX > screenWidth + 50 ||
               screenY < -50 || screenY > screenHeight + 50;
    }

    public boolean checkBulletCollision(Player player) {
    return x - width / 2 < player.x + player.width &&
       x + width / 2 > player.x &&
       y - height / 2 < player.y + player.height &&
       y + height / 2 > player.y;
    }
}

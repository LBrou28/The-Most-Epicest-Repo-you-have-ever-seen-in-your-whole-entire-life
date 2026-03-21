package Entities;
import java.awt.*;

public class Projectile {

    public double x, y;
    public double dx, dy;
    int speed = 6;
    int width = 10;
    int height = 5;

    public Projectile(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
        int screenX = (int)(x - playerX + centerX);
        int screenY = (int)(y - playerY + centerY);

        g.setColor(Color.BLACK);
        g.fillRect(screenX, screenY, width, height);
    }

    public boolean isOffScreen(int playerX, int playerY, int screenWidth, int screenHeight) {
        int screenX = (int)(x - playerX + screenWidth / 2);
        int screenY = (int)(y - playerY + screenHeight / 2);

        return screenX < -50 || screenX > screenWidth + 50 ||
               screenY < -50 || screenY > screenHeight + 50;
    }
}
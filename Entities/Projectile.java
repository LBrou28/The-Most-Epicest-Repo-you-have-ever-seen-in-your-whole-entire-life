package Entities;
import java.awt.*;

public class Projectile {

<<<<<<< HEAD
    public double x, y;
    public double dx, dy;
=======
    double x, y;
    double dx, dy;
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
    int speed = 6;
    int width = 10;
    int height = 5;

    public Projectile(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
<<<<<<< HEAD
        this.dx = dx;
        this.dy = dy;
=======

        double length = Math.sqrt(dx * dx + dy * dy);
        this.dx = dx / length;
        this.dy = dy / length;
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
    }

    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
        int screenX = (int)(x - playerX + centerX);
        int screenY = (int)(y - playerY + centerY);

        g.setColor(Color.BLACK);
<<<<<<< HEAD
        g.fillRect(screenX, screenY, width, height);
    }

    public boolean isOffScreen(int playerX, int playerY, int screenWidth, int screenHeight) {
        int screenX = (int)(x - playerX + screenWidth / 2);
        int screenY = (int)(y - playerY + screenHeight / 2);

        return screenX < -50 || screenX > screenWidth + 50 ||
               screenY < -50 || screenY > screenHeight + 50;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
=======
        g.fillRect((int)x, (int)y, width, height);
    }

    public boolean isOffScreen(int screenWidth, int screenHeight) {
        return x < 0 || x > screenWidth || y < 0 || y > screenHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
>>>>>>> e619941eb915fa528479466d1518fb933cef46e8
    }
}
package Entities;
import java.awt.*;

public class Projectile {

    double x, y;
    double dx, dy;
    int speed = 6;
    int width = 10;
    int height = 5;

    public Projectile(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;

        double length = Math.sqrt(dx * dx + dy * dy);
        this.dx = dx / length;
        this.dy = dy / length;
    }

    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int)x, (int)y, width, height);
    }

    public boolean isOffScreen(int screenWidth, int screenHeight) {
        return x < 0 || x > screenWidth || y < 0 || y > screenHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
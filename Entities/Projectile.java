package Entities;
import java.awt.*;

import Entities.Enemies.Enemy;
public class Projectile {

    int x, y;
    int speed = 6;
    int width = 10;
    int height = 5;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        x += speed; // move to the right
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }

    // THIS method is required to remove projectiles off-screen
    public boolean isOffScreen(int screenWidth) {
        return x > screenWidth;
    }

    public boolean checkBulletCollision(Player player) {
    return x < player.x + player.width &&
           x + width > player.x &&
           y < player.y + player.height &&
           y + height > player.y;
    }
}

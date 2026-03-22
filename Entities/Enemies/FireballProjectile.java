package Entities.Enemies;

import Entities.*;
import java.awt.*;

public class FireballProjectile extends Projectile {

    public FireballProjectile(double x, double y, double dx, double dy) {
        super(x, y, dx, dy);
        speed = 5; // you can tweak fireball speed separately
    }
   @Override
public void draw(Graphics g, int playerX, int playerY, int centerX, int centerY) {
    int screenX = centerX + (int)(x - playerX);
    int screenY = centerY + (int)(y - playerY);

    g.setColor(Color.RED);
    g.fillOval(screenX - 6, screenY - 6, 12, 12);
}
}
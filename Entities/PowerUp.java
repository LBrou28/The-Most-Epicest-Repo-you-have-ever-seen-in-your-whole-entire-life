package Entities;

import java.awt.*;

public class PowerUp {
    private double x, y;
    private int width = 20;
    private int height = 20;
    private PowerUpType type;

    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
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

        g.fillOval(screenX, screenY, width, height);
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

package Entities.Enemies;
import Entities.Player;
import Entities.Projectile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
public abstract class Enemy {
    protected double xPos, yPos, damage, health, speed; 

    protected int width, height;
    boolean isAlive;
    BufferedImage enemyImage;
    ArrayList<Projectile> projectiles = new ArrayList<>();
    public Enemy(double xPos, double yPos, double damage, double health) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.damage = damage;
        this.health = health;
    }

    public void update(Player player) {
    double dx = player.x - xPos;
    double dy = player.y - yPos;

    double distance = Math.sqrt(dx * dx + dy * dy);

    if (distance != 0) {
        dx /= distance;
        dy /= distance;

        xPos += dx * speed;
        yPos += dy * speed;
    }
}

    public Enemy() {}
    public void draw(Graphics g, Player player, int panelWidth, int panelHeight) {
    int centerX = panelWidth / 2;
    int centerY = panelHeight / 2;

    int screenX = (int)xPos - player.x + centerX;
    int screenY = (int)yPos - player.y + centerY;

    g.drawImage(enemyImage, screenX, screenY, width, height, null);

    for (int i = 0; i < projectiles.size(); i++) {
        Projectile p = projectiles.get(i);
        p.update();
        p.draw(g, player.x, player.y, centerX, centerY);

        if (p.checkBulletCollision(player)) {
            attack(player);
            projectiles.remove(i);
            i--;
        }
    }
}
    
    public BufferedImage getSprite() {
        return enemyImage;
    }
    public double getX() {
        return xPos;
    }
    public double getY() {
        return yPos;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public abstract void attack(Player player); 
    
    public void removeEnemy(ArrayList<Enemy> enemies, int enemy) {
        enemies.remove(enemy);
    }
    public static void moveEnemy() {
        
    }
   


    public static boolean checkCollision(Enemy enemy, Player player) {

    int padding = 8; //adjust this number

    // Player bounds (smaller hitbox)
    double playerLeft = player.x + padding;
    double playerTop = player.y + padding;
    double playerRight = player.x + player.width - padding;
    double playerBottom = player.y + player.height - padding;

    // Enemy bounds (smaller hitbox)
    double enemyLeft = enemy.getX() + padding;
    double enemyTop = enemy.getY() + padding;
    double enemyRight = enemy.getX() + enemy.getWidth() - padding;
    double enemyBottom = enemy.getY() + enemy.getHeight() - padding;

    boolean overlapX = playerLeft < enemyRight && playerRight > enemyLeft;
    boolean overlapY = playerTop < enemyBottom && playerBottom > enemyTop;

    return overlapX && overlapY;
}
}
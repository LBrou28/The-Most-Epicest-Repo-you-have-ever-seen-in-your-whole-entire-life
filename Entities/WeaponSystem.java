package Entities;
import java.util.ArrayList;

import Entities.Enemies.Enemy;

import java.awt.Graphics;
import java.awt.Rectangle;

public class WeaponSystem {
    private Player player;
    private ArrayList<Projectile> projectiles;
    private int fireDelay = 20;
    private int fireTimer = 0;
    private double bulletDamage = 10.0;


    public WeaponSystem(Player player) {
        this.player = player;
        this.projectiles = new ArrayList<>();
    }
    
    public void update(ArrayList<Enemy> enemies, int screenwidth) {
        
        if (fireTimer > 0) fireTimer--;

        if (fireTimer <= 0 && !enemies.isEmpty()) {
            Enemy target = findClosestEnemy(enemies);
            if(target != null) {
                projectiles.add(new Projectile(player.x, player.y, (int)target.xPos, (int)target.yPos));
                fireTimer = fireDelay;
            }
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            p.update();

            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = enemies.get(j);

                if (p.getBounds().intersects(e.getBounds())) {
                    e.takeDamage(bulletDamage);
                    p.active = false;
                    
                }
            }
        }
    }
}

package Entities.Enemies;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ZynDemon extends Enemy {
    
     
    public ZynDemon() {
        super(5, 5, 5, 5);
        enemyImage = setSprite();
    }

    public void attack() { }
    public BufferedImage setSprite() {
        BufferedImage enemy = null;
        try {
        enemy = ImageIO.read(getClass().getResourceAsStream("EnemyImages/Zyn.jpg"));
        }   catch (IOException e) {
        e.printStackTrace();
        }
        if (enemy == null) {
        System.out.println("Its null");
        }
        return enemy;
    }
    

}
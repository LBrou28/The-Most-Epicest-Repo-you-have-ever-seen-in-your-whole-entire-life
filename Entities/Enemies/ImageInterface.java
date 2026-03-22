package Entities.Enemies;
import Entities.Player;
import Entities.Projectile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
public class ImageInterface {

    public static ArrayList<BufferedImage> sprites = new ArrayList<>();
    public ImageInterface() {
        if (sprites.size() == 0) {
            initalizeImages();
        }
    }
    public void initalizeImages() {
        try {
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/DoubtPhantom.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/demon.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/Loneliness.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/sadghost.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/sadness.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/Witch.png")));
            sprites.add(ImageIO.read(getClass().getResourceAsStream("EnemyImages/SadnessChuck.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
    


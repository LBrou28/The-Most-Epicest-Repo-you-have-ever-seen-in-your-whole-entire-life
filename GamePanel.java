
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Entities.Enemies.Enemy;
import Entities.Enemies.ZynDemon;


public class GamePanel extends JPanel implements Runnable {

    BufferedImage chuck;

    Thread gameThread;
    boolean running;

    public GamePanel() {
    this.setPreferredSize(new Dimension(800, 600));
    this.setBackground(Color.white);
    this.setDoubleBuffered(true);

    try {
        chuck = ImageIO.read(new File("src/assets/Chuck.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void startgame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
public void run() {
    while (running) {
        repaint();
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}

   @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);


    if (chuck != null) {
        g.drawImage(chuck, 100, 100, 32, 64, null);
        Enemy demon = new ZynDemon();
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(demon);
        demon.draw(g, enemies);
    } else{
        System.out.println("yo code trash ma boy");
    }
    
    
}

   
}

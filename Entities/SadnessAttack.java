package Entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SadnessAttack {

    private double x, y;
    private double speed = 2;

    private BufferedImage[] frames;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private int frameDelay = 120; 

    private int damage = 10;
    private boolean active = true;

    public SadnessAttack(double x, double y, BufferedImage spriteSheet) {
        this.x = x;
        this.y = y;


        int frameWidth = spriteSheet.getWidth() / 4;
        int frameHeight = spriteSheet.getHeight();

        frames = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
    }

    public void update() {
        
        y += speed;

        long now = System.currentTimeMillis();
        if (now - lastFrameTime > frameDelay) {
            currentFrame = (currentFrame + 1) % frames.length;
            lastFrameTime = now;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(frames[currentFrame], (int)x, (int)y, null);
    }

    public boolean isActive() {
        return active;
    }

    public int getDamage() {
        return damage;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
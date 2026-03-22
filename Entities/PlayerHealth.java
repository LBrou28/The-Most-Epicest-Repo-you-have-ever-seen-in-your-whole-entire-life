package Entities;

import java.awt.*;

public class PlayerHealth {

    private double maxHealth = 100;
    private double currentHealth;

    private boolean isDead = false;
    private boolean isDashing = false;


    private boolean flashing = false;
    private long flashStartTime = 0;
    private long flashDuration = 100; // milliseconds

    public PlayerHealth(double maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void update(boolean isDashing) {
        this.isDashing = isDashing;

        isDead = currentHealth <= 0;

        if (flashing && System.currentTimeMillis() - flashStartTime > flashDuration) {
            flashing = false;
        }
    }

    public void takeDamage(double damage) {
        if (!isDashing && !isDead) {
            currentHealth -= damage;

            if (currentHealth < 0) {
                currentHealth = 0;
            }

            flash();
        }
    }

    public void heal(double amount) {
        currentHealth += amount;

        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void setMaxHealth(double value) {
        this.maxHealth = value;
        this.currentHealth = value;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getHealth() {
        return currentHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    private void flash() {
        flashing = true;
        flashStartTime = System.currentTimeMillis();
    }

    public void draw(Graphics g) {

        int barX = 20;
        int barY = 20;

        int barWidth = 200;
        int barHeight = 20;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);

        int healthWidth = (int) ((currentHealth / maxHealth) * barWidth);

        float ratio = (float) (currentHealth / maxHealth);
        Color healthColor = new Color(1 - ratio, ratio, 0);

        g.setColor(healthColor);
        g.fillRect(barX, barY, healthWidth, barHeight);

        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);


        if (flashing) {
            g.setColor(new Color(255, 255, 255, 120));
            g.fillRect(barX, barY, barWidth, barHeight);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString((int) currentHealth + " / " + (int) maxHealth, barX + 60, barY + 15);
    }
}
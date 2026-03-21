package Entities;
import Input.InputHandler;
import java.util.ArrayList;


public class Player {
    public int x, y, width = 40, height = 80;
    int speed = 3;
    double dashSpeed = 10;

    ArrayList<Projectile> projectiles;

    boolean isDashing = false;
    long dashTime = 0;
    long dashDuration = 150; // milliseconds

    long lastDashTime = 0;
    long dashCooldown = 5000; // milliseconds

    InputHandler input;

    public Player(InputHandler input, ArrayList<Projectile> projectiles) {
        this.input = input;
        this.projectiles = projectiles;
        x = 100;
        y = 100;
    }

    public void update() {

    double dx = 0;
    double dy = 0;

    if (input.up) dy -= 1;
    if (input.down) dy += 1;
    if (input.left) dx -= 1;
    if (input.right) dx += 1;

    //for projectiles
    if (input.shoot) {
            shoot();
            input.shoot = false; // prevent continuous shooting
        }

    long currentTime = System.currentTimeMillis();

    // START DASH
    if (input.dash && !isDashing && (currentTime - lastDashTime > dashCooldown)) {
        isDashing = true;
        dashTime = currentTime;
        lastDashTime = currentTime;
    }

    // END DASH
    if (isDashing && (currentTime - dashTime > dashDuration)) {
        isDashing = false;
    }

    double currentSpeed = isDashing ? dashSpeed : speed;

    if (dx != 0 || dy != 0) {
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        x += dx * currentSpeed;
        y += dy * currentSpeed;
    }
}

private void shoot() {
        // Spawn projectile at player's position
        projectiles.add(new Projectile(x + 16, y + 16)); 
    }
}
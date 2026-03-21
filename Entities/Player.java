package Entities;
import Input.InputHandler;


public class Player {
    public int x, y;
    int speed = 3;
    double dashSpeed = 10;

    boolean isDashing = false;
    long dashTime = 0;
    long dashDuration = 150; // milliseconds

    long lastDashTime = 0;
    long dashCooldown = 500; // milliseconds

    InputHandler input;

    public Player(InputHandler input) {
        this.input = input;
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
}
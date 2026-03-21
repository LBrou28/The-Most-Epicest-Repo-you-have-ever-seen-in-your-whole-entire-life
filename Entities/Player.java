package Entities;
import Input.InputHandler;

public class Player {
    public int x, y;
    int speed = 3;

    InputHandler input;

    public Player(InputHandler input) {
        this.input = input;
        x = 100;
        y = 100;
    }

    public void update() {
        if (input.up) {
            y -= speed;
        }
        if (input.down) {
            y += speed;
        }
        if (input.left) {
            x -= speed;
        }
        if (input.right) {
            x += speed;
        }
    }
}
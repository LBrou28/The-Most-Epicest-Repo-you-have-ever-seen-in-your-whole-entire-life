package Input;

import java.awt.event.*;

public class InputHandler extends KeyAdapter {
    public boolean up, down, left, right, dash, restart;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> dash = true;
            case KeyEvent.VK_R -> restart = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_SPACE -> dash = false;
            case KeyEvent.VK_R -> restart = false;
        }
    }
}
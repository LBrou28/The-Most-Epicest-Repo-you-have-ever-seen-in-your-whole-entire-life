package Input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InputHandler extends JPanel implements ActionListener, KeyListener {

    private int x = 0, y = 0;
    private int velX = 0, velY = 0;
    private int movementSpeed = 1;
    
    private final int BorderX = 1000;
    private final int BorderY = 1000;


    private enum MovingState {
        NORMAL,
        DASHING
    }

    private MovingState currentState;

    Timer tm = new Timer(5, this);

    public InputHandler() {
        tm.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (x < 0) { 
            velX = 0; x = 0;
        } 
        if (x > BorderX) { 
            velX = 0; x = BorderX; 
        }
        if (y < 0) { 
            velY = 0; y = 0; 
        }
        if (y > BorderY) { 
            velY = 0; y = BorderY; 
        }

        x += velX;
        y += velY;
        
        repaint(); 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (currentState == MovingState.NORMAL) {
            if (c == KeyEvent.VK_A) {
            velX = -5 * movementSpeed;
            velY = 0;
            }
            if (c == KeyEvent.VK_D) {
            velX = 5 * movementSpeed;
            velY = 0;
            }
            if (c == KeyEvent.VK_W) {
            velY = -5 * movementSpeed; 
            velX = 0;
            }
            if (c == KeyEvent.VK_S) {
            velY = 5 * movementSpeed;  
            velX = 0;
            }
        }
        
        if (c == KeyEvent.VK_SPACE) {
            startDash();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        velX = 0;
        velY = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void startDash() {
    currentState = MovingState.DASHING;
    
    velX *= 4; 
    velY *= 4;

    Timer dashDuration = new Timer(200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentState = MovingState.NORMAL;
            velX /= 4; 
            velY /= 4;
            ((Timer)e.getSource()).stop(); 
        }
    });

    dashDuration.setRepeats(false);
    dashDuration.start();
    }   

}

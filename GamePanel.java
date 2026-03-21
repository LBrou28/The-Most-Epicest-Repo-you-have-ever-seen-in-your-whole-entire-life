
import Input.InputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    BufferedImage chuck;

    Thread gameThread;
    boolean running;

    private InputHandler input;

    int chuckX = 100;
    int chuckY = 100;
    int speed = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

        input = new InputHandler();
        this.addKeyListener(input);
        this.setFocusable(true);

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

            if (input.up) {
                chuckY -= speed;
            }
            if (input.down) {
                chuckY += speed;
            }
            if (input.left) {
                chuckX -= speed;
            }
            if (input.right) {
                chuckX += speed;
            }

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
            g.drawImage(chuck, chuckX, chuckY, 32, 64, null);
        }
        else{
        System.out.println("yo code trash ma boy");
    }
}
}
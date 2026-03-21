
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {

    BufferedImage chuck;

    Thread gameThread;
    boolean running;

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
     try{
        chuck = ImageIO.read(getClass().getResourceAsStream("/assets/Chuck.png"));
     }catch (IOException e) {
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
            // game loop code here
        }
    }

   
}

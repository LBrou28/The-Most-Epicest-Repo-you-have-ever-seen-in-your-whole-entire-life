
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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

    }
    
}

   @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);


    if (chuck != null) {
        g.drawImage(chuck, 100, 100, 32, 64, null);
    }
    else{
        System.out.println("yo code trash ma boy");
    }
}

   
}


import java.awt.Color;
import java.awt.Dimesion;

import java.swing.JPanel;

public class Gamepanel extends Jpanel implements Runnable{
    //screen
    final int originalTileSize=16;
    final int scale=3;

    final int tileSize=originalTileSize*scale; //48x48 tile
    final int maxScreenCol =16;
    final int maxScreenRow =12;
    final int screenWidth = tileSize * maxScreenCol //768pixels
    final int screenHeight =tileSize*maxScreenRow; //576 pixels

    Thread gameThread;

    public Gamepanel(){

        this.setPreferredSize();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

    }

    public void startgame(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }
    @Override
    public void run(){

        
    }



}
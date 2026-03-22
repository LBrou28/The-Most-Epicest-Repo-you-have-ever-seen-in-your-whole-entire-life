import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class StartPanel extends JPanel {

    private BufferedImage backgroundImage;

    // Audio clips
    private Clip backgroundMusic;
    private Clip startButtonSound;
    private Clip helpButtonSound;

    private boolean musicMuted = false;

    public StartPanel(JFrame frame) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // CHANGE THIS PLS
        /*try {
            backgroundImage = ImageIO.read(new File("src/assets/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // audio
        backgroundMusic = loadClip("src/assets/retro-streets.wav");
        startButtonSound = loadClip("src/assets/ChuckMotiv.wav");
        helpButtonSound = loadClip("src/assets/button.wav");

        // loops bg music
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }

        // Start Game button
        JButton chuckStartButton = new JButton("Start Game");
        chuckStartButton.addActionListener((ActionEvent e) -> {
            playClip(startButtonSound);           // play start button sound
            if (backgroundMusic != null) {
                backgroundMusic.stop();           // stop background music
            }
            startButtonSound.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    AudioManager music = new AudioManager();
                    music.playMusic("src/assets/rockintro.wav", "src/assets/rockloop.wav");
                }
            });
            GamePanel gamePanel = new GamePanel();
            frame.setContentPane(gamePanel);
            frame.revalidate();
            gamePanel.requestFocusInWindow();
            gamePanel.startgame();
        });
        this.add(chuckStartButton, gbc);

        // Help button
        gbc.gridy = 1;
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener((ActionEvent e) -> {
            playClip(helpButtonSound);            // play help sound
            String instructions = "Instructions:\n" +
                    "- Use arrow keys or WASD to move.\n" +
                    "- Press SPACE or click to shoot.\n" +
                    "- Press SHIFT to dash (cooldown applies).\n" +
                    "- Avoid enemies and projectiles.\n" +
                    "- Survive as long as you can!";
            JOptionPane.showMessageDialog(this, instructions, "How to Play", JOptionPane.INFORMATION_MESSAGE);
        });
        this.add(helpButton, gbc);

        // Music mute button
        gbc.gridy = 2;
        JButton musicButton = new JButton("Mute Music");
        musicButton.addActionListener((ActionEvent e) -> {
            if (backgroundMusic == null) return;

            if (!musicMuted) {
                backgroundMusic.stop();
                musicButton.setText("Unmute Music");
            } else {
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                musicButton.setText("Mute Music");
            }
            musicMuted = !musicMuted;
        });
        this.add(musicButton, gbc);
    }

    // Paint background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    // Helper for sound clip
    private Clip loadClip(String path) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // H play a sound effect once
    private void playClip(Clip clip) {
        if (clip == null) return;
        clip.stop();          // stop if currently playing
        clip.setFramePosition(0); // rewind
        clip.start();
    }
}

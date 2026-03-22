import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {

    private static Clip introClip;
    private static Clip loopClip;
    private static Clip clip;
    private static Clip warning;

    public static void playMusic(String introPath, String loopPath) {
        try {
            introClip = AudioSystem.getClip();
            introClip.open(AudioSystem.getAudioInputStream(new File(introPath)));

            loopClip = AudioSystem.getClip();
            loopClip.open(AudioSystem.getAudioInputStream(new File(loopPath)));

            introClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    loopClip.setFramePosition(0);
                    loopClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            });

            introClip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //play for boss music
    public static void playClip(String path) {
        try {
            warning = AudioSystem.getClip();
            warning.open(AudioSystem.getAudioInputStream(new File(path)));
            warning.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playBossMusic(String path) {
        stopMusic();
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));

            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void stopMusic() {
        if (introClip != null) introClip.stop();
        if (loopClip != null) loopClip.stop();
    }
}
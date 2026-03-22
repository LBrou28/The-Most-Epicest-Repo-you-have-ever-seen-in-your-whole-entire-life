import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {

    private Clip introClip;
    private Clip loopClip;

    public void playMusic(String introPath, String loopPath) {
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

    public void stopMusic() {
        if (introClip != null) introClip.stop();
        if (loopClip != null) loopClip.stop();
    }
}
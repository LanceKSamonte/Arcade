import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tetris_Sound {
    private List<Clip> clips = new ArrayList<>();
    private URL[] url = new URL[10];
    private Clip musicClip; // Declare the musicClip here

    public Tetris_Sound() {
        url[0] = getClass().getResource("/games/Tetris.wav");
        url[1] = getClass().getResource("/games/delete line.wav");
        url[2] = getClass().getResource("/games/gameover.wav");
        url[3] = getClass().getResource("/games/rotation.wav");
        url[4] = getClass().getResource("/games/touch floor.wav");
        // Initialize other URLs if necessary
    }

    public void play(int i, boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();
            clips.add(clip); // Add the new clip to the list

            if (music) {
                if (musicClip != null) {
                    musicClip.close(); // Close the previous music clip if exists
                }
                musicClip = clip; // Assign the new music clip
            }

            clip.open(ais);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    clips.remove(clip); // Remove the clip from the list when it's stopped
                }
            });
            ais.close();
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        // Stop and close all clips
        for (Clip clip : clips) {
            clip.stop();
            clip.close();
        }
        clips.clear(); // Clear the list after stopping all clips

        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null; // Clear the musicClip after stopping
        }
    }
}

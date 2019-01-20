package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private AudioInputStream audioInputStream;
    private Clip clip;
    public Music(String path) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException uae) {
            System.out.println(uae + "\nDieser Dateityp wird nicht unterstützt.");
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (LineUnavailableException lua) {
            System.out.println(lua);
        }
    }

    public void stop(){
        clip.stop();
    }
}
package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {

    public Music(String path) {
        try {
            AudioInputStream GOTCat = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(GOTCat);
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
}
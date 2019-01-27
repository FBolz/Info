package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    //Referenzen
    private AudioInputStream audioInputStream;
    private Clip clip;

    /**
     * Konstruktor
     * @param path, der Musik welche abgespielt werden soll
     */
    public Music(String path) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException uae) {
            System.out.println(uae + "\nDieser Dateityp wird nicht unterstützt.");
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (LineUnavailableException lua) {
            System.out.println(lua);
        }
    }
    //Methode um die Musik zu stoppen
    public void stop(){
        clip.stop();
    }
    //Methode, um die Musik zu loopen
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
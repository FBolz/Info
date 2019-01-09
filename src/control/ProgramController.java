package control;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.Display;
import akkgframework.control.fundamental.SoundController;
import akkgframework.model.scenario.ScenarioController;
import model.Player;
import model.Projectile;

import java.awt.event.KeyEvent;


/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private double programTimer;

    // Referenzen
    private UIController uiController;  // diese Referenz soll auf ein Objekt der Klasse uiController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Display programmZeitAnzeige;
    private SoundController soundController;
    private Player firstPlayer;
    private Player secondPlayer;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse UIController. Diese wird als Parameter übergeben.
     * @param uiController das UIController-Objekt des Programms
     */
    public ProgramController(UIController uiController){
        this.uiController = uiController;
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     */
    public void startProgram() {
        programTimer = 0;
        // ******************************************* Ab hier euer eigener Code! *******************************************
      //  Projectile projectile = new Projectile(300,400,"right",uiController);
       // uiController.registerObject(projectile);
        firstPlayer = new Player(uiController, KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_ENTER,"assets/images/objects/gate.png",600,100);
        uiController.registerObject(firstPlayer);
        secondPlayer = new Player(uiController, KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_Q,"assets/images/objects/gate.png",100,100);
        uiController.registerObject(secondPlayer);


    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt){
        programTimer += dt;
        if(firstPlayer.getShoot()){
            Projectile projectile = new Projectile(firstPlayer.getX(),firstPlayer.getY(),"right",uiController);
            uiController.registerObject(projectile);
        }
        if(secondPlayer.getShoot()){
            Projectile projectile = new Projectile(secondPlayer.getX(),secondPlayer.getY(),"right",uiController);
            uiController.registerObject(projectile);
        }
        // ******************************************* Ab hier euer eigener Code! *******************************************

    }

}

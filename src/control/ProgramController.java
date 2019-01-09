package control;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.Display;
import akkgframework.control.fundamental.SoundController;
import akkgframework.model.abitur.datenstrukturen.Stack;
import akkgframework.model.abitur.datenstrukturen.List;
import akkgframework.model.scenario.ScenarioController;
import model.Player;
import model.Projectile;
import model.item;

import java.awt.event.KeyEvent;


/**
 * Ein Objekt der Klasse ProgramController dient dazu das Programm zu steuern. Die updateProgram - Methode wird
 * mit jeder Frame im laufenden Programm aufgerufen.
 */
public class ProgramController {

    //Attribute
    private double programTimer;
    private double projectileTimer1;
    private double projectileTimer2;

    // Referenzen
    private UIController uiController;  // diese Referenz soll auf ein Objekt der Klasse uiController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Display programmZeitAnzeige;
    private SoundController soundController;
    private Player firstPlayer;
    private Player secondPlayer;
    private Stack<item> colorStack;
    private List<Projectile> projectileListP1;
    private List<Projectile> projectileListP2;

    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse UIController. Diese wird als Parameter übergeben.
     * @param uiController das UIController-Objekt des Programms
     */
    public ProgramController(UIController uiController){
        this.uiController = uiController;
        colorStack= new Stack<item>();
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     */
    public void startProgram() {
        programTimer = 0;
        // ******************************************* Ab hier euer eigener Code! *******************************************

        firstPlayer = new Player(uiController, KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_ENTER,"assets/images/objects/gate.png",600,100,3);
        uiController.registerObject(firstPlayer);
        secondPlayer = new Player(uiController, KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_Q,"assets/images/objects/gate.png",100,100,3);
        uiController.registerObject(secondPlayer);
        projectileTimer2=0;
        projectileTimer1=0;
        projectileListP1 = new List();
        projectileListP2 = new List();
    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt){
        programTimer += dt;

        // ******************************************* Ab hier euer eigener Code! *******************************************
        projectileTimer1-=dt;
        projectileTimer2 -=dt;
        if(projectileTimer1<=0&& firstPlayer.getShoot()) {
            shoot(dt, firstPlayer, projectileListP1);
            projectileTimer1=1;
        }
        if(projectileTimer2<=0&& secondPlayer.getShoot()) {
            shoot(dt, secondPlayer, projectileListP2);
            projectileTimer2 = 1;
        }
        checkAndHandleCollisionPlayers(projectileListP2,firstPlayer);
        checkAndHandleCollisionPlayers(projectileListP1,secondPlayer);
        if(firstPlayer.collidesWith(secondPlayer)){
            firstPlayer.setCollision(true);
            secondPlayer.setCollision(true);
        }else{
            firstPlayer.setCollision(false);
            secondPlayer.setCollision(false);
        }

    }

    private void shoot(double dt,Player player,List<Projectile> projectileList){
            projectileList.append(new Projectile(player.getX(),player.getY(),"right",uiController));
            projectileList.toLast();
            uiController.registerObject(projectileList.getContent());
    }


    public void checkAndHandleCollisionPlayers(List<Projectile> projectileList,Player player) {
        if (!projectileList.isEmpty()) {
            projectileList.toFirst();
            while (projectileList.hasAccess()) {
                if (projectileList.getContent().collidesWith(player)) {
                    uiController.removeObject(projectileList.getContent());
                    projectileList.remove();
                    player.setLive(player.getLive() - 1);
                }
                projectileList.next();
            }
        }
    }

}

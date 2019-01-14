package control;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.Display;
import akkgframework.control.fundamental.SoundController;
import akkgframework.model.abitur.datenstrukturen.Queue;
import akkgframework.model.abitur.datenstrukturen.Stack;
import akkgframework.model.abitur.datenstrukturen.List;
import akkgframework.model.scenario.ScenarioController;
import model.*;

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
    private double powerUpTimer;

    // Referenzen
    private UIController uiController;  // diese Referenz soll auf ein Objekt der Klasse uiController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Display programmZeitAnzeige;
    private SoundController soundController;
    private Player firstPlayer;
    private Player secondPlayer;
    private Stack<Item> colorStack;
    private Stack <Item> collectStack;
    private Item item1,item2,item3,item4,item5;
    private List<Projectile> projectileListP1;
    private List<Projectile> projectileListP2;
    private Queue<PowerUp> powerUpQueue;
    private PowerUp activePowerUp;
    private boolean powerUpIsActive;
    private Jumba jumba;
    private double enemyTimer;


    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse UIController. Diese wird als Parameter übergeben.
     * @param uiController das UIController-Objekt des Programms
     */
    public ProgramController(UIController uiController){
        this.uiController = uiController;
        colorStack = new Stack<Item>();
    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     */
    public void startProgram() {
        programTimer = 0;
        // ******************************************* Ab hier euer eigener Code! *******************************************
        jumba = new Jumba();
        uiController.drawObjectOnPanel(jumba,0);

        firstPlayer = new Player(uiController, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, 100, "assets/images/objects/gate.png", 600, 100, 3,"left");
        uiController.registerObject(firstPlayer);
        secondPlayer = new Player(uiController, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, 100, "assets/images/objects/gate.png", 100, 100, 3,"right");
        uiController.registerObject(secondPlayer);
        projectileTimer2 = 0;
        projectileTimer1 = 0;
        powerUpTimer = 0;
        projectileListP1 = new List();
        projectileListP2 = new List();
        powerUpQueue = new Queue<>();
        createPowerUpQueue(10);
        powerUpIsActive = false;
        colorStack= new Stack<>();
        colorStack= new Stack<>();
        item1= new Item(1);
        item2= new Item(2);
        item3= new Item(3);
        item4= new Item(4);
        item5= new Item(5);

    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt) {
        programTimer += dt;

        // ******************************************* Ab hier euer eigener Code! *******************************************
        if (firstPlayer.getFastShoot()) {
            projectileTimer1 -= dt*2;
        }else {
            projectileTimer1 -= dt;
        }
        if (secondPlayer.getFastShoot()) {
            projectileTimer2 -= dt*2;
        }else {
            projectileTimer2 -= dt;
        }

        powerUpTimer -= dt;


        if (projectileTimer1 <= 0 && firstPlayer.getShoot()) {
            shoot(dt, firstPlayer, projectileListP1);
            projectileTimer1 = 1;
        }
        if (projectileTimer2 <= 0 && secondPlayer.getShoot()) {
            shoot(dt, secondPlayer, projectileListP2);
            projectileTimer2 = 1;
        }
        checkAndHandleCollisionPlayers(projectileListP2, firstPlayer);
        checkAndHandleCollisionPlayers(projectileListP1, secondPlayer);
        if (firstPlayer.collidesWith(secondPlayer)) {
            firstPlayer.setCollision(true);
            secondPlayer.setCollision(true);
            if(firstPlayer.getDirection().equals(secondPlayer.getDirection())){
                firstPlayer.setDirection("neutral");
            }
        }else {
            firstPlayer.setCollision(false);
            secondPlayer.setCollision(false);
        }
        checkAndHandleCollisionPlayerItem(item1,firstPlayer);
        checkAndHandleCollisionPlayerItem(item2,firstPlayer);
        checkAndHandleCollisionPlayerItem(item3,firstPlayer);
        checkAndHandleCollisionPlayerItem(item4,firstPlayer);
        checkAndHandleCollisionPlayerItem(item5,firstPlayer);
        checkAndHandleCollisionPlayerItem(item1,secondPlayer);
        checkAndHandleCollisionPlayerItem(item2,secondPlayer);
        checkAndHandleCollisionPlayerItem(item3,secondPlayer);
        checkAndHandleCollisionPlayerItem(item4,secondPlayer);
        checkAndHandleCollisionPlayerItem(item5,secondPlayer);

        checkAndHandleEnemyCollisions(jumba,firstPlayer);
        checkAndHandleEnemyCollisions(jumba,secondPlayer);

        checkAndHandleCollisionEnemy(projectileListP1,jumba);
        checkAndHandleCollisionEnemy(projectileListP2, jumba);



        if (powerUpTimer <= 0 && !powerUpIsActive) {
            spawnPowerUp();
        }

        if (activePowerUp != null) {
            checkAndHandlePowerUpCollisions(activePowerUp, firstPlayer);
            checkAndHandlePowerUpCollisions(activePowerUp, secondPlayer);
        }
        if (powerUpQueue.isEmpty()){
            createPowerUpQueue(10);
        }
    }


    private void spawnPowerUp() {
        powerUpTimer = 1;
        activePowerUp = powerUpQueue.front();
        uiController.registerObject(activePowerUp);
        powerUpQueue.dequeue();
        powerUpIsActive = true;
    }

    private void createPowerUpQueue(int amount) {
        double randomNumber;
        for (int i = 0; i < amount; i++) {
            randomNumber = Math.random() * 10;
            if (randomNumber == 0 || randomNumber <= 5) {
                powerUpQueue.enqueue(new PowerUp("Speed"));
            } else if (randomNumber <= 8) {
                powerUpQueue.enqueue(new PowerUp("Shoot"));
            } else if (randomNumber <= 10) {
                powerUpQueue.enqueue(new PowerUp("Live"));
            }
        }

    }
    private void checkAndHandleEnemyCollisions(Enemy enemy, Player player) {
        if (enemy.collidesWith(player)&& enemy.isEnemyIsActive()) {
            player.setLive(player.getLive()-1);
            enemy.setEnemyIsActive(false);
            spawnEnemyRandom(enemy);
        }

    }
    public void checkAndHandleCollisionEnemy(List<Projectile> projectileList, Enemy enemy) {
        if (!projectileList.isEmpty()) {
            projectileList.toFirst();
            while (projectileList.hasAccess()) {
                if (projectileList.getContent().collidesWith(enemy)) {
                    uiController.removeObject(projectileList.getContent());
                    projectileList.remove();
                    enemy.setEnemyIsActive(false);
                    spawnEnemyRandom(enemy);
                }
                projectileList.next();
            }
        }
    }

    private void spawnEnemyRandom(Enemy enemy){
       int i = (int) (Math.random()*1400);
       int y = (int) (Math.random()*1000);
        enemy.setX(i);
        enemy.setY(y);
        enemy.setEnemyIsActive(true);
    }

    private void checkAndHandlePowerUpCollisions(PowerUp activePowerUp, Player player) {
        if (activePowerUp.collidesWith(player)) {
            switch (activePowerUp.getType()) {
                case "Speed":
                    player.setSpeed(player.getSpeed() + 40);
                    break;
                case "Shoot":
                    player.setFastShoot(true);
                    break;
                case "Live":
                    player.setLive(player.getLive() + 1);
                    break;
                default:
                    break;
            }
            uiController.removeObject(activePowerUp);
            powerUpIsActive = false;
            powerUpTimer = 0;
        }

    }

    private void shoot(double dt, Player player, List<Projectile> projectileList) {
        projectileList.append(new Projectile(player.getX(), player.getY(), player.getFacing(), uiController));
        projectileList.toLast();
        uiController.registerObject(projectileList.getContent());
    }


    public void checkAndHandleCollisionPlayers(List<Projectile> projectileList, Player player) {
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
    public void checkAndHandleCollisionPlayerItem(Item item,Player player){
       /* if (player.collidesWith(item)) {
            collectStack.push(item);

        }*/
    }

}

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
    private boolean powerUpIsActive;
    private double enemyTimer;
    private boolean started;
    // Referenzen
    private UIController uiController;  // diese Referenz soll auf ein Objekt der Klasse uiController zeigen. Über dieses Objekt wird das Fenster gesteuert.
    private Display programmZeitAnzeige;
    private SoundController soundController;
    private Player firstPlayer;
    private Player secondPlayer;
    private Stack<Item> collectStack1;
    private Stack<Item> collectStack2;
    private Item[] item;
    private Item[] itemShow;
    private Start start;
    private List<Projectile> projectileListP1;
    private List<Projectile> projectileListP2;
    private Queue<PowerUp> powerUpQueue;
    private PowerUp activePowerUp;
    private Music music;
    private Options options;
    private Jumba jumba;
    private Background bck;
    private Follower follower;
    private String musicPath;
    private MusicSelection musicSelection;
    private LifeSelection lifeSelection;
    private End end;
    private Enemy[][] enemies;
    private Follower[] followers;
    private Jumba[] jumbas;
    private Gunner[] gunners;
    private Projectile pro, pro2, pro3;
    private Music effect;


    /**
     * Konstruktor
     * Dieser legt das Objekt der Klasse ProgramController an, das den Programmfluss steuert.
     * Damit der ProgramController auf das Fenster zugreifen kann, benötigt er eine Referenz auf das Objekt
     * der Klasse UIController. Diese wird als Parameter übergeben.
     *
     * @param uiController das UIController-Objekt des Programms
     */
    public ProgramController(UIController uiController) {
        this.uiController = uiController;

    }

    /**
     * Diese Methode wird genau ein mal nach Programmstart aufgerufen.
     */
    public void startProgram() {
        programTimer = 0;
        // ******************************************* Ab hier euer eigener Code! *******************************************
        bck = new Background();
        bck.setBackgorund(1);
        uiController.registerObject(bck);

        start = new Start();
        uiController.registerObject(start);
        firstPlayer = new Player(uiController, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, 100, 1400, 500, 3, "left", "Player 2");
        secondPlayer = new Player(uiController, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, 100, 100, 500, 3, "right", "Player 1");
        projectileTimer2 = 0;
        projectileTimer1 = 0;
        powerUpTimer = 0;
        projectileListP1 = new List();
        projectileListP2 = new List();
        powerUpQueue = new Queue<>();
        createPowerUpQueue(10);
        powerUpIsActive = false;
        collectStack1 = new Stack<>();
        collectStack2 = new Stack<>();
        item = new Item[5];
        follower = new Follower();
        follower.setTarget(firstPlayer);
        musicPath = "assets/sounds/music/spacetime.wav";

        followers = new Follower[2];
        jumbas = new Jumba[5];
        gunners = new Gunner[3];

        enemies = new Enemy[3][];
        enemies[0] = followers;
        enemies[1] = jumbas;
        enemies[2] = gunners;


    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     *
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt) {
        programTimer += dt;

        // ******************************************* Ab hier euer eigener Code! *******************************************
        gameMode();
        if (start.getClicked() == "standby") {
            if (firstPlayer.getFastShoot()) {
                projectileTimer1 -= dt * 3;
            } else {
                projectileTimer1 -= dt;
            }
            if (secondPlayer.getFastShoot()) {
                projectileTimer2 -= dt * 3;
            } else {
                projectileTimer2 -= dt;
            }
            managePowerUpTimer(firstPlayer, dt);
            managePowerUpTimer(secondPlayer, dt);
            if (projectileTimer1 <= 0 && firstPlayer.getShoot()) {
                //Sound effects obtained from https://www.zapsplat.com
                effect = new Music("assets/sounds/rpg-effects/battle/laser.wav");
                shoot(firstPlayer, projectileListP1);
                projectileTimer1 = 1;
            }
            if (projectileTimer2 <= 0 && secondPlayer.getShoot()) {
                //Sound effects obtained from https://www.zapsplat.com
                effect = new Music("assets/sounds/rpg-effects/battle/laser.wav");
                shoot(secondPlayer, projectileListP2);
                projectileTimer2 = 1;
            }
            checkAndHandleCollisionPlayers(projectileListP2, firstPlayer, secondPlayer);
            checkAndHandleCollisionPlayers(projectileListP1, secondPlayer, firstPlayer);
            if (firstPlayer.collidesWith(secondPlayer)) {
                firstPlayer.setCollision(true);
                secondPlayer.setCollision(true);
                firstPlayer.setLive(firstPlayer.getLive() - 1);
                secondPlayer.setLive(secondPlayer.getLive() - 1);
                if (firstPlayer.getDirection().equals(secondPlayer.getDirection())) {
                    firstPlayer.setDirection("neutral");
                }
            } else {
                firstPlayer.setCollision(false);
                secondPlayer.setCollision(false);
            }
            for (int i = 0; i < item.length; i++) {
                checkAndHandleCollisionPlayerItem(item[i], firstPlayer, collectStack1);
            }
            for (int j = 0; j < item.length; j++) {
                checkAndHandleCollisionPlayerItem(item[j], secondPlayer, collectStack2);
            }

            if (uiController.isKeyDown(KeyEvent.VK_M)) {
                if (!collectStack1.isEmpty()) {
                    System.out.println("m pressed, popping stack1");
                    //for(int i=0; i<item.length&& !collectStack1.isEmpty();i++){
                    uiController.registerObject(collectStack1.top());
                    collectStack1.top().jump();
                    System.out.println(" popping stack1");
                    collectStack1.pop();
                }
            }
            if (uiController.isKeyDown(KeyEvent.VK_Y)) {
                if (!collectStack2.isEmpty()) {
                    System.out.println("y pressed, popping stack2");
                    //for(int i=0; i<item.length&& !collectStack1.isEmpty();i++){
                    uiController.registerObject(collectStack2.top());
                    collectStack2.top().jump();
                    collectStack2.pop();
                }
            }

            checkAndHandleEnemyCollisions(firstPlayer);
            checkAndHandleEnemyCollisions(secondPlayer);

            checkAndHandleCollisionEnemy(projectileListP1);
            checkAndHandleCollisionEnemy(projectileListP2);

            checkAndHandleCollisionPlayer(firstPlayer);
            checkAndHandleCollisionPlayer(secondPlayer);


            if (powerUpTimer <= 0) {
                spawnPowerUp();
            }

            if (activePowerUp != null) {
                checkAndHandlePowerUpCollisions(firstPlayer);
                checkAndHandlePowerUpCollisions(secondPlayer);
            }
            if (powerUpQueue.isEmpty()) {
                createPowerUpQueue(10);
            }
        }
    }

    /**
     * Verwaltet den PowerUpTimer des übergebenen Spielers.
     * Wenn der PowerupTimer des Spielers größer als 0 ist
     * wird er herunter gezählt und wenn er 0 ist werden alle
     * Effekte des Spielers entfernt.
     *
     * @param player Spieler von dem der PowerUp Timer verwaltet werden soll
     * @param dt     Gibt die vergangene Zeit in Sekunden seit dem letzten Aufruf an.
     */
    private void managePowerUpTimer(Player player, double dt) {
        if (player.getPowerUpTimer() > 0) {
            double timer = player.getPowerUpTimer();
            timer -= dt;
            player.setPowerUpTimer(timer);
        } else if (player.getPowerUpTimer() <= 0) {
            player.setFastShoot(false);
            player.setStrongShoot(false);
            player.setInvert(false);
        }
    }

    /**
     * Spawnt ein PowerUp.
     * Das vorderste PowerUp in der PowerUpQueue wird zum aktivem
     * PowerUp,checkStartPosition wird für beide Spieler aufgerufen
     * und das aktive PowerUp wird zum uiController hinzugefügt.
     */

    private void spawnPowerUp() {
        powerUpTimer = 1;
        activePowerUp = powerUpQueue.front();
        powerUpQueue.dequeue();
        checkStartPosition(firstPlayer);
        checkStartPosition(secondPlayer);
        uiController.registerObject(activePowerUp);
    }

    /**
     * Überprüft ob beim spawnen des PowerUps das PowerUp mit dem Spieler
     * kollidiert.Wenn dies der Fall ist werden die Koordinaten des aktivem
     * PowerUps erneut zufällig geändert.
     *
     * @param player Spieler mit dem die Kollision geprüft wird.
     */
    private void checkStartPosition(Player player) {
        if (activePowerUp.collidesWith(player)) {
            activePowerUp.jump();
        }
    }

    /**
     * Füllt die PowerUpQueue mit der übergebenen Anzahl an PowerUps.
     * Wobei die Art der PowerUps mit der von 0 bis 10 zufällig gesetzen Variable randomNumber
     * bestimmt wird.
     * <p>
     * randomNumber=0-2:Speed Powerup das beim Aufsammeln die Geschwindigkeit
     * des Spielers ändert.
     * <p>
     * randomNumber=3-4:Invert Powerup das beim Aufsammeln die Bewegungstasten
     * des Spielers vertauscht.
     * <p>
     * randomNumber=5-6:StrongShoot Powerup das beim Aufsammeln die Schüsse
     * des Spielers verstärkt.
     * <p>
     * randomNumber=7-8:FastShoot Powerup das beim Aufsammeln die Zeit zwischen den
     * Schüssen des Spielers verkürzt.
     * <p>
     * randomNumber=9-10:Live Powerup das beim Aufsammeln die Leben des Spielers
     * um eins vergrößert.
     *
     * @param amount Anzahl an PowerUps die erstellt werden sollen.
     */
    private void createPowerUpQueue(int amount) {
        double randomNumber;
        for (int i = 0; i < amount; i++) {
            randomNumber = Math.random() * 10;
            if (randomNumber <= 2) {
                powerUpQueue.enqueue(new PowerUp("Speed"));
            } else if (randomNumber <= 4) {
                powerUpQueue.enqueue(new PowerUp("Invert"));
            } else if (randomNumber <= 6) {
                powerUpQueue.enqueue(new PowerUp("StrongShoot"));
            } else if (randomNumber <= 8) {
                powerUpQueue.enqueue(new PowerUp("FastShoot"));
            } else if (randomNumber <= 10) {
                powerUpQueue.enqueue(new PowerUp("Live"));
            }
        }

    }

    private void checkAndHandleEnemyCollisions(Player player) {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                if (enemies[i][j].collidesWith(player) && enemies[i][j].isEnemyIsActive()) {
                    player.setLive(player.getLive() - 1);
                    enemies[i][j].setEnemyIsActive(false);
                    spawnEnemyRandom(enemies[i][j]);
                }
            }


        }

    }

    public void checkAndHandleCollisionPlayer(Player player) {
        for (int i = 0; i < gunners.length; i++) {
            if (gunners[i].getProjectile() != null) {
                if (player.collidesWith(gunners[i].getProjectile()) && gunners[i].getProjectile().isActive()) {
                    player.setLive(player.getLive() - 1);
                    uiController.removeObject(gunners[i].getProjectile());
                    gunners[i].getProjectile().setActive(false);
                }
            }
        }
    }


    public void checkAndHandleCollisionEnemy(List<Projectile> projectileList) {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                if (!projectileList.isEmpty()) {
                    projectileList.toFirst();
                    while (projectileList.hasAccess()) {
                        if (projectileList.getContent().collidesWith(enemies[i][j])) {
                            uiController.removeObject(projectileList.getContent());
                            projectileList.remove();
                            enemies[i][j].setEnemyIsActive(false);
                            spawnEnemyRandom(enemies[i][j]);
                        }
                        projectileList.next();
                    }
                }
            }
        }
    }

    private void spawnEnemyRandom(Enemy enemy) {
        int i = (int) (Math.random() * 1400);
        int y = (int) (Math.random() * 700);
        enemy.setX(i);
        enemy.setY(y);
        enemy.setEnemyIsActive(true);
    }

    /**
     * Überprüft ob der übergebene Spieler mit dem aktivem PowerUp kollidiert.
     * Wenn dies der Fall ist wird das aktive PowerUp entfernt und je nach
     * PowerUp type (Art) passiert etwas anderes.
     *
     * type=Speed:Die Geschwindigkeit des Spielers wird um 40 vergrößert.
     *
     * type=FastShoot:Die Zeit zwischen den Schüssen des Spielers wird verkürzt und der
     * PowerUpTimer des Spielers wird gestartet(auf 20 gesetzt).
     *
     * type=Live:Dem Spieler wird ein Leben hinzugefügt.
     *
     * type=StrongShoot:Die Schüsse des Spielers werden verstärktund der
     * PowerUpTimer des Spielers wird gestartet(auf 20 gesetzt).
     *
     * type=Invert:Die Bewegungstasten des Spielers werden vertauscht und der
     * PowerUpTimer des Spielers wird gestartet(auf 20 gesetzt).
     *
     * @param player Spieler bei dem die Kollision mit den aktiven PowerUp geprüft werden soll
     */
    private void checkAndHandlePowerUpCollisions(Player player) {
        if (player.getPowerUpTimer() <= 0) {
            if (activePowerUp.collidesWith(player)) {
                switch (activePowerUp.getType()) {
                    case "Speed":
                        player.setSpeed(player.getSpeed() + 40);
                        break;
                    case "FastShoot":
                        player.setFastShoot(true);
                        player.setPowerUpTimer(20);
                        break;
                    case "Live":
                        player.setLive(player.getLive() + 1);
                        break;
                    case "StrongShoot":
                        player.setStrongShoot(true);
                        player.setPowerUpTimer(20);
                        break;
                    case "Invert":
                        player.setInvert(true);
                        player.setPowerUpTimer(20);
                        break;
                    default:
                        break;
                }
                uiController.removeObject(activePowerUp);
                powerUpTimer = 0;
            }
        }

    }

    private void shoot(Player player, List<Projectile> projectileList) {
        projectileList.append(new Projectile(player.getX(), player.getY(), player.getFacing(), uiController));
        projectileList.toLast();
        uiController.registerObject(projectileList.getContent());
    }


    public void checkAndHandleCollisionPlayers(List<Projectile> projectileList, Player player, Player otherPlayer) {
        if (!projectileList.isEmpty()) {
            projectileList.toFirst();
            while (projectileList.hasAccess()) {
                if (projectileList.getContent().collidesWith(player)) {
                    uiController.removeObject(projectileList.getContent());
                    projectileList.remove();
                    if (!otherPlayer.getStrongShoot()) {
                        player.setLive(player.getLive() - 1);
                    } else {
                        player.setLive(player.getLive() - 2);
                    }
                }
                projectileList.next();
            }
        }
    }


    public void checkAndHandleCollisionPlayerItem(Item item1, Player player, Stack<Item> collectStack) {
        if (player.collidesWith(item1)) {
            System.out.println("ja");
            collectStack.push(item1);
            item1.jumpOut();
            uiController.removeObject(item1);

            System.out.println("" + collectStack.top());
            if (5 == item1.getColorNumber()) {
                Stack<Item> temp = new Stack<>();
                boolean compare = true;
                for (int i = item.length - 1; i >= 0 && !collectStack.isEmpty(); i--) {
                    if (i + 1 == collectStack.top().getColorNumber()) {

                        System.out.println("true" + item[i].getColorNumber());

                        temp.push(collectStack.top());
                        collectStack.pop();
                    } else {
                        compare = false;
                        System.out.println("false" + item[i].getColorNumber());
                        temp.push(collectStack.top());
                        collectStack.pop();
                    }
                }
                while (!temp.isEmpty()) {
                    collectStack.push(temp.top());
                    temp.pop();
                }
                if (compare == true && !collectStack.isEmpty()) {
                    int life = player.getLive() + 15;
                    player.setLive(life);
                    for (int i = 0; i < item.length; i++) {
                        collectStack.pop();
                        uiController.registerObject(item[i]);
                        item[i].jump();
                    }
                } else {
                    if (!collectStack.isEmpty()) {
                        for (int i = 0; i < item.length; i++) {
                            collectStack.pop();
                            uiController.registerObject(item[i]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Methode die ale Schüsse aus der Liste vom Bildschirm entfernt
     *
     * @param projectileList, die geleert werden soll
     */
    private void removeShoot(List<Projectile> projectileList) {
        //Abfrage, ob die Liste leer ist
        if (!projectileList.isEmpty()) {
            //Liste wird von vorne durch gegangen
            projectileList.toFirst();
            //Bedingung, dass etwas durchgeführt wird solange man nicht durch die Liste durch ist
            while (projectileList.hasAccess()) {
                //Aktuelles Objekt der Liste wird entfernt
                uiController.removeObject(projectileList.getContent());
                //Nächstes Objekt der Liste wird angesprochen
                projectileList.next();
            }
        }
    }

    /**
     * Die Werte des übergebenen Spielers werden zurückgesetzt
     *
     * @param player, wessen Werte zurückgesetzt werden sollen
     **/
    private void restartGame(Player player) {
        //Speed des Players wird auf 100 gesetzt
        player.setSpeed(100);
        //Boolean, ob die Player kollidieren wird auf flase gesetzt
        player.setCollision(false);
        //Powerup Timer wird in den Ausgangs Zustand gesetzt
        player.setPowerUpTimer(0);
        //Powerups werden zurückgesetzt
        player.setFastShoot(false);
        player.setInvert(false);
        player.setStrongShoot(false);
    }

    /**
     * Methode, welche die verschiedenen Modis kontrolliert
     */
    private void gameMode() {
        //Klick auf den "Start" Knopf
        if (start.getClicked() == "start") {
            //Verhinderung eines Doppelklicks
            if (!started) {
                bck.setBackgorund(2);
                // Die Richtung des ersten Spielers, in die der Spieler am Anfang des Spiels guckt, wird auf links gesetzt
                firstPlayer.setDirection("left");
                //Die Startkoordinaten des ersten Spielers werden gesetzt
                firstPlayer.setX(1400);
                firstPlayer.setY(500);
                // Die Richtung des zweiten Spielers, in die der Spieler am Anfang des Spiels guckt, wird auf links gesetzt
                secondPlayer.setDirection("right");
                //Die Startkoordinaten des zweiten Spielers werden gesetzt
                secondPlayer.setX(100);
                secondPlayer.setY(500);
                //Zeichnen von Powerups
                powerUpTimer = 0;
                uiController.registerObject(activePowerUp);
                //Zeichnen der Spieler
                uiController.registerObject(firstPlayer);
                uiController.registerObject(secondPlayer);
                //Zeichnen der Planeten
                for (int i = 0; i < item.length; i++) {
                    item[i] = new Item(i + 1, firstPlayer, secondPlayer);
                    uiController.registerObject(item[i]);
                }
                itemShow = new Item[5];
                for (int i = 0; i < itemShow.length; i++) {
                    int distance = 40;
                    itemShow[i] = new Item(i + 1, firstPlayer, secondPlayer);
                    itemShow[i].setY(10);
                    itemShow[i].setX(1350 + i * distance);
                    itemShow[i].setHeight(15);
                    itemShow[i].setWidth(15);
                    itemShow[i].setJump(false);
                    uiController.registerObject(itemShow[i]);
                }
                //Zeichnen der Gegner
                spawn();
                // Abspielen von Musik
                music = new Music(musicPath);
                //Loopen der Musik
                music.loop();
                //Verhinderung eines Doppelklicks
                started = true;
                //Entfernen des Start Bildschirms
                uiController.removeObject(start);
            }
            //Switchen des "Gamemodes" zu Standby damit das Spiel gestartet wird
            start.setClicked("standby");
            //Überprüfung, ob auf "Options" gedrückt wurde
        } else if (start.getClicked() == "menu") {
            //Erstellen und zeichen eines neuen Option Bildschirm
            options = new Options();
            bck.setBackgorund(2);
            uiController.registerObject(options);
            //Entfernen des Start Bildschirm
            uiController.removeObject(start);
            //"Gamemode" wird auf options gesetzt
            start.setClicked("options");
            //Überprüfung, ob auf "back" gedrückt wurde
        } else if (start.getClicked() == "options" && options.getClicked() == "back") {
            bck.setBackgorund(1);
            //Start Bildschirm wird gezeichnet
            uiController.registerObject(start);
            //Menü Bildschirm wird gezeichnet
            uiController.removeObject(options);
            //Klick Überprüfung von Start und Options werden in den Ausgangs Zustand gesetzt
            start.setClicked("null");
            options.setClicked("null");
            //Überprüfung, ob auf den "Music" Knopf gedrückt wurde
        } else if (start.getClicked() == "options" && options.getClicked() == "music") {
            //Erstellung einer neuen Musik Auswahl
            musicSelection = new MusicSelection();
            //Menü Bildschirm wird entfernt
            uiController.removeObject(options);
            //Musik Auswahl Bildschirm wird gezeichnet
            uiController.registerObject(musicSelection);
            //"Options Modus" wird auf "musicSelection" gesetzt, um zu überprüfen, wo im Menü man gerade ist
            options.setClicked("musicSelection");
            // Überprüfung, ob man aus der Musik Auswahl rausgehen will
        } else if (start.getClicked() == "options" && options.getClicked() == "musicSelection" && musicSelection.getClicked() == "back") {
            //Menü Bildschirm wird gezeichnet
            uiController.registerObject(options);
            // Musik Auswahl Bildschirm wird entfernt
            uiController.removeObject(musicSelection);
            //Klick Überprüfung von MusicSelection und Options werden in den Ausgangs Zustand gesetzt
            musicSelection.setClicked("null");
            options.setClicked("null");
        }
        //Überprüfung, ob auf irgendeinen Titel gedrückt wurde
        else if (start.getClicked() == "options" && options.getClicked() == "musicSelection" && musicSelection.getClicked() != null) {
            //Pfad der Musik die abgespielt wird, wird auf den Pfad gesetzt von der Musik, welche ausgewählt wurde
            musicPath = "assets/sounds/music/" + musicSelection.getClicked();
            //Man wird aus der Auswahl "rausgeworfen"
            musicSelection.setClicked("back");
        }
        //Überprüfung, ob auf den "Life" Knopf gedrückt wurde
        else if (start.getClicked() == "options" && options.getClicked() == "life") {
            //Erstellung einer neuen Lebens Auswahl
            lifeSelection = new LifeSelection();
            //Menü Bildschirm wird entfernt
            uiController.removeObject(options);
            //Musik Auswahl Bildschirm wird gezeichnet
            uiController.registerObject(lifeSelection);
            //"Options Modus" wird auf "lifeSelection" gesetzt, um zu überprüfen, wo im Menü man gerade ist
            options.setClicked("lifeSelection");
        }
        //Überprüfung, ob man auf den "back" Knopf gedrückt hat in der Lebens Auswahl
        else if (start.getClicked() == "options" && options.getClicked() == "lifeSelection" && lifeSelection.getClicked() == -1) {
            //Menü Bildschirm wird wieder gezeichnet
            uiController.registerObject(options);
            //Lebens Auswahl Bildschirm wird entfernt
            uiController.removeObject(lifeSelection);
            //Klick Überprüfung von LifeSelection und Options werden in den Ausgangs Zustand gesetzt
            lifeSelection.setClicked(0);
            options.setClicked("null");
            //Überprüfung, ob man auf eine Lebens Anzahl gedrückt hat
        } else if (start.getClicked() == "options" && options.getClicked() == "lifeSelection" && lifeSelection.getClicked() > 0) {
            //Leben beider Spieler werden auf die Anzahl der Leben gesetzt, welche ausgewählt wurde
            firstPlayer.setLive(lifeSelection.getClicked());
            secondPlayer.setLive(lifeSelection.getClicked());
            //Man wird aus dem Auswahl Bildschirm "rausgeworfen"
            lifeSelection.setClicked(-1);
        }
        //Überprüfung, ob das Spiel vorbei ist befindet
        else if (start.getClicked() == "endscreen") {
            //Endscreen wird gezeichnet
            uiController.registerObject(end);
            // Leben beider Spieler werden auf 3 gesetzt
            firstPlayer.setLive(3);
            secondPlayer.setLive(3);
            //"Gamemode" wird auf "end" gesetzt
            start.setClicked("end");
            //Überprüfung, ob man das Spiel neu starten möchte
        } else if (start.getClicked() == "end" && end.getClicked() == "restart") {
            //Endscreen wird entfernt
            uiController.removeObject(end);
            //Start Bildschirm wird gezeichnet
            uiController.registerObject(start);
            //Die Methode restartGame wird für beide Spieler aufgerufen, um die Werte der Spieler zurück zu setzen
            restartGame(firstPlayer);
            restartGame(secondPlayer);
            //Background wird auf den Start Bildschirm gewechselt
            bck.setBackgorund(1);
            //Klick Überprüfung des Start und des End Bildschirm werden auf irgendwas gesetzt, was nicht irgendwo anders abgefragt wird
            start.setClicked("restarted");
            end.setClicked("restarted");
        }
        //Überprüfung, ob irgendein Spieler keine Leben mehr hat
        if (firstPlayer.getLive() <= 0 || secondPlayer.getLive() <= 0) {
            //Attribut started wird auf false gesetzt damit man das Spiel wieder starten kann
            started = false;
            //Spieler werden entfernt
            uiController.removeObject(firstPlayer);
            uiController.removeObject(secondPlayer);
            //Gegner werden entfernt
            despawn();
            //Planeten werden entfernt
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < item.length; i++) {
                    uiController.removeObject(item[i]);
                }
                for (int i = 0; i < itemShow.length; i++) {
                    uiController.removeObject(itemShow[i]);
                }
            }
            //Musik wird gestoppt
            music.stop();
            //"Gamemode" wird auf "endscreen" gesetzt, damit dieser gezeichnet werden kann
            start.setClicked("endscreen");
            //Background wird geändert
            bck.setBackgorund(3);
            // Powerup wird entfernt
            uiController.removeObject(activePowerUp);
            //Schüsse der Spieler werden entfernt
            removeShoot(projectileListP1);
            removeShoot(projectileListP2);
            //Attribut activePowerUp wird auf null gesetzt, sodass es kein aktuelles Powerup gibt
            activePowerUp = null;
            //Abfrage, ob der zweite Spieler keine Leben mehr hat
            if (secondPlayer.getLive() <= 0) {
                //Neuer Endscreen wird erstellt, welcher den Namen des Siegers übergeben bekommt, damit dieser ausgegeben werden kann
                end = new End(firstPlayer.getName());
            }
            //Abfrage, ob der zweite Spieler keine Leben mehr hat
            if (firstPlayer.getLive() <= 0) {
                //Neuer Endscreen wird erstellt, welcher den Namen des Siegers übergeben bekommt, damit dieser ausgegeben werden kann
                end = new End(secondPlayer.getName());
            }

        }
    }

    public void setTarget(Follower follower, int i) {

        if (i == 1) {
            follower.setTarget(firstPlayer);
        } else {
            follower.setTarget(secondPlayer);
        }
    }

    public void spawn() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                if (i == 0) {
                    enemies[i][j] = new Follower();
                    enemies[i][j].setX(Math.random() * 1400 + 100);
                    enemies[i][j].setY(Math.random() * 900 + 50);
                    setTarget((Follower) enemies[i][j], j);
                    uiController.registerObject(enemies[i][j]);
                }
                if (i == 1) {
                    enemies[i][j] = new Jumba();
                    enemies[i][j].setX(Math.random() * 1400 + 100);
                    enemies[i][j].setY(Math.random() * 900 + 50);
                    uiController.registerObject(enemies[i][j]);
                }
                if (i == 2) {
                    switch (j) {
                        case 0:
                            enemies[i][j] = new Gunner(uiController, pro);
                            break;
                        case 1:
                            enemies[i][j] = new Gunner(uiController, pro2);
                            break;
                        case 2:
                            enemies[i][j] = new Gunner(uiController, pro3);
                            break;
                    }
                    enemies[i][j].setX(Math.random() * 1400 + 100);
                    enemies[i][j].setY(Math.random() * 700 + 50);
                    uiController.registerObject(enemies[i][j]);
                }
            }
        }

    }

    public void despawn() {
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                uiController.removeObject(enemies[i][j]);
            }
        }
    }
}
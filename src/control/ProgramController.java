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
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt) {
        programTimer += dt;

        // ******************************************* Ab hier euer eigener Code! *******************************************
        //Die Methode gameMode wird dauerhaft aufgerufen
        gameMode();
        //Nur wenn der "Gamemode" "Standby" ist wird alles ausgeführt
        if(start.getClicked()== "standby") {
            //Wenn der erste Spieler das Powerup für einen schnelleren Schuss einsammelt vergeht die Zeit dreimal so schnell
            if (firstPlayer.getFastShoot()) {
                projectileTimer1 -= dt * 3;
            } else {
                //Sonst wird immer eine Sekunde vom Timer abgezogen
                projectileTimer1 -= dt;
            }
            //Wenn der zweite Spieler das Powerup für einen schnelleren Schuss einsammelt vergeht die Zeit dreimal so schnell
            if (secondPlayer.getFastShoot()) {
                projectileTimer2 -= dt * 3;
            } else {
                //Sonst wird immer eine Sekunde vom Timer abgezogen
                projectileTimer2 -= dt;
            }
            managePowerUpTimer(firstPlayer, dt);
            managePowerUpTimer(secondPlayer, dt);
            if (powerUpTimer < 0) {
                powerUpTimer -= dt;
            }
            //Wenn der Timer für die Projectiles null beträgt oder null unterschreitet und die Taste für den ersten Spieler für den Schuus gedrückt wurde passiert etwas
            if (projectileTimer1 <= 0 && firstPlayer.getShoot()) {
                //Sound effects obtained from https://www.zapsplat.com
                //Neue Musik wird erstellt für den Sound effect
                effect = new Music("assets/sounds/rpg-effects/battle/laser.wav");
                //Die Methode shoot wird aufgerufen der erste Spieler und seine Liste werden übergeben
                shoot(firstPlayer, projectileListP1);
                //Der Timer wird auf 1 gesetzt
                projectileTimer1 = 1;
            }
            //Wenn der Timer für die Projectiles null beträgt oder null unterschreitet und die Taste für den ersten Spieler für den Schuus gedrückt wurde passiert etwas
            if (projectileTimer2 <= 0 && secondPlayer.getShoot()) {
                //Sound effects obtained from https://www.zapsplat.com
                //Neue Musik wird erstellt für den Sound effect
                effect = new Music("assets/sounds/rpg-effects/battle/laser.wav");
                //Die Methode shoot wird aufgerufen der zweite Spieler und seine Liste werden übergeben
                shoot(secondPlayer, projectileListP2);
                //Der Timer wird auf 1 gesetzt
                projectileTimer2 = 1;
            }
            //Die Methoden zur Überprüfung für die Kollision der Spieler mit den gegnerischen Schüssen wird für beide Spieler mit ihren Listen aufgerufen
            checkAndHandleCollisionPlayers(projectileListP2, firstPlayer, secondPlayer);
            checkAndHandleCollisionPlayers(projectileListP1, secondPlayer, firstPlayer);
            //Wenn die Spieler kollidieren verlieren beide ein Leben und die boolean Werte für die Kollision werden für beide Spieler auf true gesetzt
            if (firstPlayer.collidesWith(secondPlayer)) {
                firstPlayer.setCollision(true);
                secondPlayer.setCollision(true);
                firstPlayer.setLive(firstPlayer.getLive() - 1);
                secondPlayer.setLive(secondPlayer.getLive() - 1);
                //Wenn beide in die gleiche Richtung fliegen und trotzdem kollidieren wird die Richtung des einen Spielers auf "neutral" gesetzt, damit verhindert wird, dass ein Spieler aus dem Bildschirm "fliegt"
                if (firstPlayer.getDirection().equals(secondPlayer.getDirection())) {
                    firstPlayer.setDirection("neutral");
                }
                //Falls sie nicht kollidieren werden die boolean Werte für die Kollision für beide Spieler auf false gesetzt
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
            //Die Methode zur Überprüfung, ob ein Gegner den Spieler berührt, wird für beide Spieler ausgeführt
            checkAndHandleEnemyCollisions(firstPlayer);
            checkAndHandleEnemyCollisions(secondPlayer);
            //Die Methode zur Überprüfung, ob ein Schuss des Spielers einen Gegner berührt, wird für beide Spieler ausgeführt
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

    /**
     * Überprüft die Kollision zwischen einem Schuss und einem Gegner
     * @param projectileList, welche durchgegangen werden soll, um zu überprüfen, ob ein Schuss einen Gegner trifft
     */
    public void checkAndHandleCollisionEnemy (List < Projectile > projectileList) {
            //Alle Gegnertypen werden durchgegangen
            for (int i = 0; i < enemies.length; i++) {
                //Alle Gegner des Gegnertyps werden durchgegangen
                for (int j = 0; j < enemies[i].length; j++) {
                    //Abfrage, ob die Liste Objekte hat
                    if (!projectileList.isEmpty()) {
                        //Erstes Objekt der Liste wird angesprochen
                        projectileList.toFirst();
                        //So lange die Liste ein aktuelles Objekt hat wird etwas durchgeführt
                        while (projectileList.hasAccess()) {
                            //Wenn das aktuelle Objekt der Liste mit einem Gegner kollidiert passiert etwas
                            if (projectileList.getContent().collidesWith(enemies[i][j])) {
                                //Der Schuss, der mit dem Gegner kollidiert wird vom Bildschirm entfernt
                                uiController.removeObject(projectileList.getContent());
                                //Der Schuss, der mit dem Gegner kollidiert wird aus der Liste entfernt
                                projectileList.remove();
                                //Der Gegner wird kurzzeitig auf inaktiv gesetzt
                                enemies[i][j].setEnemyIsActive(false);
                                //Der Gegner wird irgendwo zufällig auf dem Bildschirm platziert
                                spawnEnemyRandom(enemies[i][j]);
                            }
                            //Das nächste Objekt der Liste wird angesprochen
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

    /**
     * Lässt einen Spieler mit Hilfe einer Liste schießen
     * @param player, welcher schießt
     * @param projectileList, des Spielers der geschossen hat
     */
    private void shoot( Player player, List<Projectile> projectileList) {
       //Fügt ein Objekt der Klasse Projectile hinten an die Liste an mit den Koordinaten des Spielers und der Richtung in die der Spieler guckt
        projectileList.append(new Projectile(player.getX(), player.getY(), player.getFacing(), uiController));
        //Das letzte Objekt der Liste wird angesprochen
        projectileList.toLast();
        //Das aktuelle Objekt wird gezeichnet
        uiController.registerObject(projectileList.getContent());
    }

    /**
     * Überprüft, ob ein Spieler von einem Schuss des anderen Spielers getroffen wurde
     * @param projectileList, welche überprüft werden soll
     * @param player, welcher überprüft werden soll, ob er abgeschossen wurde
     * @param otherPlayer, welcher geschossen hat
     */
    public void checkAndHandleCollisionPlayers(List<Projectile> projectileList, Player player, Player otherPlayer) {
        //Abfrage, ob die Liste ein Objekt hat
        if (!projectileList.isEmpty()) {
            //Die Liste wird von vorne angesprochen
            projectileList.toFirst();
            //Bedingung, dass etwas durchgeführt wird solange man nicht durch die Liste durch ist
            while (projectileList.hasAccess()) {
                //Wenn der Spieler mit einem Objekt der Liste kollidiert, wird etwas ausgeführt
                if (projectileList.getContent().collidesWith(player)) {
                    //Das aktuelle Objekt der Liste wird vom Bildschirm entfernt
                    uiController.removeObject(projectileList.getContent());
                    //Das aktuelle Objekt der Liste wird aus der Liste entfernt
                    projectileList.remove();
                    //Überprüfung, ob der Spieler der schießt das Powerup eingesammelt hat, welches ihm einen stärkeren Schuss gibt
                    if (!otherPlayer.getStrongShoot()) {
                        //Wenn der Spieler das Powerup nicht eingesammelt hat, wird dem anderem Spieler ein Leben abgezogen
                        player.setLive(player.getLive() - 1);
                    } else {
                        //Wenn der Spieler das Powerup eingesammelt hat, verliert der andere Spieler zwei Leben
                        player.setLive(player.getLive() - 2);
                    }
                }
                //Das nächste Objekt der Liste wird angesprochen
                projectileList.next();
            }
        }
    }

    /**
     * überprüft ob ein Spieler mit einem der Planeten (Items) kollidiert
     * wenn dies passiert wird das item auf den Stapel des Spielers gelegt
     * wenn das blaue Item eingesammelt wurde(Colornumber 5), weil es das letzte in der richtigen Reihenfolge ist,
     * wird überprüft ob man die Items in der richtigen Reihenfolge eingesammelt hat.
     * Vom Stack in einen temporären Stack und wieder zurück
     * nur wenn 5 mal true also wenn die Reihenfolge richtig ist bekommt der Spieler 15 Leben
     *Anschließend werden die Items aus dem Stack entfernt und man kann von vorne anfangen um wieder 15 Leben zu bekommen
     * das aber auch wenn man falsch eingesammelt hat
     * @param item1 item das man gerade einsammelt
     * @param player der Spieler, der gerade mit dem Item kollidiert
     * @param collectStack stack in die die Items gesammelt werden, bei Spieler 1 collectStack1 usw.
     */
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
    private void removeShoot (List < Projectile > projectileList) {
        //Abfrage, ob die Liste ein Objekt hat
        if (!projectileList.isEmpty()) {
            //Liste wird von vorne durch gegangen
            projectileList.toFirst();
            //Bedingung, dass etwas durchgeführt wird solange man nicht durch die Liste durch ist
            while (projectileList.hasAccess()) {
                //Aktuelles Objekt der Liste wird entfernt
                uiController.removeObject(projectileList.getContent());
                //Das aktuelle Objekt der Liste wird aus der Liste entfernt
                projectileList.remove();
            }
        }
    }

            /**
             * Die Werte des übergebenen Spielers werden zurückgesetzt
             *
             * @param player, wessen Werte zurückgesetzt werden sollen
             **/
            private void restartGame (Player player){
                //Speed des Players wird auf 100 gesetzt
                player.setSpeed(100);
                //Boolean, ob die Player kollidieren wird auf flase gesetzt
                player.setCollision(false);
                //Powerup Timer wird in den Ausgangs Zustand gesetzt
                player.setPowerUpTimer(-1);
                //Powerups werden zurückgesetzt
                player.setFastShoot(false);
                player.setInvert(false);
                player.setStrongShoot(false);
                //Schuss abfrage wird auf false gesetzt
                player.setShoot(false);
            }

            /**
             * Methode, welche die verschiedenen Modis kontrolliert
             */
            private void gameMode () {
                //Klick auf den "Start" Knopf
                if (start.getClicked() == "start") {
                    //Verhinderung eines Doppelklicks
                    if (!started) {
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
                        //Pfad der Musik wird auf den Pfad von Spacetime gesetzt
                        musicPath = "assets/sounds/music/spacetime.wav";
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
                    uiController.registerObject(options);
                    //Entfernen des Start Bildschirm
                    uiController.removeObject(start);
                    //"Gamemode" wird auf options gesetzt
                    start.setClicked("options");
                    //Überprüfung, ob auf "back" gedrückt wurde
                } else if (start.getClicked() == "options" && options.getClicked() == "back") {
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
                    //Die Richtung der Spieler werden in den Ausgangs Zustand gesetzt
                    firstPlayer.setDirection("left");
                    secondPlayer.setDirection("right");
                    firstPlayer.setFacing("left");
                    secondPlayer.setFacing("right");
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
                    bck.setBackgorund(2);
                    // Powerup wird entfernt
                    uiController.removeObject(activePowerUp);
                    //Schüsse der Spieler werden entfernt
                    removeShoot(projectileListP1);
                    removeShoot(projectileListP2);
                    //Schüsse des Gunners werden entfernt
                    for (int i = 0; i < gunners.length; i++) {
                        uiController.removeObject(gunners[i].getProjectile());
                        gunners[i].getProjectile().setActive(false);
                    }
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

    public void setTarget(Follower follower, int i){

        if (i == 1) {
            follower.setTarget(firstPlayer);
        } else {
            follower.setTarget(secondPlayer);
        }
    }

    public void spawn(){
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

    public void despawn(){
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                uiController.removeObject(enemies[i][j]);
            }
        }
    }
}
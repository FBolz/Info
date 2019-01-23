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
    private Gunner gunners;



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
        uiController.registerObject(bck);

        start = new Start();
        uiController.registerObject(start);
        firstPlayer = new Player(uiController, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, 100,1400, 500, 3, "left","Player 2");
        secondPlayer = new Player(uiController, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_Q, 100,100, 500, 3, "right","Player 1");
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
        musicPath = "assets/sounds/music/spacetime2.wav";

        followers = new Follower[2];
        jumbas = new Jumba[5];

        enemies = new Enemy[2][];
        enemies[0] = followers;
        enemies[1] = jumbas;

    }

    /**
     * Diese Methode wird wiederholt automatisch aufgerufen und zwar für jede Frame einmal, d.h. über 25 mal pro Sekunde.
     * @param dt Die Zeit in Sekunden, die seit dem letzten Aufruf der Methode vergangen ist.
     */
    public void updateProgram(double dt) {
        programTimer += dt;

        // ******************************************* Ab hier euer eigener Code! *******************************************
        gameMode();
        if(start.getClicked()== "standby") {
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
            if (powerUpTimer < 0) {
                powerUpTimer -= dt;
            }

            if (projectileTimer1 <= 0 && firstPlayer.getShoot()) {
                shoot(firstPlayer, projectileListP1);
                projectileTimer1 = 1;
            }
            if (projectileTimer2 <= 0 && secondPlayer.getShoot()) {
                shoot(secondPlayer, projectileListP2);
                projectileTimer2 = 1;
            }
            checkAndHandleCollisionPlayers(projectileListP2, firstPlayer, secondPlayer);
            checkAndHandleCollisionPlayers(projectileListP1, secondPlayer, firstPlayer);
            if (firstPlayer.collidesWith(secondPlayer)) {
                firstPlayer.setCollision(true);
                secondPlayer.setCollision(true);
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
                if(!collectStack1.isEmpty()){
                System.out.println("m pressed, popping stack1");
                //for(int i=0; i<item.length&& !collectStack1.isEmpty();i++){
                uiController.registerObject(collectStack1.top());
                collectStack1.top().jump();
                    System.out.println(" popping stack1");
                collectStack1.pop();
                }
            }
            if (uiController.isKeyDown(KeyEvent.VK_Y)) {
                if(!collectStack2.isEmpty()){
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


            if (powerUpTimer <= 0) {
                spawnPowerUp();
            }

            if (activePowerUp != null) {
                checkAndHandlePowerUpCollisions(activePowerUp, firstPlayer);
                checkAndHandlePowerUpCollisions(activePowerUp, secondPlayer);
            }
            if (powerUpQueue.isEmpty()) {
                createPowerUpQueue(10);
            }
        }
    }

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


    private void spawnPowerUp() {
        powerUpTimer = 1;
        activePowerUp = powerUpQueue.front();
        uiController.registerObject(activePowerUp);
        powerUpQueue.dequeue();
    }

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

    private void checkAndHandleEnemyCollisions( Player player) {
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


        public void checkAndHandleCollisionEnemy (List < Projectile > projectileList) {
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

        private void spawnEnemyRandom (Enemy enemy){
            int i = (int) (Math.random() * 1400);
            int y = (int) (Math.random() * 1000);
            enemy.setX(i);
            enemy.setY(y);
            enemy.setEnemyIsActive(true);
        }

    private void checkAndHandlePowerUpCollisions(PowerUp activePowerUp, Player player) {
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

    private void shoot( Player player, List<Projectile> projectileList) {
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
                if (compare == true&& !collectStack.isEmpty()) {
                    int life = player.getLive() + 15;
                    player.setLive(life);
                    for (int i = 0; i < item.length; i++) {
                        collectStack.pop();
                        uiController.registerObject(item[i]);
                    }
                } else {
                    if(!collectStack.isEmpty()) {
                        for (int i = 0; i < item.length; i++) {
                            collectStack.pop();
                            uiController.registerObject(item[i]);
                        }
                    }
                }
            }
        }
    }

    private void removeShoot(List<Projectile> projectileList){
        if(!projectileList.isEmpty()){
            projectileList.toFirst();
            while(projectileList.hasAccess()){
                uiController.removeObject(projectileList.getContent());
                projectileList.next();
            }
        }
    }

    private void restartGame(Player player){
        player.setLive(3);
        player.setSpeed(100);
        player.setCollision(false);
        player.setPowerUpTimer(-1);
        player.setFastShoot(false);
        player.setInvert(false);
        player.setStrongShoot(false);
    }

    private void gameMode(){
        if(start.getClicked()=="start"){
            powerUpTimer=0;
            uiController.registerObject(activePowerUp);
            uiController.registerObject(firstPlayer);
            uiController.registerObject(secondPlayer);
            music = new Music(musicPath);
            for (int i = 0; i < item.length; i++) {
                item[i] = new Item(i + 1,firstPlayer,secondPlayer);
                uiController.registerObject(item[i]);
            }
            itemShow= new Item[5];
            for(int i=0; i< itemShow.length;i++){
                int distance= 40;
                itemShow[i]= new Item(i+1,firstPlayer,secondPlayer);
                itemShow[i].setY(10);
                itemShow[i].setX(1350+i*distance);
                itemShow[i].setHeight(15);
                itemShow[i].setWidth(15);
                itemShow[i].setJump(false);
                uiController.registerObject(itemShow[i]);
            }
            spawn();
            start.setClicked("standby");
            uiController.removeObject(start);
        }else if(start.getClicked()== "menu") {
            options = new Options();
            uiController.registerObject(options);
            uiController.removeObject(start);
            start.setClicked("options");
        }else if(start.getClicked()=="options" && options.getClicked()== "back") {
            uiController.registerObject(start);
            uiController.removeObject(options);
            start.setClicked("null");
            options.setClicked("null");
        }else if(start.getClicked() == "options" && options.getClicked()== "music"){
            musicSelection = new MusicSelection();
            uiController.removeObject(options);
            uiController.registerObject(musicSelection);
            options.setClicked("musicSelection");
        }else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "back") {
            uiController.registerObject(options);
            uiController.removeObject(musicSelection);
            musicSelection.setClicked("null");
            options.setClicked("null");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "flags") {
            musicPath= "assets/sounds/music/flags.wav";
            musicSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "doomed") {
            musicPath= "assets/sounds/music/doomed.wav";
            musicSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "great") {
            musicPath= "assets/sounds/music/greatMissions.wav";
            musicSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "battle") {
            musicPath= "assets/sounds/music/battleThemeA.wav";
            musicSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="musicSelection" && musicSelection.getClicked()== "space") {
            musicPath= "assets/sounds/music/spacetime2.wav";
            musicSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()== "life"){
            lifeSelection = new LifeSelection();
            uiController.removeObject(options);
            uiController.registerObject(lifeSelection);
            options.setClicked("lifeSelection");
        }else if(start.getClicked() == "options" && options.getClicked()=="lifeSelection" && lifeSelection.getClicked()=="back") {
            uiController.registerObject(options);
            uiController.removeObject(lifeSelection);
            musicSelection.setClicked("null");
            options.setClicked("null");
        }else if(start.getClicked() == "options" && options.getClicked()=="lifeSelection" && lifeSelection.getClicked()=="10") {
            firstPlayer.setLive(10);
            secondPlayer.setLive(10);
            lifeSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="lifeSelection" && lifeSelection.getClicked()=="5") {
            firstPlayer.setLive(5);
            secondPlayer.setLive(5);
            lifeSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="lifeSelection" && lifeSelection.getClicked()=="4") {
            firstPlayer.setLive(4);
            secondPlayer.setLive(4);
            lifeSelection.setClicked("back");
        }
        else if(start.getClicked() == "options" && options.getClicked()=="lifeSelection" && lifeSelection.getClicked()=="3") {
            firstPlayer.setLive(3);
            secondPlayer.setLive(3);
            lifeSelection.setClicked("back");
        }else if(start.getClicked()=="endscreen"){
            uiController.registerObject(end);
            firstPlayer.setLive(1);
            secondPlayer.setLive(1);
            start.setClicked("end");
        }else if(start.getClicked()=="end" && end.getClicked()=="restart"){
            uiController.removeObject(end);
            uiController.registerObject(start);
            restartGame(firstPlayer);
            restartGame(secondPlayer);
            firstPlayer.setDirection("left");
            firstPlayer.setX(1400);
            firstPlayer.setY(500);
            secondPlayer.setDirection("right");
            secondPlayer.setX(100);
            secondPlayer.setY(500);
            start.setClicked("restarted");
            end.setClicked("restarted");
        }
        if(firstPlayer.getLive()<=0|| secondPlayer.getLive()<=0){
            uiController.removeObject(firstPlayer);
            uiController.removeObject(secondPlayer);
            despawn();
            for (int i = 0; i < item.length; i++) {
                uiController.removeObject(item[i]);
            }
            for(int i=0; i< itemShow.length;i++){
                uiController.removeObject(itemShow[i]);
            }
            music.stop();
            start.setClicked("endscreen");
            uiController.removeObject(activePowerUp);
            removeShoot(projectileListP1);
            removeShoot(projectileListP2);
            activePowerUp=null;
            if(secondPlayer.getLive()<=0){
                end = new End(firstPlayer.getName());
            }
            if(firstPlayer.getLive()<=0){
                end = new End(secondPlayer.getName());
            }

        }
    }
    public void setTarget(Follower follower, int i){

        if(i == 1){
            follower.setTarget(firstPlayer);
        }else{
            follower.setTarget(secondPlayer);
        }
    }
    public void spawn(){
        for(int i = 0; i < enemies.length; i++){
            for(int j = 0; j < enemies[i].length;j++ ){
                if(i == 0){
                    enemies[i][j] = new Follower();
                    enemies[i][j].setX(Math.random()*1400 + 100);
                    enemies[i][j].setY(Math.random()* 900 + 50);
                    setTarget((Follower)enemies[i][j],j);
                    uiController.registerObject(enemies[i][j]);
                }
                if(i == 1){
                    enemies[i][j] = new Jumba();
                    enemies[i][j].setX(Math.random()*1400 + 100);
                    enemies[i][j].setY(Math.random()* 900 + 50);
                    uiController.registerObject(enemies[i][j]);
                }
                if(i == 2){
                    enemies[i][j] = new Gunner();
                    enemies[i][j].setX(Math.random()*1400 + 100);
                    enemies[i][j].setY(Math.random()* 900 + 50);
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
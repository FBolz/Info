package akkgframework.model.scenario;

import control.Config;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.model.scenarioexample.Bauer;
import akkgframework.model.scenarioexample.Burg;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Repräsentiert eine Spielzone (ein Level) mit allen Objekten, Größe ist immer 1024x1024.
 */
public class ZoneController extends GraphicalObject{


    //Attribute
    private double timer;
    private int levelToLoad;

    private final double SPAWN_DELAY = 0.5;

    //Referenzen
    private ScenarioController scnControl;
    private ZoneLoader zoneLoader;
    private ScenarioTerrain[][] scenarioTerrain;
    private ArrayList<ScenarioObject> objects;
    private ArrayList<ScenarioRessource> scenarioRessources;
    private ArrayList<ScenarioCharacter> characters;

    private Burg theBurg;

    public ZoneController(ScenarioController scenarioController){
        levelToLoad = 0;
        scnControl = scenarioController;
        zoneLoader = new ZoneLoader();
        objects = new ArrayList<>();
        scenarioRessources = new ArrayList<>();
        characters = new ArrayList<>();
        scenarioTerrain = new ScenarioTerrain[0][0];
        scnControl.getUiControl().registerObject(this);
    }

    public void loadLevel(int level){
        levelToLoad = level;
    }

    public void addPeasant(SimplePeasant sp){
        sp.setX(theBurg.getSpawnCoordinates().x);
        sp.setY(theBurg.getSpawnCoordinates().y);
        characters.add(sp);
        scnControl.getUiControl().registerObject(sp);
    }

    public ScenarioRessource getFirstRessourceAt(int x, int y, int radius){
        ScenarioRessource result = null;
        Iterator<ScenarioRessource> iterator = scenarioRessources.iterator();
        while(iterator.hasNext() && result == null){
            ScenarioRessource tmp = iterator.next();
            if (Math.sqrt( Math.pow(tmp.getX()-x,2) + Math.pow(tmp.getY()-y,2) ) <= radius) result = tmp;
        }
        if (Config.DEBUG){
            if (result != null) System.out.println("DEBUG-INFO: Found Ressource "+result.getType().name()+" at x: "+result.getX()+" and y: "+result.getY());
        }
        return result;
    }

    public ScenarioObject getFirstObjectAt(int x, int y, int radius){
        ScenarioObject result = null;
        Iterator<ScenarioObject> iterator = objects.iterator();
        while(iterator.hasNext() && result == null){
            ScenarioObject tmp = iterator.next();
            if (Math.sqrt( Math.pow(tmp.getX()-x,2) + Math.pow(tmp.getY()-y,2) ) <= radius) result = tmp;
        }
        if (Config.DEBUG){
            if (result != null) System.out.println("DEBUG-INFO: Found Object "+result.getType().name()+" at x: "+result.getX()+" and y: "+result.getY());
        }
        return result;
    }

    public ScenarioCharacter getFirstCharacterAt(int x, int y, int radius){
        ScenarioCharacter result = null;
        Iterator<ScenarioCharacter> iterator = characters.iterator();
        while(iterator.hasNext() && result == null){
            ScenarioCharacter tmp = iterator.next();
            if (Math.sqrt( Math.pow(tmp.getX()-x,2) + Math.pow(tmp.getY()-y,2) ) <= radius) result = tmp;
        }
        if (Config.DEBUG){
            if (result != null) System.out.println("DEBUG-INFO: Found Object "+result.getType().name()+" at x: "+result.getX()+" and y: "+result.getY());
        }
        return result;
    }

    public ScenarioTerrain getTerrainAt(double x, double y, int direction){
        ScenarioTerrain result = null;
        double column = Math.floor(x / scenarioTerrain.length);
        double row = Math.floor(y / scenarioTerrain[0].length);
        int modD = direction % 360;
        if (modD < 45 && modD >= 0 || modD > 325 && modD <= 360) column++; // Right
        if (modD >= 45 && modD < 135 ) row--; // Up
        if (modD >= 135 && modD < 315 ) column--; // Left
        if (modD >= 315 && modD <= 325 ) row++; //Down
        if (column >= 0 && column < scenarioTerrain.length && row >= 0 && row <= scenarioTerrain[0].length) result = scenarioTerrain[(int)column][(int)row];
        if (Config.DEBUG){
            if (result != null) System.out.println("DEBUG-INFO: Found Terrain "+result.getType().name()+" at xTile: "+column+" and yTile: "+row);
        }
        return result;
    }

    private void loadLevelNow(int level){
        unregisterAllObjects();
        if (level == 1){
            //Load ScenarioTerrain
            scenarioTerrain = zoneLoader.getZoneTerrain("assets/data/zone1.txt");
            //Place Ressources
            // MAYBE better in ZoneLoader with ScenarioRessource-Map?
            ScenarioRessource wood = new ScenarioRessource(false,true,2500,1,ScenarioObjectData.WOOD,scnControl);
            wood.setX(32*8);
            wood.setY(32*8);
            scenarioRessources.add(wood);
            //Place Burg
            theBurg = new Burg(scnControl);
            theBurg.setX(32*14);
            theBurg.setY(32*28);
            objects.add(theBurg);
            //Place Start-Bauer
            new Bauer(theBurg);
        }
        registerAllObjects();
    }

    private void registerAllObjects(){
        //ScenarioTerrainData
        for(int i = 0; i < scenarioTerrain.length; i++){
            for(int j = 0; j < scenarioTerrain[0].length; j++){
                if(scenarioTerrain[i][j]!=null) {
                    scenarioTerrain[i][j].setX(i * 32);
                    scenarioTerrain[i][j].setY(j * 32);
                    scnControl.getUiControl().registerObject(scenarioTerrain[i][j]);
                }
            }
        }
        //Ressources
        Iterator<ScenarioRessource> iterator = scenarioRessources.iterator();
        while(iterator.hasNext()) scnControl.getUiControl().registerObject(iterator.next());
        //Objects
        Iterator<ScenarioObject> iterator2 = objects.iterator();
        while(iterator2.hasNext()) scnControl.getUiControl().registerObject(iterator2.next());
        //Characters
        Iterator<ScenarioCharacter> iterator3 = characters.iterator();
        while(iterator3.hasNext()) scnControl.getUiControl().registerObject(iterator3.next());
    }

    public void unregisterAllObjects(){
        //ScenarioTerrainData
        for(int i = 0; i < scenarioTerrain.length; i++){
            for(int j = 0; j < scenarioTerrain[0].length; j++){
                    scnControl.getUiControl().removeObject(scenarioTerrain[i][j]);
            }
        }
        //Ressources
        Iterator<ScenarioRessource> iterator = scenarioRessources.iterator();
        while(iterator.hasNext()) scnControl.getUiControl().removeObject(iterator.next());
        //Objects
        Iterator<ScenarioObject> iterator2 = objects.iterator();
        while(iterator2.hasNext()) scnControl.getUiControl().removeObject(iterator2.next());
        //Characters
        Iterator<ScenarioCharacter> iterator3 = characters.iterator();
        while(iterator3.hasNext()) scnControl.getUiControl().registerObject(iterator3.next());
    }

    public boolean collides(GraphicalObject g){
        int xTile = (int)Math.floor(g.getX()/30);
        int yTile = (int)Math.floor(g.getY()/30);
        //Check Map-Borders
        if (g.getX() < 0 || g.getX() + g.getWidth() > Config.WINDOW_WIDTH || g.getY() < 0 || g.getY() + g.getHeight() > Config.WINDOW_HEIGHT-29) return true;
        //Check surrounding Terrain
        for(int xPos = xTile-1;xPos<xTile+1;xPos++){
            for(int yPos = yTile-1;yPos<yTile+1;yPos++){
                if(xPos<scenarioTerrain.length && xPos >= 0 && yPos<scenarioTerrain[0].length && yPos >=0){
                    if(scenarioTerrain[xPos][yPos].isBlocking() && g.collidesWith(scenarioTerrain[xPos][yPos])) return true;
                }
            }
        }
        //Ressources
        Iterator<ScenarioRessource> iterator = scenarioRessources.iterator();
        while(iterator.hasNext()){
            ScenarioRessource tmp = iterator.next();
            if (tmp.isBlocking() && tmp.collidesWith(g)) return true;
        }
        //Objects
        Iterator<ScenarioObject> iterator2 = objects.iterator();
        while(iterator2.hasNext()){
            ScenarioObject tmp = iterator2.next();
            if (tmp.isBlocking() && tmp.collidesWith(g)) return true;
        }
        //Characters
        Iterator<ScenarioCharacter> iterator3 = characters.iterator();
        while(iterator3.hasNext()){
            ScenarioCharacter tmp = iterator3.next();
            if (tmp.isBlocking() && tmp.collidesWith(g)) return true;
        }
        return false;
    }

    @Override
    public void update(double dt){
        timer += dt;
        if (levelToLoad > 0 && timer > SPAWN_DELAY){
            loadLevelNow(levelToLoad);
            levelToLoad = 0;
            timer = 0;
        }
    }

    public Burg getTheBurg() {
        return theBurg;
    }

    public ScenarioTerrain[][] getScenarioTerrain() {
        return scenarioTerrain;
    }
}

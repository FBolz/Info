package akkgframework.model.scenario;

import control.Config;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;


public abstract class ScenarioCharacter extends GraphicalObject{

    //Attribute
    private boolean firstAction;
    private boolean isBlocking;
    private boolean hidden;
    private int imageIndex;

    private double animation_timer, action_timer, life_timer;

    private int direction;
    private double xFactor;
    private double yFactor;
    private int sightradius;
    private boolean justCollided;
    private int waySoFar;
    private final int MOVE_DISTANCE = 20;

    private final double STUN_TIME = 1.5;
    private final double ANIMATION_TIMESTEP = 0.5;

    //Referenzen
    private ScenarioCharacterData type;
    private ScenarioController scenarioController;

    public ScenarioCharacter(ScenarioCharacterData type, boolean hidden, boolean isBlocking, ScenarioController scn){
        waySoFar = 0;
        firstAction = true;
        sightradius = 25;
        justCollided = false;
        scenarioController = scn;
        animation_timer = ANIMATION_TIMESTEP;
        action_timer = ScenarioController.ACTION_TIME_STEP;
        direction = 90;
        imageIndex = 0;
        this.hidden = hidden;
        this.isBlocking = isBlocking;
        this.type = type;
        setMyImage(type.getWalkDown()[1]);
    }

    @Override
    public void draw(DrawTool drawTool) {
        if (!isHidden()) drawTool.drawImage(getMyImage(),x,y);
        if (Config.DEBUG){
            //Anzeige Sichtradius
            drawTool.setCurrentColor(255,255,0,255);
            drawTool.drawCircle(x+xFactor*sightradius,(int)(y+yFactor*sightradius),sightradius);
            //Anzeige Terrainuntersuchung
            double column = Math.floor(x / scenarioController.getZoneControl().getScenarioTerrain().length);
            double row = Math.floor(y / scenarioController.getZoneControl().getScenarioTerrain()[0].length);
            int modD = direction % 360;
            if (modD < 45 && modD >= 0 || modD > 325 && modD <= 360) column++; // Right
            if (modD >= 45 && modD < 135 ) row--; // Up
            if (modD >= 135 && modD < 225 ) column--; // Left
            if (modD >= 225 && modD <= 325 ) row++; //Right
            drawTool.drawRectangle(column*Config.WINDOW_WIDTH/scenarioController.getZoneControl().getScenarioTerrain().length,
                    row*(Config.WINDOW_HEIGHT-29)/scenarioController.getZoneControl().getScenarioTerrain()[0].length,
                    Config.WINDOW_WIDTH/scenarioController.getZoneControl().getScenarioTerrain().length,
                    (Config.WINDOW_HEIGHT-29)/scenarioController.getZoneControl().getScenarioTerrain()[0].length);
        }
        zeichneDich(drawTool);
    }

    protected void zeichneDich(DrawTool drawTool){

    }

    private void switchImage(double dt){
        if (imageIndex < 2) {
            imageIndex++;
        } else {
            imageIndex = 0;
        }
        int modD = direction % 360;
        if (modD < 45 && modD >= 0 || modD > 325 && modD <= 360) setMyImage(type.getWalkRight()[imageIndex]);
        if (modD >= 45 && modD < 135 ) setMyImage(type.getWalkUp()[imageIndex]);
        if (modD >= 135 && modD < 215 ) setMyImage(type.getWalkLeft()[imageIndex]);
        if (modD >= 215 && modD <= 325 ) setMyImage(type.getWalkDown()[imageIndex]);
    }

    @Override
    public void update(double dt){
        life_timer += dt;
        if (life_timer > STUN_TIME) {
            if (firstAction){
                macheEinmal();
                firstAction = false;
            }
            animation_timer = animation_timer - dt;
            if (animation_timer < 0) {
                animation_timer = ANIMATION_TIMESTEP;
                switchImage(dt);
            }
            action_timer = action_timer - dt;
            if (action_timer < 0) {
                action_timer = ScenarioController.ACTION_TIME_STEP;
                wiederhole();
            }
        }
    }

    private void moving(int distance){
        if (distance > 0) {
            //Rechne Richtungswinkel in Vektor um und bewege
            xFactor = Math.cos(Math.toRadians(-1*direction));
            yFactor = Math.sin(Math.toRadians(-1*direction));
            double deltaX = distance * xFactor;
            double deltaY = distance * yFactor;
            //Prüfe Kollision, bewege ggf zurück und lösche pixelsToMove
            x += deltaX;
            y += deltaY;
            if (scenarioController.collides(this)){
                justCollided = true;
                x -= deltaX;
                y -= deltaY;
            } else {
                waySoFar += distance;
            }
        }
    }


    //ZU IMPLEMENTIEREN IN DER UNTERKLASSE
    public abstract void wiederhole();
    public abstract void macheEinmal();

    //METHODEN ZUR NUTZUNG IN DER UNTERKLASSE
    protected void dreheDichUm(int t){
        if (t<=360 && t>=-360){
            direction += t;
            if (direction < 0) direction = direction*-1+360;
        }
    }

    protected int gibBisherGelaufen(){
        return waySoFar;
    }

    protected void dreheDichNach(int t){
        if(t<=360 && t >= 0) direction = t;
    }

    protected void gehe(){
        moving(MOVE_DISTANCE);
    }

    protected boolean bistDuGeradeKollidiert(){
        boolean tmp = justCollided;
        justCollided = false;
        return tmp;
    }

    protected String anWelchemRandBinIch(){
        if( x < 10 ) return "links";
        if( x > Config.WINDOW_WIDTH-width-10) return "rechts";
        if( y < 10 ) return "oben";
        if( y > Config.WINDOW_HEIGHT-29-height-10) return "unten";
        return null;
    }

    protected int gibZufallsZahl(int a, int b){
        return (int)(a + Math.random()* b);
    }

    protected ScenarioRessource gibRessourceVorMir(){
        ScenarioRessource res = scenarioController.getZoneControl().getFirstRessourceAt((int)(x+xFactor*sightradius),(int)(y+yFactor*sightradius),sightradius);
        if (res != null && !res.isHidden()) return res;
        return null;
    }

    protected boolean istRessourceVorMir(){
        return gibRessourceVorMir() != null;
    }

    protected ScenarioTerrain gibTerrainVorMir(){
        ScenarioTerrain terr =  scenarioController.getZoneControl().getTerrainAt(x,y,direction);
        return terr;
    }

    protected ScenarioObject gibObjektVorMir(){
        ScenarioObject obj = scenarioController.getZoneControl().getFirstObjectAt((int)(x+xFactor*sightradius),(int)(y+yFactor*sightradius),sightradius);
        if (obj != null && !obj.isHidden()) return obj;
        return null;
    }

    protected ScenarioCharacter gibCharakterVorMir(){
        ScenarioCharacter ch = scenarioController.getZoneControl().getFirstCharacterAt((int)(x+xFactor*sightradius),(int)(y+yFactor*sightradius),sightradius);
        if (ch != null && !ch.isHidden()) return ch;
        return null;
    }

    protected boolean istDieKachelVorMirBegehbar(){
        return scenarioController.getZoneControl().getTerrainAt(x,y,direction).isBlocking();
    }

    //TODO richtig machen
    protected void dreheDichZurBurg(){
        SimpleCastle sc = this.getScenarioController().getZoneControl().getTheBurg();
        // Richtung
        double deltaX = sc.getX() - this.getX();
        double deltaY = sc.getY() - this.getY();
        double winkel = Math.toDegrees(Math.atan(deltaY/deltaX));
        // if (Config.DEBUG) System.out.println("Winkel zur Burg: dX="+deltaX+" dY="+deltaY+" winkel="+(360-winkel));
        this.dreheDichNach(360-(int)winkel);
    }

    protected boolean binIchBeiBurg(){
        SimpleCastle sc = this.getScenarioController().getZoneControl().getTheBurg();
        double distance = Math.sqrt( Math.pow(sc.getX()-this.getX(),2) + Math.pow(sc.getY()-this.getY(),2) );
        if (distance < 20) return true;
        return false;
    }

    //GETTER

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean isHidden() {
        return hidden;
    }

    public ScenarioCharacterData getType() {
        return type;
    }

    public ScenarioController getScenarioController() {
        return scenarioController;
    }
}

//TODO hier weiter: Richtung Burg drehen. Schilder. Ressource bei Castle droppen. Zielfeld als zu erreichende Stelle bei einfachen Szenarien.
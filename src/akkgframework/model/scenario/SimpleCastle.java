package akkgframework.model.scenario;

import akkgframework.model.fundamental.Pair;
import akkgframework.model.scenarioexample.Bauer;
import akkgframework.model.scenarioexample.Burg;
import akkgframework.view.DrawTool;

public abstract class SimpleCastle extends ScenarioObject{

    private int food;
    private int wood;
    private double timer;

    private final double FOOD_SPAWN_TIME = 10;
    private final int PEASANT_COST = 5;
    private final int PEASANT_FAIL_COST = 2;
    private final int MAX_PEASANTS = 5;
    private int peasantCount;

    public SimpleCastle(ScenarioController scn) {
        super(ScenarioObjectData.CASTLE_LEFT_SIDE, false, false, scn);
        peasantCount = 0;
        food = 0;
        this.createAndSetNewImage("assets/images/tilesets/scenario/castle_single.png");
        macheEinmal();
    }

    @Override
    public void draw(DrawTool drawTool){
        drawTool.drawImage(getMyImage(),x,y);
        drawTool.setCurrentColor(255,255,0,255);
        drawTool.drawText(x+65,y+20,"Holz: "+wood);
    }

    public Pair getSpawnCoordinates(){
        return new Pair((int)(x+width/2-16),(int)y+20);
    }

    @Override
    public void update(double dt){
        timer += dt;
        if (timer > FOOD_SPAWN_TIME){
            food++;
            timer = 0;
        }
        wiederhole();
    }

    private void addPeasant(){
        if (peasantCount < MAX_PEASANTS) {
            if (food >= PEASANT_COST) {
                if (Math.random() < 0.9) {
                    food -= PEASANT_COST;
                    getScenarioController().getZoneControl().addPeasant(new Bauer((Burg) this));
                    peasantCount++;
                    System.out.println("[BURG]: Neuer Bauer! Resultat: Erfolg!");
                } else {
                    food -= PEASANT_COST;
                    System.out.println("[BURG]: Neuer Bauer! Resultat: Leider ist der neue Bauer mit dem Essen davongelaufen. Mist.");
                }

            } else {
                food -= PEASANT_FAIL_COST;
                System.out.println("[BURG]: Neuer Bauer! Resultat: Es war zu wenig Essen da, um einen Bauern zu erzeugen. Der Versuch hat leider 2 Essen gekostet.");
            }
        }else{
            System.out.println("[BURG]: Mehr Bauern gehen nicht. Echt mal.");
        }
    }

    //Zu überschreibende Methoden
    public abstract void macheEinmal();
    public abstract void wiederhole();

    //Zu benutzende Methodne
    protected int gibAnzahlEssen(){return food; }
    public void erzeugeBauer(){ addPeasant();}

}

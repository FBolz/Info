package akkgframework.model.scenario;

import control.Config;
import akkgframework.view.DrawTool;

public abstract class SimplePeasant extends ScenarioCharacter {

    private int gesammeltesHolz;
    private int carryCapacity;

    private SimpleCastle castle;

    public SimplePeasant(SimpleCastle c){
        super(ScenarioCharacterData.PEASANT, false, false, c.getScenarioController());
        castle = c;
        getScenarioController().getZoneControl().addPeasant(this);
        carryCapacity = 50;
        this.getScenarioController().SOUND_CONTROL.playSound("door");
        gesammeltesHolz = 0;
    }

    public void zeichneDich(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,0,255);
        drawTool.drawText(x+30,y+30,""+gesammeltesHolz);
    }

    // METHODEN ZUR NUTZUNG
    protected void sammleRessource(){
        if (gesammeltesHolz < carryCapacity) {
            ScenarioRessource ressource = this.gibRessourceVorMir();
            if (ressource != null) {
                if (ressource.getType() == ScenarioObjectData.WOOD) gesammeltesHolz += ressource.abbauen();
                if (gesammeltesHolz > carryCapacity) gesammeltesHolz = carryCapacity;
                if (Config.DEBUG)
                    System.out.println("DEBUG-INFO: Ressource abgebaut. Aktuelles Bauer-Holz: " + gesammeltesHolz);
            }else{
                if (Config.DEBUG) System.out.println("DEBUG-INFO: Ressource nicht vorhanden.");
            }
        } else {
            System.out.println("BAUER: Ich kann nicht mehr tragen.");
        }
    }

    protected boolean kannIchNochMehrTragen(){
        return gesammeltesHolz < carryCapacity;
    }
}

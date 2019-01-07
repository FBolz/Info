package akkgframework.model.scenario;

import akkgframework.view.DrawTool;

public class ScenarioRessource extends ScenarioObject{

    //Attribute
    private int unitsLeft;
    private int tier;

    public ScenarioRessource(boolean hidden, boolean isBlocking, int units, int tier, ScenarioObjectData type, ScenarioController scn){
        super(type,hidden,isBlocking,scn);
        this.unitsLeft = units;
        this.tier = tier;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(!isHidden()) drawTool.drawImage(getMyImage(),x,y);
    }

    public int abbauen(){
        if (unitsLeft > 0 && !isHidden()) {
            this.getScenarioController().SOUND_CONTROL.playSound(getType().name);
            int abbau = (int) (10 / tier * Math.random() + 1);
            if (abbau <= unitsLeft) {
                unitsLeft -= abbau;
                return abbau;
            } else {
                abbau = unitsLeft;
                unitsLeft = 0;
                this.setHidden(true);
                return abbau;
            }
        }
        this.setHidden(true);
        return 0;
    }

    @Override
    public void update(double dt){

    }

}

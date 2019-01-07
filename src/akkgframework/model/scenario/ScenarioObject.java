package akkgframework.model.scenario;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;


public abstract class ScenarioObject extends GraphicalObject{

    //Attribute
    private boolean isBlocking;
    private boolean hidden;

    //Referenzen
    private ScenarioObjectData type;
    private ScenarioController scenarioController;

    public ScenarioObject(ScenarioObjectData type, boolean hidden, boolean isBlocking, ScenarioController scn){
        this.scenarioController = scn;
        this.hidden = hidden;
        this.isBlocking = isBlocking;
        this.type = type;
        setMyImage(type.getImg());
    }

    @Override
    public void draw(DrawTool drawTool) {
        if (!isHidden()) drawTool.drawImage(getMyImage(),x,y);
    }

    @Override
    public void update(double dt){

    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean isHidden() {
        return hidden;
    }

    public ScenarioObjectData getType() {
        return type;
    }

    public ScenarioController getScenarioController(){ return scenarioController; }
}

package akkgframework.model.scenario;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.image.BufferedImage;

public class ScenarioTerrain extends GraphicalObject{

    //Referenzen
    private ScenarioTerrainData type;
    private boolean isBlocking;

    public ScenarioTerrain(ScenarioTerrainData type, BufferedImage image, boolean isBlocking){
        this.type = type;
        this.isBlocking = isBlocking;
        setMyImage(image);
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
    }

    @Override
    public void update(double dt){

    }

    public ScenarioTerrainData getType() {
        return type;
    }

    public boolean isBlocking() {
        return isBlocking;
    }
}

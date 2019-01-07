package akkgframework.model.scenario;

import control.Config;
import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.control.fundamental.SoundController;

public class ScenarioController extends GraphicalObject{

    //Attribute
    public static final double ACTION_TIME_STEP = 1;
    private double scenarioTime;

    //Referenzen
    private UIController uiControl;
    private ZoneController zoneController;
    public final SoundController SOUND_CONTROL;

    public ScenarioController(int startlevel, UIController uiController){
        System.out.println("INFORMATION: Szenario-Controller wurde gestartet. Sie befinden sich im Szenario-Modus.");
        SOUND_CONTROL = new SoundController();
        uiController.registerObject(SOUND_CONTROL);
        loadSounds();
        if (Config.DEBUG) System.out.println("DEBUG-INFO: BGM muted due to Debug-Mode.");
        scenarioTime = 0;
        this.uiControl = uiController;
        uiControl.registerObject(this);
        zoneController = new ZoneController(this);
        loadLevel(startlevel);
    }

    private void loadSounds(){
        SOUND_CONTROL.loadSound("assets/sounds/music/flags.mp3","bgm",true);
        SOUND_CONTROL.loadSound("assets/sounds/rpg-effects/world/door.wav", "door", false);
        // Load ScenarioData-associated Sounds.
        SOUND_CONTROL.loadSound("assets/sounds/rpg-effects/world/chopwood.mp3", ScenarioObjectData.WOOD.name, false);
    }

    @Override
    public void update(double dt){
        scenarioTime += dt;
        if (!Config.DEBUG){
            if (scenarioTime > 3 && !SOUND_CONTROL.isPlaying("bgm")) SOUND_CONTROL.playSound("bgm");
        }
    }

    public void loadLevel(int level){
        zoneController.loadLevel(level);
    }

    public boolean collides(GraphicalObject g){
        return zoneController.collides(g);
    }

    public UIController getUiControl(){
        return uiControl;
    }

    public ZoneController getZoneControl(){ return zoneController; }

}

package akkgframework.model.scenarioexample;

import akkgframework.model.scenario.ScenarioController;
import akkgframework.model.scenario.SimpleCastle;

public class Burg extends SimpleCastle {

    public Burg(ScenarioController scn) {
        super(scn);
    }

    @Override
    public void macheEinmal() {

    }

    @Override
    public void wiederhole() {
        if(gibAnzahlEssen() > 5) erzeugeBauer();
    }

}

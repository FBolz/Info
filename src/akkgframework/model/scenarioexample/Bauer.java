package akkgframework.model.scenarioexample;

import akkgframework.model.scenario.SimplePeasant;

public class Bauer extends SimplePeasant {

    public Bauer(Burg b) {
        super(b);
    }

    @Override
    public void wiederhole() {
        if (gibRessourceVorMir() == null || !kannIchNochMehrTragen()){
            if (!kannIchNochMehrTragen()) dreheDichZurBurg();
            gehe();
            //if (bistDuGeradeKollidiert()){
            //    dreheDichUm(20);
            //}
        }
        if (istRessourceVorMir() && kannIchNochMehrTragen()){
            this.sammleRessource();
        }
    }

    @Override
    public void macheEinmal() {
        dreheDichNach(108);
    }

}

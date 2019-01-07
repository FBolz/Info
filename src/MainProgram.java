import akkgframework.control.fundamental.MainController;
import control.Config;

import java.awt.*;

public class MainProgram {

    /**
     * Die Main-Methode von Java startet das Programm. Sie ist einzigartig im ganzen Projekt.
     * Sie erzeugt mit dem nachfolgenden Code genau ein Objekt der Klasse MainController und ist
     * dann abgeschlossen.
     */
    public static void main (String[] args){
        if ( Config.INFO_MESSAGES) System.out.println("***** PROGRAMMSTART *****.");
        if ( Config.INFO_MESSAGES) System.out.println(Config.VERSION);
        if ( Config.INFO_MESSAGES) System.out.println("Benötigte Objekte des Frameworks werden erzeugt:");
        if ( Config.INFO_MESSAGES) System.out.println("  > Main-Methode: Programmstart. Erzeuge ein Objekt der Main-Controller-Klasse.");
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new MainController();
                    }
                });
    }
}

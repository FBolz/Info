package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class End extends GraphicalObject {
    //Referenzen
    private String clicked;
    private String player;

    /**
     * Konstruktor
     * @param player, der gewonnen hat
     */
    public End(String player) {
    this.player=player;
    }

    /**
     * Zeichnet die Texte für Restart, End und wer gewonnen hat
     * @param drawTool Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool){
        drawTool.formatText("", 3,40);
        drawTool.setCurrentColor(255,255,255,255);

        drawTool.drawText(650,150,player+" won!");
        drawTool.formatText("", 3,60);
        drawTool.drawText(700,345,"Restart");
        drawTool.drawText(700,545,"End");
    }

    /**
     * Überprüfung, wo der Mauszeiger losgelassen wurde und dann wird clicked auf den angeklickten Status gesetzt
     * @param e Der Mauszeiger
     */
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 700 && e.getX()<=910 && e.getY()>=300 && e.getY()<=345){
            clicked = "restart";
        }
        if(e.getX()>= 700 && e.getX()<=825 && e.getY()>=500 && e.getY()<=550){
            System.exit(0);
        }
    }
    //Getter-Methode für den Status
    public String getClicked() {
        return clicked;
    }

    /**
     * Setzt den Status auf den übergebenen Status
     * @param clicked Auf welchen Status es gesetzt werden soll
     */
    public void setClicked(String clicked) {
        this.clicked = clicked;
    }
}

package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Options extends GraphicalObject {
    //Referenz
    private String clicked;
    /**
     * Zeichnet die Texte für Music, Life und den Back Knopf
     * @param drawTool Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,60);
        drawTool.drawText(670,350,"Music");
        drawTool.drawText(670,580,"Life");
        drawTool.formatText("", 3,40);
        drawTool.drawText(200,800,"Back");
    }
    /**
     * Überprüfung, wo der Mauszeiger losgelassen wurde und danach wird clicked auf den ausgewählten Status gesetzt
     * @param e Der Mauszeiger
     */
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 670 && e.getX()<=840 && e.getY()>=305 && e.getY()<=355){
            clicked = "music";
        }
        if(e.getX()>= 670 && e.getX()<=785 && e.getY()>=535 && e.getY()<=585){
            clicked = "life";
        }
        if(e.getX()>= 40 && e.getX()<=440 && e.getY()>=750 && e.getY()<=850){
            clicked = "back";
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

package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class MusicSelection extends GraphicalObject {
    //Referenz
    private String clicked;
    /**
     * Zeichnet die Texte für die verschiedenen Möglichkeiten und den Back Knopf
     * @param drawTool Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,40);
        drawTool.drawText(620,100,"Flags");
        drawTool.drawText(620,250,"Doomed");
        drawTool.drawText(620,400,"Great Missions");
        drawTool.drawText(620,550,"Battle Theme");
        drawTool.drawText(620,700,"Spacetime");
        drawTool.drawText(200,910,"Back");
    }

    /**
     * Überprüfung, wo der Mauszeiger losgelassen wurde und dann clicked auf den ausgewählten Musik Pfad setzen oder auf back
     * @param e Der Mauszeiger
     */
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 620 && e.getX()<=725 && e.getY()>=70 && e.getY()<=105){
            clicked = "flags.wav";
        }
        if(e.getX()>= 620 && e.getX()<=780 && e.getY()>=220 && e.getY()<=255){
            clicked = "doomed.wav";
        }
        if(e.getX()>= 620 && e.getX()<=905 && e.getY()>=370 && e.getY()<=405){
            clicked = "greatMissions.wav";
        }
        if(e.getX()>= 620 && e.getX()<=870 && e.getY()>=520 && e.getY()<=555){
            clicked = "battleThemeA.wav";
        }
        if(e.getX()>= 620 && e.getX()<=820 && e.getY()>=670 && e.getY()<=705){
            clicked = "spacetime.wav";
        }
        if(e.getX()>= 40 && e.getX()<=440 && e.getY()>=850 && e.getY()<=950)

        {
            clicked = "back";
        }
    }
    //Getter-Methode für den ausgewählten Musik Pfad bzw. Status
    public String getClicked() {
        return clicked;
    }
    /**
     * Setzt den Musik Pfad auf den übergebenen Pfads
     * @param clicked Auf welchen Pfad  gewechselt werden soll bzw. Status
     */
    public void setClicked(String clicked) {
        this.clicked = clicked;
    }

}

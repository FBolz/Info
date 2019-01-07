package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class House extends GraphicalObject {

    /**
     * Konstruktor der Klasse House, die von der Klasse GraphicalObject erbt.
     * Das Prinzip der Vererbung wird in naher Zukunft im Unterricht noch besprochen.
     * Hier im Beispiel werden Werte den in der Oberklasse GraphicalObject deklarierten und dort bereits initialisierten Attributen zugewiesen.
     */
    public House(){
        x = 100;
        y = 300;
        width = 50;
        height = 25;
    }


    @Override
    /**
     * Wird vom Hintergrundprozess für jeden Frame aufgerufen. Nur hier kann die grafische Repräsentation des Objekts realisiert
     * werden. Es ist möglich über das Grafikobjekt "drawTool" ein Bild zeichnen zu lassen, aber auch geometrische Formen sind machbar.
     */
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawTriangle(x,y,x+width,y,x+width/2,y-height/3);
        drawTool.setCurrentColor(255,0,0,255);
        drawTool.drawRectangle(x,y,width,height);
    }

    @Override
    /**
     * Wird vom Hintergrundprozess für jeden Frame aufgerufen. Hier kann das verhalten des Objekts festgelegt werden, zum Beispiel
     * seine Bewegung.
     */
    public void update(double dt) {

    }
}

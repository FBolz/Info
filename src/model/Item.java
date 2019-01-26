package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.image.BufferedImage;

public class Item extends GraphicalObject {

    private int colorNumber;
    private double timer;
    private boolean jump;
    private Player player1;
    private Player player2;

    /**
     * Konstruktor der Klasse Item, Attribute werden festgelegt
     * @param colorNumber bestimmt die Frabe des Planeten
     * @param player1 kennt Spieler
     * @param player2 und 2
     */
    public Item(int colorNumber,Player player1,Player player2) {
        this.colorNumber=colorNumber;
        x = Math.random() * 1000;
        y = Math.random() * 900;
        height= 30;
        width=30;
        timer=0;
        jump=true;
        this.player1= player1;
        this.player2=player2;
        switch (colorNumber) {
            case 1://gelb
                createAndSetNewImage("assets/images/objects/yellow planet.png" );
                break;
            case 2:
                createAndSetNewImage("assets/images/objects/redplanet.png" );
                break;
            case 3:
                createAndSetNewImage("assets/images/objects/purpleplanet2.png" );
                break;
            case 4: createAndSetNewImage("assets/images/objects/greenPlanet.png" );
                break;
            case 5:createAndSetNewImage("assets/images/objects/blue planet.png" );
                break;

        }
    }

    /**
     * bestimmtes Bild abhängig von der colorfarbe wiord gezeichnet
     * @param drawTool
     */
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
    }

    /**
     * Gibt die Farbe des Items (Planeten) zurück
     * @return colorNumber
     */
    public int getColorNumber() {
        return colorNumber;
    }

    /**
     * Objekt springt wenn der timer größer als 10 ist und das attribut jump true ist an neue Koordinaten im Bildschirm
     * wenn der Planet dann mit dem Spieler kollidiert springt er wieder an neue Koordinaten
     */
    public void jump(){
        if(jump==true&& timer>= 10) {
            x = Math.random() * 1200;
            y = Math.random() * 950;
            if(this.collidesWith(player1) || this.collidesWith(player2)){
                x = Math.random() * 1350;
                y = Math.random() * 950;
            }
        }
    }

    /**
     * Planet (Item) springt an eine Stelle außerhalb des Bildschirms
     */
    public void jumpOut(){
      y= Math.random()*-700;
      x= Math.random()*-700;
    }

    /**
     * Ein timer läuft
     * Nach 10 Sekunden wird jump aufgerufen und der Planet
     * @param dt
     */
    public void update(double dt){
        timer= timer+1*dt;
        if( timer >=10){
            jump();
            timer=0;
        }

    }

    /**
     * die Planeten, die Oben sind können nicht springen also wird ihr jump attribut auf false gesetzt
     * @param a
     */
    public void setJump(boolean a ){
        jump=a ;

    }
}

package model;


import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Projectile extends GraphicalObject {

    // Attribute
    private int speed;
    private int size;
    private int farbeR, farbeG, farbeB;
    private String direction;

    // Referenzen
    private UIController uiControl;


    /**
     * Konstruktor
     *
     * @param x         spezifiziert die x-Koordinate der oberen, linken Ecke des Spielers.
     * @param y         spezifiziert die y-Koordinate der oberen, linken Ecke des Spielers.
     */
    public Projectile(double x, double y, String direction, UIController uiControl) {
        this.uiControl = uiControl;
        this.x = x;
        this.y = y;
        farbeR = (int) (Math.random() * 256);
        farbeG = (int) (Math.random() * 256);
        farbeB = (int) (Math.random() * 256);
        this.speed = 15;
        this.size = 10;
        this.direction=direction;

    }

    /**
     * Methode wird automatisch aufgerufen.
     * Zeichnet den Spieler als Kreis
     *
     * @param drawTool Referenz auf das Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(farbeR, farbeG, farbeB, 255);
        drawTool.drawFilledCircle(x, y, size / 2);
        drawTool.setCurrentColor(0, 0, 0, 255);
        drawTool.drawCircle(x, y, size / 2);
    }

    /**
     * Methode wird automatisch aufgerufen.
     * Wenn die passende Taste herunter gedrückt wird, bewegt sich der Spieler in die entsprechende Richtung.
     *
     * @param dt Gibt die vergangene Zeit in Sekunden seit dem letzten Aufruf an.
     */
    public void update(double dt) {
        if (direction!=null) {
            if (direction.equals("left")) {
                x = x - speed;
            }
            if (direction.equals("right")) {
                x = x + speed;
            }
            if (direction.equals("down")) {
                y = y + speed;
            }
            if (direction.equals("up")) {
                y = y - speed;
            }
        }
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}

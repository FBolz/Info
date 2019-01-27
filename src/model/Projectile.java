package model;


import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Projectile extends GraphicalObject {

    // Attribute
    private int speed;
    private String direction;
    private boolean isActive;

    // Referenzen
    private UIController uiControl;


    /**
     * Konstruktor
     *
     * @param x          x-Koordinate des Projectiles.
     * @param y          y-Koordinate des Projectiles.
     * @param direction  Richtung des Projectiles
     */
    public Projectile(double x, double y, String direction, UIController uiControl) {
        this.uiControl = uiControl;
        this.x = x+10;
        this.y = y+10;
        this.speed = 15;
        this.direction=direction;
        switch (direction) {
            case "left":
                createAndSetNewImage("assets/images/Laser/Laser_Left.png");
                break;
            case "right":
                createAndSetNewImage("assets/images/Laser/Laser_Right.png");
                break;
            case "up":
                createAndSetNewImage("assets/images/Laser/Laser_Up.png");
                break;
            case "down":
                createAndSetNewImage("assets/images/Laser/Laser_Down.png");
            break;
            default:
                break;
        }
        isActive = false;

    }

    /**
     *
     * Zeichnet das Projectile
     *
     * @param drawTool Referenz auf das Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(), x, y);
    }

    /**
     * Je nachdem welche Richtung übergeben wurde bewegt sich das Projectile dementsprechend.
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

    /**
     *Setzen der Geschwindigkeit.
     *@param speed Neue Geschwindigkeit
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Rückgabe der Geschwindigkeit.
     * @return speed Geschwindigkeit des Projectiles.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Rückgabe der Richtung.
     * @return direction Richtung des Projectiles.
     */
    public String getDirection() {
        return direction;
    }
    /**
     *Setzen der Richtung.
     *@param direction Neue Richtung
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    /**
     * Rückgabe ob das Projectile aktiv ist.
     * @return Aktivität des Projectiles
     */
    public boolean isActive() {
        return isActive;
    }
    /**
     *Setzen des Aktivitätszustandes.
     *@param active Neuer Aktivitätszustand
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}

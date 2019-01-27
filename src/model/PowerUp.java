package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class PowerUp extends GraphicalObject {
    // Attribute
    private String type;
    /**
     * Konstruktor
     * @param type Art des PowerUps
     *
     */
    public PowerUp(String type) {
        this.type=type;
        switch (type) {
            case "Speed":
                createAndSetNewImage("assets/images/PowerUps/PowerUp_Speed.png");
                break;
            case "FastShoot":
                createAndSetNewImage("assets/images/PowerUps/PowerUp_RapidShot.png");
                break;
            case "Live":
                createAndSetNewImage("assets/images/PowerUps/PowerUp_Life.png");
                break;
            case "StrongShoot":
                createAndSetNewImage("assets/images/PowerUps/PowerUp_PowerShot .png");
                break;
            case "Invert":
                createAndSetNewImage("assets/images/PowerUps/PowerUp_Speed.png");
                break;
            default:
                break;
        }
        jump();
    }

    /**
     *
     * Zeichnet das PowerUp
     *
     * @param drawTool Referenz auf das Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool) {
    drawTool.drawImage(getMyImage(), x, y);
    }

    /**
     * Zufälliges setzen der x und y Koordinate
     *
     */
    public void jump() {
        x = Math.random() * 900;
        y = Math.random() * 800;

    }
    /**
     * Rückgabe der PowerUp Art
     * @return type Art des PowerUps
     */
    public String getType() {
        return type;
    }
}


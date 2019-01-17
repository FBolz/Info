package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class PowerUp extends GraphicalObject {
    private int strength;
    private String type;

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

    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(), x, y);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int amount) {
        strength = strength + amount;
    }

    private void jump() {
        x = Math.random() * 600;
        y = Math.random() * 704;

    }

    public String getType() {
        return type;
    }
}


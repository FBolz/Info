package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class PowerUp extends GraphicalObject {
    private int strength;
    private String type;

    public PowerUp(String type) {
        this.type=type;
        jump();
    }

    public void draw(DrawTool drawTool) {
        switch (type){
            case "Speed":drawTool.setCurrentColor(30,144,255,255); break;
            case "Shoot": drawTool.setCurrentColor(0, 0, 0, 255); break;
            case "Live": drawTool.setCurrentColor(255,48,48,255); break;
            default: break;
        }

        drawTool.drawFilledCircle(x, y, 30);
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

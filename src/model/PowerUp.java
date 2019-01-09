package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class PowerUp extends GraphicalObject {
    private int strength;
    public PowerUp(){
        jump();



    }
    public  void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(59,59,59,255);
        drawTool.drawFilledCircle(x,y,60);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int amount) {
        strength = strength+ amount;
    }
    private void jump(){
        x=Math.random()*1600;
        y= Math.random()*1024;
    }
}

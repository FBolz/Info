package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Item extends GraphicalObject {
    private int colorNumber;
    private double timer;
    private boolean jump;

    public Item(int colorNumber) {
        this.colorNumber=colorNumber;
        jump();
        height= 30;
        width=30;
        timer=0;
        jump=true;


    }

    public void draw(DrawTool drawTool) {
        switch (colorNumber) {
            case 1: drawTool.setCurrentColor(255, 215, 0, 255);
            break;
            case 2: drawTool.setCurrentColor(255, 44, 44, 255);
            break;
            case 3: drawTool.setCurrentColor(191, 61, 255, 255);
            break;
            case 4: drawTool.setCurrentColor(34, 139, 34, 255);
            break;
            case 5: drawTool.setCurrentColor(72, 118, 255, 255);
            break;

        }
        drawTool.drawFilledRectangle(x,y,width,height);
    }
    public int getColorNumber() {
        return colorNumber;
    }
    public void jump(){
        if(jump==true&& timer>= 10) {
            x = Math.random() * 1000;
            y = Math.random() * 624;
        }
    }
    public void jumpOut(){
        y= Math.random()*-700;
        x= Math.random()*-700;
    }
    public void update(double dt){
        timer= timer+1*dt;
        if( timer >=10){
            jump();
            timer=0;
        }

    }
    public void setJump(boolean amount){
        jump=amount ;

    }
}

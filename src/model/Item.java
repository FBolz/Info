package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Item extends GraphicalObject {
    private int colorNumber;
    private double timer;

    public Item(int colorNumber) {
        this.colorNumber=colorNumber;
        x= Math.random()*1600;
        y= Math.random()*1024;
        height= 40;
        width=40;
        timer=0;


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
        x= Math.random()*1600;
        y= Math.random()*1024;
    }
    public void update(double dt){
        timer= timer+1*dt;
        if( timer >=10){
            jump();
            timer=0;
        }

    }
}

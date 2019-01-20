package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Options extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawFilledRectangle(600,100,300,200);
        drawTool.drawFilledRectangle(600,400,300,200);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawText(750,200,"Music");
        drawTool.drawText(750,500,"Life");
        drawTool.drawText(240,900,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=100 && e.getY()<=300){
            clicked = "music";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=400 && e.getY()<=600){
            clicked = "life";
        }
        if(e.getX()>= 40 && e.getX()<=440 && e.getY()>=850 && e.getY()<=950){
            clicked = "back";
        }
    }

    public String getClicked() {
        return clicked;
    }

    public void setClicked(String clicked) {
        this.clicked = clicked;
    }

}

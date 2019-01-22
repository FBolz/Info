package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Start extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(600,100,300,200);
        drawTool.drawFilledRectangle(600,400,300,200);
        drawTool.drawFilledRectangle(600,700,300,200);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,55);
        drawTool.drawText(680,200,"Start");
        drawTool.drawText(650,500,"Options");
        drawTool.drawText(650,800,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=100 && e.getY()<=300){
            clicked = "start";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=400 && e.getY()<=600){
            clicked= "menu";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=700 && e.getY()<=900){
            System.exit(0);
        }
    }

    public String getClicked() {
        return clicked;
    }

    public void setClicked(String clicked) {
        this.clicked = clicked;
    }
}

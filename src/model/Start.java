package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Start extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(600,50,300,200);
        drawTool.drawFilledRectangle(600,300,300,200);
        drawTool.drawFilledRectangle(600,550,300,200);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,55);
        drawTool.drawText(650,150,"Start");
        drawTool.drawText(650,400,"Options");
        drawTool.drawText(650,650,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=50 && e.getY()<=250){
            clicked = "start";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=300 && e.getY()<=500){
            clicked= "menu";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=550 && e.getY()<=7500){
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

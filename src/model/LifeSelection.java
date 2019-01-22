package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class LifeSelection extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(600,100,300,100);
        drawTool.drawFilledRectangle(600,250,300,100);
        drawTool.drawFilledRectangle(600,400,300,100);
        drawTool.drawFilledRectangle(600,550,300,100);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,35);

        drawTool.drawText(670,150,"10 Leben");
        drawTool.drawText(670,300,"5 Leben");
        drawTool.drawText(670,450,"4 Leben");
        drawTool.drawText(670,600,"3 Leben");
        drawTool.formatText("", 3,40);
        drawTool.drawText(200,910,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=100 && e.getY()<=200){
            clicked = "10";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=250 && e.getY()<=350){
            clicked = "5";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=400 && e.getY()<=500){
            clicked = "4";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=550 && e.getY()<=650){
            clicked = "3";
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

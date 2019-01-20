package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class MusicSelection extends GraphicalObject {

    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawFilledRectangle(600,100,300,100);
        drawTool.drawFilledRectangle(600,250,300,100);
        drawTool.drawFilledRectangle(600,400,300,100);
        drawTool.drawFilledRectangle(600,550,300,100);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawText(700,150,"Flags");
        drawTool.drawText(700,300,"Doomed");
        drawTool.drawText(700,450,"Great Missions");
        drawTool.drawText(700,600,"Battle Theme");
        drawTool.drawText(240,900,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=100 && e.getY()<=200){
            clicked = "flags";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=250 && e.getY()<=350){
            clicked = "doomed";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=400 && e.getY()<=500){
            clicked = "great";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=550 && e.getY()<=650){
            clicked = "battle";
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

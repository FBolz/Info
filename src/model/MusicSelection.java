package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class MusicSelection extends GraphicalObject {

    private String clicked;
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(600,50,300,100);
        drawTool.drawFilledRectangle(600,200,300,100);
        drawTool.drawFilledRectangle(600,350,300,100);
        drawTool.drawFilledRectangle(600,500,300,100);
        drawTool.drawFilledRectangle(600,650,300,100);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,40);
        drawTool.drawText(670,100,"Flags");
        drawTool.drawText(660,250,"Doomed");
        drawTool.drawText(610,400,"Great Missions");
        drawTool.drawText(620,550,"Battle Theme");
        drawTool.drawText(620,700,"Spacetime");
        drawTool.drawText(200,910,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=50 && e.getY()<=150){
            clicked = "flags";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=200 && e.getY()<=300){
            clicked = "doomed";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=250 && e.getY()<=450){
            clicked = "great";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=500 && e.getY()<=600){
            clicked = "battle";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=650 && e.getY()<=750){
            clicked = "space";
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

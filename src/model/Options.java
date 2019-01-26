package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Options extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,60);
        drawTool.drawText(670,350,"Music");
        drawTool.drawText(670,580,"Life");
        drawTool.formatText("", 3,40);
        drawTool.drawText(200,910,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 670 && e.getX()<=840 && e.getY()>=305 && e.getY()<=355){
            clicked = "music";
        }
        if(e.getX()>= 670 && e.getX()<=785 && e.getY()>=535 && e.getY()<=585){
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

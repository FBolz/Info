package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class LifeSelection extends GraphicalObject {
    private int clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,35);
        drawTool.drawText(670,150,"15 Leben");
        drawTool.drawText(670,300,"10 Leben");
        drawTool.drawText(670,450,"5 Leben");
        drawTool.drawText(670,600,"1 Leben");
        drawTool.formatText("", 3,40);
        drawTool.drawText(200,910,"Back");
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 670 && e.getX()<=820 && e.getY()>=120 && e.getY()<=200){
            clicked = 15;
        }
        if(e.getX()>= 670 && e.getX()<=820 && e.getY()>=270 && e.getY()<=350){
            clicked = 10;
        }
        if(e.getX()>= 670 && e.getX()<=800 && e.getY()>=420 && e.getY()<=500){
            clicked = 5;
        }
        if(e.getX()>= 670 && e.getX()<=800 && e.getY()>=570 && e.getY()<=650){
            clicked = 1;
        }
        if(e.getX()>= 40 && e.getX()<=440 && e.getY()>=850 && e.getY()<=950){
            clicked = -1;
        }
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }
}

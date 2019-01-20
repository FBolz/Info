package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class End extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawFilledRectangle(600,100,300,200);
        drawTool.drawFilledRectangle(600,700,300,200);
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawText(750,200,"Restart");
        drawTool.drawText(750,800,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=100 && e.getY()<=300){
            clicked = "restart";
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

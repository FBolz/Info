package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Start extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(650,110,130,45);
        drawTool.drawFilledRectangle(650,360,210,45);
        drawTool.drawFilledRectangle(650,610,110,45);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,55);
        drawTool.drawText(650,150,"Start");
        drawTool.drawText(650,400,"Options");
        drawTool.drawText(650,650,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 650 && e.getX()<=780 && e.getY()>=110 && e.getY()<=155){
            clicked = "start";
        }
        if(e.getX()>= 650 && e.getX()<=860 && e.getY()>=360 && e.getY()<=405){
            clicked= "menu";
        }
        if(e.getX()>= 650 && e.getX()<=760 && e.getY()>=610 && e.getY()<=655){
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

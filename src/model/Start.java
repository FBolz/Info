package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class Start extends GraphicalObject {
    private String clicked;
    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,55);
        drawTool.drawText(650,340,"Start");
        drawTool.drawText(650,440,"Options");
        drawTool.drawText(650,540,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 650 && e.getX()<=780 && e.getY()>=310 && e.getY()<=355){
            clicked = "start";
        }
        if(e.getX()>= 650 && e.getX()<=860 && e.getY()>=410 && e.getY()<=455){
            clicked= "menu";
        }
        if(e.getX()>= 650 && e.getX()<=760 && e.getY()>=510 && e.getY()<=555){
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

package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class End extends GraphicalObject {
    private String clicked;
    private String player;
    public End(String player) {
    this.player=player;
    }

    public void draw(DrawTool drawTool){
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(700,300,210,45);
        drawTool.drawFilledRectangle(700,500,125,45);
        drawTool.formatText("", 3,40);
        drawTool.setCurrentColor(255,255,255,255);

        drawTool.drawText(650,150,player+" won!");
        drawTool.formatText("", 3,60);
        drawTool.drawText(700,345,"Restart");
        drawTool.drawText(700,545,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 700 && e.getX()<=910 && e.getY()>=300 && e.getY()<=345){
            clicked = "restart";
        }
        if(e.getX()>= 700 && e.getX()<=825 && e.getY()>=500 && e.getY()<=550){
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

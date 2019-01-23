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
        drawTool.drawFilledRectangle(600,300,300,200);
        drawTool.drawFilledRectangle(600,400,300,200);
        drawTool.formatText("", 3,40);
        drawTool.setCurrentColor(255,255,255,255);

        drawTool.drawText(650,150,player+" won!");
      /*  drawTool.drawText(700,200,"Restart");
        drawTool.drawText(700,500,"End");*/
        drawTool.formatText("", 3,60);
        drawTool.drawText(700,350,"Restart");
        drawTool.drawText(700,450,"End");
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=250 && e.getY()<=450){
            clicked = "restart";
        }
        if(e.getX()>= 600 && e.getX()<=900 && e.getY()>=350 && e.getY()<=550){
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

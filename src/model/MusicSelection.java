package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.event.MouseEvent;

public class MusicSelection extends GraphicalObject {

    private String clicked;
    public void draw(DrawTool drawTool){

        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawFilledRectangle(40,850,400,100);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,40);
        drawTool.drawText(620,100,"Flags");
        drawTool.drawText(620,250,"Doomed");
        drawTool.drawText(620,400,"Great Missions");
        drawTool.drawText(620,550,"Battle Theme");
        drawTool.drawText(620,700,"Spacetime");
        drawTool.drawText(200,910,"Back");
    }


    public void mouseReleased(MouseEvent e){
        if(e.getX()>= 620 && e.getX()<=725 && e.getY()>=70 && e.getY()<=105){
            clicked = "flags";
        }
        if(e.getX()>= 620 && e.getX()<=780 && e.getY()>=220 && e.getY()<=255){
            clicked = "doomed";
        }
        if(e.getX()>= 620 && e.getX()<=905 && e.getY()>=370 && e.getY()<=405){
            clicked = "great";
        }
        if(e.getX()>= 620 && e.getX()<=870 && e.getY()>=520 && e.getY()<=555){
            clicked = "battle";
        }
        if(e.getX()>= 620 && e.getX()<=820 && e.getY()>=670 && e.getY()<=705){
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

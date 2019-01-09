package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Player extends GraphicalObject {
    private int KeyToGoUp;
    private int KeyToGoDown;
    private int KeyToGoLeft;
    private int KeyToGoRight;
    private UIController uiController;


    public Player(UIController uiController,int  KeyToGoUp, int KeyToGoDown, int KeyToGoLeft, int KeyToGoRight, String path, int x, int y){
        this.KeyToGoDown=KeyToGoDown;
        this.KeyToGoLeft = KeyToGoLeft;
        this.KeyToGoRight = KeyToGoRight;
        this.KeyToGoUp = KeyToGoUp;
        this.x=x;
        this.y=y;
        this.uiController = uiController;
        createAndSetNewImage(path);
    }

    public void draw(DrawTool  drawTool){
        drawTool.drawImage(getMyImage(),x,y);
    }

    public void update(double dt){
        if(y<=950-getMyImage().getHeight()&&uiController.isKeyDown(KeyToGoDown)){
            y+=100*dt;
        }
        if(y>=0 && uiController.isKeyDown(KeyToGoUp)){
            y-=100*dt;
        }

        if(x<=1550-getMyImage().getWidth()&&uiController.isKeyDown(KeyToGoRight)){
            x+=100*dt;
        }
        if(x>=0 && uiController.isKeyDown(KeyToGoLeft)){
            x-=100*dt;
        }
    }

}

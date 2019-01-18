package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.image.BufferedImage;

public class Item extends GraphicalObject {

    private int colorNumber;
    private double timer;
    private boolean jump;
    private Player player1;
    private Player player2;

    public Item(int colorNumber,Player player1,Player player2) {
        this.colorNumber=colorNumber;
        x = Math.random() * 1000;
        y = Math.random() * 1004;
        height= 30;
        width=30;
        timer=0;
        jump=true;
        this.player1= player1;
        this.player2=player2;
        switch (colorNumber) {
            case 1://gelb
                createAndSetNewImage("assets/images/objects/yellow planet.png" );
                // drawTool.setCurrentColor(255, 215, 0, 255);
                break;
            case 2:
                createAndSetNewImage("assets/images/objects/redplanet.png" );
                //drawTool.setCurrentColor(255, 44, 44, 255);
                break;
            case 3:
                createAndSetNewImage("assets/images/objects/purpleplanet.png" );
                //drawTool.setCurrentColor(191, 61, 255, 255);
                break;
            case 4: createAndSetNewImage("assets/images/objects/green planet.png" );
                // drawTool.setCurrentColor(34, 139, 34, 255);
                break;
            case 5:createAndSetNewImage("assets/images/objects/blue planet.png" );
                //drawTool.setCurrentColor(72, 118, 255, 255);
                break;

        }
    }

    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
    }

    public int getColorNumber() {
        return colorNumber;
    }
    public void jump(){
        if(jump==true&& timer>= 10) {
            x = Math.random() * 1350;
            y = Math.random() * 1000;
            if(this.collidesWith(player1) || this.collidesWith(player2)){
                x = Math.random() * 1350;
                y = Math.random() * 1000;
            }
        }
    }
    public void jumpOut(){
      y= Math.random()*-700;
      x= Math.random()*-700;
    }
    public void update(double dt){
        timer= timer+1*dt;
        if( timer >=10){
            jump();
            timer=0;
        }

    }
    public void setJump(boolean amount){
        jump=amount ;

    }
}

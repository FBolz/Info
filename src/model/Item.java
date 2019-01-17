package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

import java.awt.image.BufferedImage;

public class Item extends GraphicalObject {
    private int colorNumber;
    private double timer;
    private boolean jump;
    private BufferedImage image;

    public Item(int colorNumber) {
        this.colorNumber=colorNumber;
        jump();
        height= 30;
        width=30;
        timer=0;
        jump=true;


    }

    public void draw(DrawTool drawTool) {
        switch (colorNumber) {
            case 1://gelb
                image= createNewImage("assets/images/objects/yellow planet.png" );
                        // drawTool.setCurrentColor(255, 215, 0, 255);
            break;
            case 2:
                image= createNewImage("assets/images/objects/redplanet.png" );
                //drawTool.setCurrentColor(255, 44, 44, 255);
            break;
            case 3:
                image= createNewImage("assets/images/objects/purpleplanet.png" );
                //drawTool.setCurrentColor(191, 61, 255, 255);
            break;
            case 4: image= createNewImage("assets/images/objects/green planet.png" );
                // drawTool.setCurrentColor(34, 139, 34, 255);
            break;
            case 5:image= createNewImage("assets/images/objects/blue planet.png" );
                //drawTool.setCurrentColor(72, 118, 255, 255);
            break;

        }
        drawTool.drawImage(image,x,y);
    }
    public int getColorNumber() {
        return colorNumber;
    }
    public void jump(){
        if(jump==true&& timer>= 10) {
            x = Math.random() * 1000;
            y = Math.random() * 624;
        }
    }
    public void jumpOut(){
        y= Math.random()*-700;
        x= Math.random()*-700;
    }
    public void update(double dt){
        timer= timer+0.5*dt;
        if( timer >=10){
            jump();
            timer=0;
        }

    }
    public void setJump(boolean amount){
        jump=amount ;

    }
}

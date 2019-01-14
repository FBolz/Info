package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;
import control.ProgramController;

public class Player extends GraphicalObject {
    private int KeyToGoUp;
    private int KeyToGoDown;
    private int KeyToGoLeft;
    private int KeyToGoRight;
    private int KeyToShoot;
    private boolean shoot;
    private double speed;
    private boolean fastShoot;
    private int live;
    private boolean collision;
    private String direction;
    private String facing;
    private UIController uiController;
    private double spritetimer;


    public Player(UIController uiController, int KeyToGoUp, int KeyToGoDown, int KeyToGoLeft, int KeyToGoRight, int KeyToShoot, double speed,String path, int x, int y,int live,String facing) {
        this.KeyToGoDown = KeyToGoDown;
        this.KeyToGoLeft = KeyToGoLeft;
        this.KeyToGoRight = KeyToGoRight;
        this.KeyToGoUp = KeyToGoUp;
        this.KeyToShoot = KeyToShoot;
        this.x = x;
        this.y = y;
        this.live=live;
        this.speed=speed;
        this.uiController = uiController;
        this.facing=facing;
        createAndSetNewImage(path);
    }

    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(), x, y);
        drawTool.drawText(x+60,y+10,"Life: "+String.valueOf(live));
        if(spritetimer> 0.5){
            createAndSetNewImage("assets/images/Player1-1.png");
        }

        if(spritetimer < 0.5){
            createAndSetNewImage("assets/images/Player1-2.png");
        }
        if(spritetimer> 1){
            spritetimer = 0;
        }
    }

    public void update(double dt) {
       spritetimer= spritetimer+dt;
        if(!uiController.isKeyDown(KeyToGoDown)&&!uiController.isKeyDown(KeyToGoUp)&& !uiController.isKeyDown(KeyToGoRight) && !uiController.isKeyDown(KeyToGoLeft)&&!collision) {
            direction = "neutral";
        }
        if (y <= 950 - getMyImage().getHeight() && uiController.isKeyDown(KeyToGoDown)&& !collision) {
            y += speed * dt;
            direction="down";
            facing=direction;
        }else if(y <= 950 - getMyImage().getHeight() &&direction.equals("down")&&collision) {
            y-=speed;
            if(y<=0){
                y=0;
            }
        }
        if (y >= 0 && uiController.isKeyDown(KeyToGoUp)&&!collision) {
            y -= speed * dt;
            direction="up";
            facing=direction;
        }else if(y >= 0 && direction.equals("up") && collision){
            y+=speed;
            if(y>= 950 - getMyImage().getHeight()){
                y= 950 - getMyImage().getHeight();
            }
        }

        if (x <= 1550 - getMyImage().getWidth() && uiController.isKeyDown(KeyToGoRight)&&!collision) {
            x += speed * dt;
            direction= "right";
            facing=direction;
        }else if(x <= 1550 - getMyImage().getWidth() &&direction.equals("right") &&collision) {
            x-=speed;
            if(x<=0){
                x=0;
            }
        }
        if (x >= 0 && uiController.isKeyDown(KeyToGoLeft)&&!collision) {
            x -= speed * dt;
            direction= "left";
            facing=direction;
        }else if(x >= 0 && direction.equals("left") &&collision) {
            x+=speed;
            if(x >= 1550 - getMyImage().getWidth()){
                x= 1550 - getMyImage().getWidth();
            }
        }
        if (uiController.isKeyDown(KeyToShoot)) {
            shoot = true;
        } else {
            shoot = false;
        }

    }

    public boolean getShoot() {
        return shoot;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean getFastShoot() {
        return fastShoot;
    }

    public void setFastShoot(boolean fastShoot) {
        this.fastShoot = fastShoot;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public String getFacing() {
        return facing;
    }
}

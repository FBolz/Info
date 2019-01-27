package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;
import control.ProgramController;

import java.awt.image.BufferedImage;

public class Player extends GraphicalObject {
    private int KeyToGoUp;
    private int KeyToGoDown;
    private int KeyToGoLeft;
    private int KeyToGoRight;
    private int KeyToShoot;
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private BufferedImage PlayerUp1;
    private BufferedImage PlayerUp2;
    private BufferedImage PlayerDown1;
    private BufferedImage PlayerDown2;
    private BufferedImage PlayerRight1;
    private BufferedImage PlayerRight2;
    private BufferedImage PlayerLeft1;
    private BufferedImage PlayerLeft2;
    private BufferedImage activeImage1;
    private BufferedImage activeImage2;

    private boolean shoot;
    private double speed;
    private boolean fastShoot;
    private int live;
    private boolean collision;
    private String direction;
    private String facing;
    private UIController uiController;
    private double spritetimer;
    private boolean strongShoot;
    private double powerUpTimer;
    private boolean invert;
    private String name;



    public Player(UIController uiController, int KeyToGoUp, int KeyToGoDown, int KeyToGoLeft, int KeyToGoRight, int KeyToShoot, double speed, int x, int y, int live, String facing,String name) {
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
        this.name=name;

        PlayerUp1 = createNewImage("assets/images/Player/Player1-1Up.png");
        PlayerUp2 = createNewImage("assets/images/Player/Player1-2Up.png");
        PlayerDown1 =  createNewImage("assets/images/Player/Player1-1Down.png");
        PlayerDown2 = createNewImage("assets/images/Player/Player1-2Down.png");
        PlayerRight1 = createNewImage("assets/images/Player/Player1-1Right.png");
        PlayerRight2 = createNewImage("assets/images/Player/Player1-2Right.png");
        PlayerLeft1 = createNewImage("assets/images/Player/Player1-1Left.png");
        PlayerLeft2 = createNewImage("assets/images/Player/Player1-2Left.png");

        switch (facing) {
            case "left":
                activeImage1=PlayerLeft1;
                activeImage2=PlayerLeft2;
                break;
            case "right":
                activeImage1=PlayerRight1;
                activeImage2=PlayerRight2;
                break;
            case "up":
                activeImage1=PlayerUp1;
                activeImage2=PlayerUp2;
                break;
            case "down":
                activeImage1=PlayerDown1;
                activeImage2=PlayerDown2;
                break;
            default:
                break;
        }

        setMyImage(activeImage1);
        powerUpTimer=-1;
        invert=false;

    }

    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(), x, y);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.formatText("", 3,20);

        drawTool.drawText(x+60,y-30,"Life: "+(live));
        drawTool.drawText(x+60,y-50,name);
        if (powerUpTimer>=0) {
            drawTool.drawText(x + 60, y - 10, "PowerUp Timer: " + Math.round(powerUpTimer));
        }
        if(spritetimer> 0.5){
            setMyImage(activeImage1);
        }

        if(spritetimer < 0.5){
            setMyImage(activeImage2);
        }
        if(spritetimer> 1){
            spritetimer = 0;
        }
    }

    public void update(double dt) {
        if (invert && upKey!=KeyToGoDown){
            upKey=KeyToGoDown;
            downKey=KeyToGoUp;
            leftKey=KeyToGoRight;
            rightKey=KeyToGoLeft;
        }else if (!invert && upKey!=KeyToGoUp){
            upKey=KeyToGoUp;
            downKey=KeyToGoDown;
            leftKey=KeyToGoLeft;
            rightKey=KeyToGoRight;
        }

        spritetimer= spritetimer+dt;
        if(!uiController.isKeyDown(downKey)&&!uiController.isKeyDown(upKey)&& !uiController.isKeyDown(rightKey) && !uiController.isKeyDown(leftKey)&&!collision) {
            direction = "neutral";
        }
        if (y <= 950 - getMyImage().getHeight() && uiController.isKeyDown(downKey)&& !collision) {
            y += speed * dt;
            direction="down";
            facing=direction;
            activeImage1=PlayerDown1;
            activeImage2=PlayerDown2;
        }else if(y <= 950 - getMyImage().getHeight() &&direction.equals("down")&&collision) {
            y-=speed;
            if(y<=0){
                y=0;
            }
        }
        if (y >= 0 && uiController.isKeyDown(upKey)&&!collision) {
            y -= speed * dt;
            direction="up";
            facing=direction;
            activeImage1=PlayerUp1;
            activeImage2=PlayerUp2;
        }else if(y >= 0 && direction.equals("up") && collision){
            y+=speed;
            if(y>= 950 - getMyImage().getHeight()){
                y= 950 - getMyImage().getHeight();
            }
        }

        if (x <= 1550 - getMyImage().getWidth() && uiController.isKeyDown(rightKey)&&!collision) {
            x += speed * dt;
            direction= "right";
            facing=direction;
            activeImage1=PlayerRight1;
            activeImage2=PlayerRight2;
        }else if(x <= 1550 - getMyImage().getWidth() &&direction.equals("right") &&collision) {
            x-=speed;
            if(x<=0){
                x=0;
            }
        }
        if (x >= 0 && uiController.isKeyDown(leftKey)&&!collision) {
            x -= speed * dt;
            direction= "left";
            facing=direction;
            activeImage1=PlayerLeft1;
            activeImage2=PlayerLeft2;

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

    public boolean getStrongShoot() {
        return strongShoot;
    }

    public void setStrongShoot(boolean strongShoot) {
        this.strongShoot = strongShoot;
    }

    public double getPowerUpTimer() {
        return powerUpTimer;
    }

    public void setPowerUpTimer(double powerUpTimer) {
        this.powerUpTimer = powerUpTimer;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

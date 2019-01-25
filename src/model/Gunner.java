package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.view.DrawTool;

public class Gunner extends Enemy
{
    private double timer;
    private double spritetimer;
    private Projectile projectile;
    private UIController uic;
    private double lasertimer;
    private String direction;

    public Gunner(UIController uic,Projectile projectile) {
        super("assets/images/Kanone-right.png", 1, 1, 100);
        direction = "right";
        timer = 0;
        this.uic = uic;
        this.projectile = projectile;
    }

    @Override
    public void draw(DrawTool drawTool)
    {
        super.draw(drawTool);
    }

    public void update(double dt){
        timer = timer + dt ;
        spritetimer = spritetimer + dt;
        lasertimer = lasertimer +dt;
        if(timer < 3) {
            y = y + speed * dt;
        }
        if(timer> 3) {
            y = y - speed * dt;
        }
        if(lasertimer  >= 3){
            projectile = new Projectile(x,y,getRandomDirection(),uic);
            projectile.setActive(true);
            uic.registerObject(projectile);
            lasertimer = 0;
        }
        if(timer > 6) {
            timer = 0;
        }

     }
     public Projectile getProjectile(){
        return projectile;

     }
     public String getRandomDirection(){
        int i = (int)(Math.random()*2)+1;
        if(i == 2){
            direction = "left";
            createAndSetNewImage("assets/images/Kanone-left.png");
        }
         if(i == 1){
             direction = "right";
             createAndSetNewImage("assets/images/Kanone-right.png");
         }
         return direction;
     }

    }


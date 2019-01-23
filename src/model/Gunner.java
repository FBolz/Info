package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.view.DrawTool;

public class Gunner extends Enemy
{
    private double timer;
    private double spritetimer;
    private Projectile projectile;
    private UIController uic;
    public Gunner(UIController uic,Projectile projectile) {
        super("assets/images/objects/house.png", 1, 2, 0);
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
        timer = timer + dt;
        spritetimer = spritetimer + dt;
        if(timer < 3) {
            y = y + speed * dt;
        }
        if(timer> 3) {
            y = y - speed * dt;
        }
        if(timer  == 2){
            projectile.setX(x);
            projectile.setX(y);
            projectile.setDirection("right");
            uic.registerObject(projectile);
        }
        if(timer > 6) {
            timer = 0;
        }

    }
    }


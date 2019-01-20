package model;

import akkgframework.view.DrawTool;

public class Gunner extends Enemy
{
    private double timer;
    private double spritetimer;
    public Gunner() {
        super("assets/images/objects/house.png", 1, 2, 0);
        timer = 0;
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

        if(timer > 6) {
            timer = 0;
        }

    }
    }


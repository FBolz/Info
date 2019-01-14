package model;

import akkgframework.view.DrawTool;

public class Jumba extends  Enemy {

    private double timer;
    private int random;
    private double spritetimer;



    public Jumba(){
        super("assets/images/Jumba.png",3,3,50);
        x = 60;
        y = 300;
        timer = 0;
        random = (int) Math.random()*4+1;

    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        if(spritetimer> 0.5){
            createAndSetNewImage("assets/images/Jumba-richtig.png");
        }

        if(spritetimer < 0.5){
            createAndSetNewImage("assets/images/Jumba3.png");
        }
        if(spritetimer> 1){
            spritetimer = 0;
        }


    }
    public void update(double dt){
        timer = timer + dt;
        spritetimer = spritetimer + dt;
        if(timer < 3) {
           x = x + speed * dt;
         }
         if(timer> 3) {
         x = x - speed * dt;
         }

         if(timer > 6) {
             timer = 0;
         }

    }





    }


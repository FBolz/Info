package model;

import akkgframework.control.fundamental.UIController;
import akkgframework.view.DrawTool;

public class Gunner extends Enemy
{
    //Attribute
    private double timer;
    private double spritetimer;
    private Projectile projectile;
    private UIController uic;
    private double lasertimer;
    private String direction;
// Konstruktor
    public Gunner(UIController uic,Projectile projectile) {
        super("assets/images/Kanone-right.png", 1, 1, 100);
        direction = "right";
        timer = 0;
        this.uic = uic;
        this.projectile = projectile;
    }

    /**
     * Zeichnung
     * @param drawTool Zeichenwerkzeug
     */
    @Override
    public void draw(DrawTool drawTool)
    {
        super.draw(drawTool);
    }

    /**
     * Bewegung des Gunners und Schuss
     * @param dt Zeit
     */
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

    /**
     * getter methode des Projectiles
     * @return
     */
     public Projectile getProjectile(){
        return projectile;

     }

    /**
     * Bestimmung der Richtung des Abschusses des Projectiles und des Sprites
     * @return
     */
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


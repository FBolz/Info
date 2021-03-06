package model;

import akkgframework.view.DrawTool;

public class Follower extends Enemy {
// Attribute
    private Player target;
    private double timer;
    private int counter;
    private String path1;
    private String path2;
    private double spritetimer;

    /**
     * Konstruktor
     */
    public Follower() {
        super("assets/images/Follower-Up-1.png",1,1,1);
        x = 400;
        y = 300;
        timer = 0;
        counter = 0;
        spritetimer = 0;
        path1 = "assets/images/Follower-Up-1.png";
        path2 = "assets/images/Follower-Up-2.png";
    }
    /**
     * Zeichnet den Follower mit Animation
     * @param drawTool Zeichenwerkzeug
     */
    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(), x, y);
        if(spritetimer < 0.5){
          createAndSetNewImage(path1);
        }
        if(spritetimer > 0.5){
            createAndSetNewImage(path2);
        }
        if(spritetimer>= 1){
            spritetimer = 0;
        }
    }

    /**
     * Bewegung des Followers in Richtung seines Targets
     * @param dt Zeit
     */
    @Override
    public void update(double dt) {
         timer= timer +dt;
         spritetimer = spritetimer + dt;

         if(timer >= 5){
             timer = 0;
             speed++ ;
             counter++;
         }
         if(counter >3){
             speed = 1;
             counter = 0;
         }

            if (target.getX() < this.getX()) {
                x = x - speed;
                path1 = "assets/images/Follower-Left-1.png";
                path2 =  "assets/images/Follower-Left-2.png";
            }
            if (target.getX() > this.getX()) {
                x = x + speed;
                path1 = "assets/images/Follower-Right-1.png";
                path2 =  "assets/images/Follower-Right-2.png";
            }

            if (target.getY() < this.getY())
            {
                y = y - speed;
                path1 = "assets/images/Follower-Up-1.png";
                path2 = "assets/images/Follower-Up-2.png";

            }
            if (target.getY() > this.getY()) {

                    y = y + speed;
                    path1 = "assets/images/Follower-Down-1.png";
                    path2 = "assets/images/Follower-Down-2.png";
                }
    }



   public int random(){
   int i =  (int)(Math.random())*2+1;
     return i;
    }

    /**
     * Änderung des Targets
     * @param ptarget der zu verfolgende Spieler
     */
    public void setTarget(Player ptarget){
        target = ptarget;
    }
}

package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public abstract class Enemy extends GraphicalObject {

    protected int life;
    protected int damage;
    protected int speed;
    protected boolean enemyIsActive;

    public Enemy(String s, int plife, int dmg, int sp){
        this.createAndSetNewImage(s);
        life = plife;
        damage = dmg;
        speed = sp;
        enemyIsActive = true;
    }

    public void draw(DrawTool drawTool){
        drawTool.drawImage(getMyImage(),x,y);
    }

    public boolean isEnemyIsActive() {
        return enemyIsActive;
    }

    public void setEnemyIsActive(boolean enemyIsActive) {
        this.enemyIsActive = enemyIsActive;
    }

    public void stop(){
        speed = 0;
    }
}

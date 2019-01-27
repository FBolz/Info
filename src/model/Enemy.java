package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public abstract class Enemy extends GraphicalObject {
//Attribute
    protected int life;
    protected int damage;
    protected int speed;
    protected boolean enemyIsActive;

    /**
     *
     * @param s das zu zeichnende Bild
     * @param plife Leben
     * @param dmg Schaden
     * @param sp Schnelligkeit
     */
    public Enemy(String s, int plife, int dmg, int sp){
        this.createAndSetNewImage(s);
        life = plife;
        damage = dmg;
        speed = sp;
        enemyIsActive = true;
    }
    /**
     * Zeichnet den Gegner
     * @param drawTool Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool){
        drawTool.drawImage(getMyImage(),x,y);
    }

    /**
     * Wiedergabe von dem Boolean wert zu Bewältigung der Kollisionen
     * @return
     */
    public boolean isEnemyIsActive() {
        return enemyIsActive;
    }

    /**
     * Änderung des Boolean Wertes
     * @param enemyIsActive
     */

    public void setEnemyIsActive(boolean enemyIsActive) {
        this.enemyIsActive = enemyIsActive;
    }

}

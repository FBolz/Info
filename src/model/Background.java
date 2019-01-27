package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Background extends GraphicalObject
{
    // Atribute
    private String playBck;
    private String end;



    public Background()
    {
        x = 0;
        y = 0;
        playBck = "assets/images/thumb-1920-885542.png";
        end = "assets/images/Game Over.png";
        createAndSetNewImage(playBck);

    }

    @Override

    /**
     * Zeichnet die Texte für die verschiedenen Möglichkeiten
     * @param drawTool Zeichenwerkzeug
     */
    public void draw(DrawTool drawTool)
    {
        drawTool.drawImage(getMyImage(),x,y);

}

    /**
     * Änderung des Bildes
     * @param i steht für den jeweiligen background
     *
     */
    public void setBackgorund(int i){
    switch(i)
    {
        case 1:
            createAndSetNewImage(playBck);
            break;
        case 2:
            createAndSetNewImage(end);
            break;

    }
}
}
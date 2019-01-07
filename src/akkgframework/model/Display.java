package akkgframework.model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

/**
 * Zeigt einen Text im Fenster an.
 */
public class Display extends GraphicalObject {

    //Attribute
    private String meinText;
    private int x, y;

    /**
     * Erzeugt eine Textanzeige
     * @param deinText Der anzuzeigende Text.
     * @param x X-Koordinate der Anzeige.
     * @param y Y-Koordinate der Anzeige.
     */
    public Display(String deinText, int x, int y){
        meinText = deinText;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.setCurrentColor(0,0,0,255);
        drawTool.drawText(x,y,meinText);
    }

    /**
     * Verändert den anzuzeigenden Text
     * @param neuerText
     */
    public void setText(String neuerText){
        meinText = neuerText;
    }

}

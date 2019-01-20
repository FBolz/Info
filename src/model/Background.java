package model;

import akkgframework.model.fundamental.GraphicalObject;
import akkgframework.view.DrawTool;

public class Background extends GraphicalObject
{
    public Background()
    {
        x = 0;
        y = 0;
        createAndSetNewImage("assets/images/thumb-1920-885542.png");
    }

    @Override
    public void draw(DrawTool drawTool)
    {
        drawTool.drawImage(getMyImage(),x,y);
    }
}

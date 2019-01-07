package akkgframework.model.scenario;

import akkgframework.model.fundamental.Tileset;

import java.awt.image.BufferedImage;

public enum ScenarioCharacterData {
    PEASANT("assets/images/tilesets/scenario/characters.png",3,0,32,32);


    private final BufferedImage[] walkDown, walkLeft, walkRight, walkUp;

    ScenarioCharacterData(String path, int startTileX, int startTileY, int tileWidth, int tileHeight){
        Tileset graphics = new Tileset(path,tileWidth,tileHeight);
        walkDown = new BufferedImage[3];
        walkLeft = new BufferedImage[3];
        walkRight = new BufferedImage[3];
        walkUp = new BufferedImage[3];
        for(int i = 0; i<3; i++){
            walkDown[i] = graphics.getTile(startTileX+i,0);
            walkLeft[i] = graphics.getTile(startTileX+i,1);
            walkRight[i] = graphics.getTile(startTileX+i,2);
            walkUp[i] = graphics.getTile(startTileX+i,3);
        }
    }

    public BufferedImage[] getWalkDown() {
        return walkDown;
    }

    public BufferedImage[] getWalkLeft() {
        return walkLeft;
    }

    public BufferedImage[] getWalkRight() {
        return walkRight;
    }

    public BufferedImage[] getWalkUp() {
        return walkUp;
    }
}

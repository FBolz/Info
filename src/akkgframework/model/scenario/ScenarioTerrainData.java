package akkgframework.model.scenario;

import akkgframework.control.scenario.MapController;
import akkgframework.model.fundamental.Pair;
import akkgframework.model.fundamental.Tileset;

import java.awt.image.BufferedImage;


public enum ScenarioTerrainData {

    GRAS, WATER, FOREST;

    public enum Tile {
        WATER_WAVES(WATER, 8,0,"Wasser mit Wellen","assets/images/tilesets/scenario/complete_tileset.png",32,32),
        TREE_STUMPS(GRAS,9,3,"Baumstümpfe","assets/images/tilesets/scenario/complete_tileset.png",32,32),
        FLOWERS_VARIATION1(GRAS,7,3,"Blumen","assets/images/tilesets/scenario/complete_tileset.png",32,32),
        FLOWERS_VARIATION2(GRAS,8,3,"Blumen","assets/images/tilesets/scenario/complete_tileset.png",32,32),
        GRAS_BLANK(GRAS,1,0,"Gras","assets/images/tilesets/scenario/complete_tileset.png",32,32);

        public final BufferedImage img;
        public final String name;
        public final ScenarioTerrainData type;

        Tile(ScenarioTerrainData type, int i, int j, String name, String path, int tilesWidth, int tilesHeight){
            this.type = type;
            Tileset graphics = new Tileset(path,tilesWidth,tilesHeight);
            img = graphics.getTile(i,j);
            this.name = name;
        }
    }

    /**
     * Legt die Tilesetteile für bestimmte Terraintypen fest.
     */
    public enum TileSet {
        WATERSET(WATER,"Water","assets/images/tilesets/scenario/complete_tileset.png",32,32,
                //1MIDDLE           2SINGLE
                new Pair(0,0),new Pair(5,1),
                //3BORDER_LEFT      4BORDER_RIGHT       5BORDER_UP          6BORDER_DOWN
                new Pair(9,0),new Pair(7,0),new Pair(1,1),new Pair(5,0),
                //7CORNER_TOP_LEFT  8CTL_EMPTY          9CORNER_BOT_LEFT    10CBL_EMPTY         11CORNER_TOP_RIGHT  12CTR_EMPTY         13CORNER_BOT_RIGHT  14CORNER_BOT_RIGHT_EMPTY
                new Pair(4,0),new Pair(4,0),new Pair(0,1),new Pair(0,1),new Pair(6,0),new Pair(6,0),new Pair(2,1),new Pair(2,1),
                //15END_LEFT        16END_RIGHT         17END_UP            18END_DOWN
                new Pair(3,0),new Pair(2,0),new Pair(4,1),new Pair(3,1),
                //19LINE_HORIZONTAL 20LINE_VERTICAL
                new Pair(5,1),new Pair(5,1)),
        FORESTSET(FOREST,"Wald","assets/images/tilesets/scenario/complete_tileset.png",32,32,
                //1MIDDLE           2SINGLE
                new Pair(2,3),new Pair(6,1),
                //3BORDER_LEFT      4BORDER_RIGHT       5BORDER_UP          6BORDER_DOWN
                new Pair(7,2),new Pair(6,2),new Pair(2,2),new Pair(1,2),
                //7CORNER_TOP_LEFT  8CTL_EMPTY          9CORNER_BOT_LEFT    10CBL_EMPTY         11CORNER_TOP_RIGHT  12CTR_EMPTY         13CORNER_BOT_RIGHT  14CORNER_BOT_RIGHT_EMPTY
                new Pair(7,1),new Pair(3,3),new Pair(9,1),new Pair(5,3),new Pair(8,1),new Pair(4,3),new Pair(0,2),new Pair(6,3),
                //15END_LEFT        16END_RIGHT         17END_UP            18END_DOWN
                new Pair(9,2),new Pair(8,2),new Pair(5,2),new Pair(3,2),
                //19LINE_HORIZONTAL 20LINE_VERTICAL
                new Pair(0,3),new Pair(4,2));

        private final Pair[] coordinates;
        public final String name;
        public final ScenarioTerrainData type;
        private final Tileset graphics;

        TileSet(ScenarioTerrainData type, String name, String tileSetPath, int tilesWidth, int tilesHeight, Pair... coordinates){
            graphics = new Tileset(tileSetPath,tilesWidth,tilesHeight);
            this.type = type;
            this.name = name;
            if (coordinates.length < 20){
                System.out.println("WARNUNG: TileSet "+name+" ist mit nur "+coordinates.length+" Tiles von 20 besetzt.");
            }
            this.coordinates = coordinates;
        }

        public BufferedImage getImg(MapController.RectanglePart partCode){
            if (partCode == MapController.RectanglePart.MIDDLE) return graphics.getTile(coordinates[0].x,coordinates[0].y);
            if (partCode == MapController.RectanglePart.SINGLE) return graphics.getTile(coordinates[1].x,coordinates[1].y);
            if (partCode == MapController.RectanglePart.BORDER_LEFT) return graphics.getTile(coordinates[2].x,coordinates[2].y);
            if (partCode == MapController.RectanglePart.BORDER_RIGHT) return graphics.getTile(coordinates[3].x,coordinates[3].y);
            if (partCode == MapController.RectanglePart.BORDER_UP) return graphics.getTile(coordinates[4].x,coordinates[4].y);
            if (partCode == MapController.RectanglePart.BORDER_DOWN) return graphics.getTile(coordinates[5].x,coordinates[5].y);
            if (partCode == MapController.RectanglePart.CORNER_TOP_LEFT) return graphics.getTile(coordinates[6].x,coordinates[6].y);
            if (partCode == MapController.RectanglePart.CORNER_TOP_LEFT_EMPTY) return graphics.getTile(coordinates[7].x,coordinates[7].y);
            if (partCode == MapController.RectanglePart.CORNER_BOT_LEFT) return graphics.getTile(coordinates[8].x,coordinates[8].y);
            if (partCode == MapController.RectanglePart.CORNER_BOT_LEFT_EMPTY) return graphics.getTile(coordinates[9].x,coordinates[9].y);
            if (partCode == MapController.RectanglePart.CORNER_TOP_RIGHT) return graphics.getTile(coordinates[10].x,coordinates[10].y);
            if (partCode == MapController.RectanglePart.CORNER_TOP_RIGHT_EMPTY) return graphics.getTile(coordinates[11].x,coordinates[11].y);
            if (partCode == MapController.RectanglePart.CORNER_BOT_RIGHT) return graphics.getTile(coordinates[12].x,coordinates[12].y);
            if (partCode == MapController.RectanglePart.CORNER_BOT_RIGHT_EMPTY) return graphics.getTile(coordinates[13].x,coordinates[13].y);
            if (partCode == MapController.RectanglePart.END_LEFT) return graphics.getTile(coordinates[14].x,coordinates[14].y);
            if (partCode == MapController.RectanglePart.END_RIGHT) return graphics.getTile(coordinates[15].x,coordinates[15].y);
            if (partCode == MapController.RectanglePart.END_UP) return graphics.getTile(coordinates[16].x,coordinates[16].y);
            if (partCode == MapController.RectanglePart.END_DOWN) return graphics.getTile(coordinates[17].x,coordinates[17].y);
            if (partCode == MapController.RectanglePart.LINE_HORIZONTAL) return graphics.getTile(coordinates[18].x,coordinates[18].y);
            if (partCode == MapController.RectanglePart.LINE_VERTICAL) return graphics.getTile(coordinates[19].x,coordinates[19].y);
            return null;
        }

    }
}

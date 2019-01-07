package akkgframework.model.scenario;


import akkgframework.model.fundamental.Tileset;

import java.awt.image.BufferedImage;

public enum ScenarioObjectData {
    CASTLE_LEFT_SIDE(4,6,"Burg","assets/images/tilesets/scenario/complete_tileset.png"),
    CASTLE_RIGHT_SIDE(5,6,"Burg","assets/images/tilesets/scenario/complete_tileset.png"),
    CHEST_CLOSED(0,4,"Geschlossene Truhe","assets/images/tilesets/scenario/complete_tileset.png"),
    CHEST_OPEN(1,4,"Offene Truhe","assets/images/tilesets/scenario/complete_tileset.png"),
    SACK(2,4,"Sack","assets/images/tilesets/scenario/complete_tileset.png"),
    COINSTACK(3,4,"Stapel Münzen","assets/images/tilesets/scenario/complete_tileset.png"),
    PRIEST(4,4,"Priester","assets/images/tilesets/scenario/complete_tileset.png"),
    PEASANT_SHOVEL(6,4,"Bauer mit Schaufel","assets/images/tilesets/scenario/complete_tileset.png"),
    WARRIOR(9,4,"Krieger","assets/images/tilesets/scenario/complete_tileset.png"),
    KNIGHT(0,5,"Ritter","assets/images/tilesets/scenario/complete_tileset.png"),
    KNIGHT_ELITE(1,5,"Elite-Ritter","assets/images/tilesets/scenario/complete_tileset.png"),
    UNDEAD_WARRIOR(5,5,"Untoter Krieger","assets/images/tilesets/scenario/complete_tileset.png"),
    UNDEAD_KNIGHT(6,5,"Untoter Ritter","assets/images/tilesets/scenario/complete_tileset.png"),
    UNDEAD_KNIGHT_ELITE(7,5,"Untoter Elite-Ritter","assets/images/tilesets/scenario/complete_tileset.png"),
    OGRE(8,5,"Oger","assets/images/tilesets/scenario/complete_tileset.png"),
    SORCERER(9,5,"Zauberer","assets/images/tilesets/scenario/complete_tileset.png"),
    DRAGON_SMALL(0,6,"Drachenjunges","assets/images/tilesets/scenario/complete_tileset.png"),
    DRAGON_MEDIUM(1,6,"Drache","assets/images/tilesets/scenario/complete_tileset.png"),
    DRAGON_BIG(2,6,"Großdrache","assets/images/tilesets/scenario/complete_tileset.png"),
    BOAR(3,6,"Wildschwein","assets/images/tilesets/scenario/complete_tileset.png"),
    BLUE_KNIGHT(6,6,"Blauritter","assets/images/tilesets/scenario/complete_tileset.png"),
    PEASANT(7,6,"Bauer","assets/images/tilesets/scenario/complete_tileset.png"),
    SKELETON(8,6,"Skellett","assets/images/tilesets/scenario/complete_tileset.png"),
    BLUE_ROBE_KNIGHT(9,6,"Blaurobenritter","assets/images/tilesets/scenario/complete_tileset.png"),
    SIGN_DIRECTIONS(0,5,"Richtungsschild","assets/images/tilesets/scenario/misc.png"),
    WOOD (6,10,"Holz","assets/images/tilesets/scenario/misc.png"),
    IRON (3,0,"Eisen","assets/images/tilesets/scenario/mining.png"),
    SILVER(9,0,"Silber","assets/images/tilesets/scenario/mining.png"),
    GOLD(1,0,"Gold","assets/images/tilesets/scenario/mining.png");

    public final BufferedImage img;
    public final String name;

    ScenarioObjectData(int zeile, int spalte, String name, String tilesetfile){
        Tileset graphics = new Tileset(tilesetfile,32,32);
        img = graphics.getTile(zeile,spalte);
        this.name = name;
    }

    public java.awt.image.BufferedImage getImg(){
        return img;
    }

}

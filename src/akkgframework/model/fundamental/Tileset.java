package akkgframework.model.fundamental;

import control.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Die Klasse Tileset ermöglicht die Verwaltung eines Bild mit vielen Unterbildern, sofern das Bild richtig
 * aufgeteilt ist. Die Unterbilder bezeichnet man als Stücke (Tiles) des ganzen Bildes (Tileset).
 */
public class Tileset {

    private int gridWidth, gridHeight;
    private int countWidth, countHeight;
    private boolean initialized;

    private BufferedImage completeSet;
    private BufferedImage[][] tiles;
    private String filename;

    /**
     * Erzeugt ein neues Tileset aus einem Bild.
     * @param pathToImage Das Bild für das Tileset.
     * @param gridWidth Die Breite jeder Zelle im Tileset (Breiten müssen für alle Zellen gleich sein)
     * @param gridHeight Die Höhe jeder Zelle im Tileset (Höhen müssen für alle Zellen gleich sein)
     */
    public Tileset(String pathToImage, int gridWidth, int gridHeight){
        initialized = false;
        this.filename = pathToImage;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
        this.completeSet = null;
        try {
            completeSet = ImageIO.read(new File(pathToImage));
            initialized = true;
        } catch (IOException e) {
            if ( Config.INFO_MESSAGES) System.out.println("FEHLER: Laden eines Tilesets fehlgeschlagen: "+pathToImage+"\n Hast du den Pfad und Dateinamen richtig geschrieben?");
            initialized = false;
        }
        if (initialized){
            createTileSet();
        }
    }

    /**
     * Spaltet das komplette Bild gemäß einem Raster in einzelne
     * Stücke (tiles) auf.
     */
    private void createTileSet(){
        countWidth = completeSet.getWidth()/gridWidth;
        countHeight = completeSet.getHeight()/gridHeight;
        tiles = new BufferedImage[countWidth][countHeight];
        for ( int i = 0; i < countWidth; i++){
            for ( int j = 0; j < countHeight; j++){
                tiles[i][j] = completeSet.getSubimage(i*gridWidth,j*gridHeight,gridWidth,gridHeight);
            }
        }
    }

    /**
     * Liefert ein Stück des Tilesets zurück.
     * @param i Die Zeile des Stücks im Tileset, beginnend bei 0.
     * @param j Die Spalte des Stücks im Tileset, beginnend bei 0.
     * @return
     */
    public BufferedImage getTile(int i, int j){
        if (initialized){
            try {
                return tiles[i][j];
            } catch (Exception e){
                System.out.println("FEHLER: Es wurde versucht ein Tile aus dem Tileset zu laden, dass nicht enthalten ist! (Index zu groß oder zu klein!)");
            }
        } else {
            System.out.println("FEHLER: Das Stück (tile) des Sets "+this.filename+" kann nicht geladen werden, da das Gesamtbild nicht geladen werden konnte (siehe oben!)");
        }
        return null;
    }

    /**
     *
     * @return Die Anzahl der Zellen des Tilesets in der Breite.
     */
    public int getCountWidth() {
        return countWidth-1;
    }

    /**
     * Die Anzahl der Zellen des Tilesets in der Höhe.
     * @return
     */
    public int getCountHeight() {
        return countHeight-1;
    }


}

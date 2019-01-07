package akkgframework.control.scenario;

import control.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapController {

    private final String[][] borderedMap;
    private final String[][] map;
    private int mapWidth;
    private int mapHeight;
    private String mapPath;

    /**
     * Erzeugt ein MapController-Objet für eine spezifische Karte. Die Karte ist in Strings codiert, wobei jeder String
     * einen bestimmten Terraintyp darstellt.
     * Die Karte wird aus einer Datei generiert, deren Dateipfad übergeben werden muss.
     * @param mapPath Pfad zur Kartendatei.
     */
    public MapController(String mapPath){
        this.mapPath = mapPath;
        map = loadMap(mapPath);
        this.borderedMap = setMap(map);
    }

    private String[][] loadMap(String filePath){
        Path path = Paths.get(filePath);
        int columns = 0; int lines = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                String[] zeileninhalt = line.split("-");
                for (int i = 0; i < zeileninhalt.length && firstLine; i++){
                    columns++;
                }
                //Nächste Zeile
                firstLine = false;
                lines++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        path = Paths.get(filePath);
        String[][] tmpMap = new String[columns][lines];
        try (BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            int zeile = 0;
            while ((line = reader.readLine()) != null) {
                //Verarbeite Zeile
                String[] zeileninhalt = line.split("-");
                for (int i = 0; i < zeileninhalt.length; i++){
                    tmpMap[i][zeile] = zeileninhalt[i];
                }
                //Nächste Zeile
                zeile++;
            }
        } catch (Exception e){
            System.out.println("FEHLER: Die Kartendatei konnte nicht geladen werden!");
        }
        if(Config.DEBUG) {
            System.out.println("DEBUG-INFO: Loaded normal map:");
            for (int i = 0; i < tmpMap[0].length; i++) {
                for (int j = 0; j < tmpMap.length; j++) {
                    System.out.print(tmpMap[j][i]);
                }
                System.out.println();
            }
            System.out.println();
        }
        mapWidth = tmpMap.length;
        mapHeight = tmpMap[0].length;
        return tmpMap;
    }

    /**
     * Muss aufgerufen werden, bevor Tilesetprüfungen mit getRectangularTileType gemacht werden können.
     * @param newMap Ein zweidimensionales String-Array, dass die Karte darstellt. Kachelspalten müssen mit Bindestrichen getrennt werden, Zeilen mit Absätzen. Der äußere Rand der Karte muss
     *            mit "x" versehen werden, um den Rand der Karte anzuzeigen.
     */
    private String[][] setMap(String[][] newMap){
        String[][] tmp = newMap;
        newMap = new String[tmp.length+2][tmp[0].length+2];
        // Neue Ränder
        for(int i = 0; i<newMap.length;i++){
            newMap[i][0] = "x";
            newMap[i][newMap[0].length-1] = "x";
        }
        for(int i = 0; i<newMap[0].length;i++){
            newMap[0][i] = "x";
            newMap[newMap.length-1][i] = "x";
        }
        for(int i = 0;i < tmp.length;i++){
            for(int j = 0; j < tmp[0].length; j++){
                newMap[i+1][j+1] = tmp[i][j];
            }
        }
        if(Config.DEBUG) {
            System.out.println("DEBUG-INFO: Generated extendend map:");
            for (int i = 0; i < newMap[0].length; i++) {
                for (int j = 0; j < newMap.length; j++) {
                    System.out.print(newMap[j][i]);
                }
                System.out.println();
            }
        }
        return newMap;
    }

    /**
     * Dieses enum spezifiziert verschiedene Fälle für ein die richtige Wahl einer Kachel basierend
     * auf den umgebenden Kacheln, wenn von einem zusammenhängenden Muster ausgegangen wird.
     */
    public enum RectanglePart{
        //Wenn eine Seite der Kachel voll ausgefüllt werden soll:
        BORDER_LEFT, BORDER_RIGHT, BORDER_UP, BORDER_DOWN,
        //Wenn die Kachel eine Ecke bildet, die innen ausgefüllt ist:
        CORNER_TOP_LEFT, CORNER_TOP_RIGHT, CORNER_BOT_LEFT, CORNER_BOT_RIGHT,
        //Wenn die Kachel eine Ecke bildet, die innen nicht ausgefüllt ist:
        CORNER_TOP_LEFT_EMPTY, CORNER_TOP_RIGHT_EMPTY, CORNER_BOT_LEFT_EMPTY, CORNER_BOT_RIGHT_EMPTY,
        //Wenn die Kachel komplett gefüllt ist (ohne Struktur) oder einzeln isoliert steht
        MIDDLE,SINGLE,
        //Wenn die Kachel das Ende einer 1-Kachel-Schlange ist
        END_RIGHT, END_LEFT, END_UP, END_DOWN,
        //Wenn die Kachel eine links und rechts oder oben und unten isolierte Linie bildet
        LINE_HORIZONTAL, LINE_VERTICAL;
    }

    /**
     * Diese Methode untersucht eine bestimmte Stelle einer Karte, die als zweidimensionales String-Array vorliegt. Die Karte muss als String-Array vorher mit "setMap" geladen werden!
     * @param i Die x-Koordinate der zu untersuchenden Kachel.
     * @param j Die y-Koordinate der zu untersuchenden Kachel.
     * @param s Der String, der dem Typ der Kachel auf der Karte entspricht.
     * @return Gibt den Typ der Kachel zurück, der für die grafische Darstellung benötigt wird.
     */
    public RectanglePart getRectangularTileType(int i, int j, String s) {
        //Shift Coordinates to fit in bordered borderedMap
        i++;
        j++;
        if (!borderedMap[i][j].equals(s)) {
            System.out.println("FEHLER: Der passende Tile-Typ konnte nicht ermittelt werden, da die Datenstruktur nicht den korrekten Code aufweist.");
            return null;
        }
        int mapW = borderedMap.length-1;
        int mapH = borderedMap[0].length-1;
        if (i > 0 && i < mapW && j > 0 && j<mapH) {
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.MIDDLE;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.BORDER_DOWN;
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.BORDER_UP;
            if (borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.BORDER_LEFT;
            if (borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s)) return RectanglePart.BORDER_RIGHT;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (!borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.LINE_HORIZONTAL;
            if (borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s)) return RectanglePart.LINE_VERTICAL;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s) && borderedMap[i-1][j+1].equals(s))
                return RectanglePart.CORNER_TOP_RIGHT;
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s) && !borderedMap[i-1][j+1].equals(s))
                return RectanglePart.CORNER_TOP_RIGHT_EMPTY;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s) && borderedMap[i+1][j+1].equals(s))
                return RectanglePart.CORNER_TOP_LEFT;
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s) && !borderedMap[i+1][j+1].equals(s))
                return RectanglePart.CORNER_TOP_LEFT_EMPTY;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s) && borderedMap[i-1][j-1].equals(s))
                return RectanglePart.CORNER_BOT_RIGHT;
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s) && !borderedMap[i-1][j-1].equals(s))
                return RectanglePart.CORNER_BOT_RIGHT_EMPTY;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s) && borderedMap[i+1][j-1].equals(s))
                return RectanglePart.CORNER_BOT_LEFT;
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s) && !borderedMap[i+1][j-1].equals(s))
                return RectanglePart.CORNER_BOT_LEFT_EMPTY;
            //  Oben,                            unten,                           rechts,                          links                           + Inneres Feld
            if (borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s)) return RectanglePart.END_UP;
            if (!borderedMap[i][j-1].equals(s) && borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s)) return RectanglePart.END_DOWN;
            if (!borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && borderedMap[i+1][j].equals(s) && !borderedMap[i-1][j].equals(s)) return RectanglePart.END_RIGHT;
            if (!borderedMap[i][j-1].equals(s) && !borderedMap[i][j+1].equals(s) && !borderedMap[i+1][j].equals(s) && borderedMap[i-1][j].equals(s)) return RectanglePart.END_LEFT;
            return RectanglePart.SINGLE;
        }
        return null;
    }

    public String[][] getMap(){
        return map;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public String getMapPath() {
        return mapPath;
    }
}

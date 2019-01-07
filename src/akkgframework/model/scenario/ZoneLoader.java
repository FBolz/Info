package akkgframework.model.scenario;

import akkgframework.control.scenario.MapController;

public class ZoneLoader{

    //Attribute
	private final double flowerChance = 0.1;
	private final double stumpsChance = 0.1;
	private final double wavesChance = 0.1;

	//Referenzen
	private MapController mapController;

	public ScenarioTerrain[][] getZoneTerrain(String filePath){
		mapController = new MapController(filePath);
		return createTerrain( mapController.getMap() );
    }

	private ScenarioTerrain[][] createTerrain(String[][] map){
		ScenarioTerrain[][] generatedScenarioTerrain = new ScenarioTerrain[map.length][map[0].length];
		for(int i = 0; i< generatedScenarioTerrain.length; i++){
			for(int j = 0; j< generatedScenarioTerrain[0].length; j++){
				//GRAS
				if (map[i][j].equals("g")){
					if(Math.random()<stumpsChance){
						generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.GRAS, ScenarioTerrainData.Tile.TREE_STUMPS.img,false);
					} else if(Math.random()<flowerChance){
						if(Math.random()<0.5){
                            generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.GRAS, ScenarioTerrainData.Tile.FLOWERS_VARIATION1.img,false);
						} else {
                            generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.GRAS, ScenarioTerrainData.Tile.FLOWERS_VARIATION2.img,false);
						}
					} else {
                        generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.GRAS, ScenarioTerrainData.Tile.GRAS_BLANK.img,false);
					}
				}
				// WATERSET
                if (map[i][j].equals("w")){
					MapController.RectanglePart rp = mapController.getRectangularTileType(i, j, "w");
                    generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.WATER, ScenarioTerrainData.TileSet.WATERSET.getImg( rp ),true);
					if ( rp == MapController.RectanglePart.MIDDLE && Math.random()<wavesChance ){
						generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.WATER, ScenarioTerrainData.Tile.WATER_WAVES.img,true);
					}
                }
				// FORESTSET
                if (map[i][j].equals("f")) {
                    //Borders
                    generatedScenarioTerrain[i][j] = new ScenarioTerrain(ScenarioTerrainData.FOREST, ScenarioTerrainData.TileSet.FORESTSET.getImg( mapController.getRectangularTileType(i, j, "f")),true);
                }
			}
		}
		return generatedScenarioTerrain;
	}

}
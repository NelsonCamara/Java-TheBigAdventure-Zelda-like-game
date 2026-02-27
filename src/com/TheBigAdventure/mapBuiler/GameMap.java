package com.TheBigAdventure.mapBuiler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.characterEntities.ActionType;
import com.TheBigAdventure.characterEntities.Character;
import com.TheBigAdventure.graphic.Graph;

/**
 * Represents the game map with all game data organized by specific positions.
 */
public final class GameMap {

  private final MapSize size;
  private final Map<Position, Case> grid;
  private final ArrayList<Case> casesToReDraw;
  private final Map<String,BufferedImage> picturesInMap;
  private final Position playerPosition = new Position (0,0);
  private final Map<String,Double> mapScale;
  private final Map<String,Integer> mapInventoryDimensions;
  private final Map<Integer,Position> inventoryIndexPositions;
  private final Map<String,Boolean>  drawedObjectsInfo;
  private int   inventoryCursorIndexPosition = 0;
  private final Position inventoryPosition = new Position (0,0);
  private boolean mapDrawed = false;
  private Instant lastMonsterEvent;
  private static final long MONSTERINTERVAL = 1;


  /**
   * Constructs a GameMap with the specified grid layout.
   *
   * @param grid The initial grid layout of the game map.
   */
  protected GameMap(Map<Position, Case> grid, MapSize size,Map<String, BufferedImage> mapImages) {
    this.grid = grid;
    this.size = size;
    this.picturesInMap=mapImages;
    this.casesToReDraw   = new ArrayList<>();
    this.mapScale = new HashMap<>();
    this.mapInventoryDimensions = new HashMap<>();
    this.inventoryIndexPositions = new HashMap<>();
    this.drawedObjectsInfo = new HashMap<>();
    this.lastMonsterEvent = Instant.now();
    
    drawedObjectsInfo.put("InventoryDrawed",false);
    drawedObjectsInfo.put("DrawInventory",false);
    drawedObjectsInfo.put("EraseInventory",false);

  }

  /**
   * Retrieves the game map grid.
   *
   * @return The grid of the game map.
   */
  public final Map<Position, Case> getGrid() {
    return grid;
  }

  /**
   * Adds or updates a case at a specific position in the game map.
   *
   * @param position The position to add or update the case.
   * @param caseObj  The Case object to place at this position.
   */
  protected final void setCaseAt(Position position, Case caseObj) {
    grid.put(position, caseObj);
  }

  /**
   * Retrieves the case at a specific position in the game map.
   *
   * @param position The position of the case to retrieve.
   * @return The case at the specified position, or null if no case is present.
   */
  public final Case getCaseAt(Position position) {
    return grid.get(position);
  }

  /**
   * Checks if the given position is valid and walkable on the map.
   *
   * @param position The position to check.
   * @param grid     The grid of the game map.
   * @return true if the position is valid and walkable, false otherwise.
   */
  private final  boolean isValidPosition(Position position, Map<Position, Case> grid) {
    return grid.containsKey(position) && grid.get(position).isWalkable();
  }

  /**
   * Updates the specified case with the given entity.
   *
   * @param caseObj The case to be updated.
   * @param entity  The entity to update the case with.
   */
  private final Case updateCaseWithEntity(Case caseObj, Character entity) {
    return caseObj.withCharacterEntity(Optional.ofNullable(entity));
}

  
  /**
   * Updates the player's position on the game map.
   *
   * @param grid      The grid of the game map.
   * @param newPos    The new position of the player.
   * @param currentPos The current position of the player.
   * @param stringPos The position for additional information (optional).
   */
  private final void updatePlayerMovement(Map<Position, Case> grid,Position newPos,Position currentPos,Position stringPos) {
    setPlayerPosition(newPos);

  }
  
  /**
   * Updates the position of an entity on the game map.
   *
   * @param grid      The grid of the game map.
   * @param newPos    The new position of the entity.
   * @param currentPos The current position of the entity.
   * @param entity    The entity to update.
   */
  private final void updateEntityPosition(Map<Position, Case> grid,Position newPos,Position currentPos,Character entity) {
    entity.setPosition(newPos);
    Case updatedCurrentCase = updateCaseWithEntity(grid.get(currentPos), null);
    grid.put(currentPos, updatedCurrentCase);

    Case updatedNewCase = updateCaseWithEntity(grid.get(newPos), entity);
    grid.put(newPos, updatedNewCase);
  }
  
  /**
   * Moves a movable entity in the specified direction on the game map.
   *
   * @param entity    The entity to be moved.
   * @param direction The direction to move the entity.
   * @param grid      The grid of the game map.
   */

  public final void moveMovableEntity(Character entity, Direction direction,GameMap gameMap) {
    Position currentPosition = new Position(0,0);
    currentPosition.setNewPosition(entity.getPosition());
    Position newPosition = currentPosition.calculateNewPosition(direction);
    Position stringPos = new Position(playerPosition.getX(),playerPosition.getY()-1);
    
    if (isValidPosition(newPosition, grid)) {
      updateEntityPosition(grid, newPosition, currentPosition, entity);
      if(entity.isPlayer()) updatePlayerMovement(grid, newPosition, currentPosition, stringPos); 
      
      Position caseInFront = newPosition.calculateNewPosition(direction);
      entity.setCaseInFrontPosition(caseInFront);
     }
    else {
      Position caseInFront = currentPosition.calculateNewPosition(direction);
      entity.setCaseInFrontPosition(caseInFront);
    }
   }
  
  /**
   * Moves the inventory cursor in the specified direction on the game map.
   *
   * @param direction The direction in which to move the inventory cursor.
   * @param gameMap   The game map containing the inventory.
   */
  public final void moveInventoryCursor(Direction direction,GameMap gameMap) {
    int cursorIndex = gameMap.getInventoryCursorIndexPosition();
    switch (direction) {
    case UP:
      if(cursorIndexValidation(cursorIndex,direction)) {gameMap.setInventoryCursorIndexPosition(cursorIndex-3);}
        break;
    case DOWN:
      if(cursorIndexValidation(cursorIndex,direction)) {gameMap.setInventoryCursorIndexPosition(cursorIndex+3);}
        break;
    case LEFT:
      if(cursorIndexValidation(cursorIndex,direction)) {gameMap.setInventoryCursorIndexPosition(cursorIndex-1);}
        break;
    case RIGHT:
      if(cursorIndexValidation(cursorIndex,direction)) {gameMap.setInventoryCursorIndexPosition(cursorIndex+1);}
        break;
    case INIT:
        

        break;
    default:
        break;
    }
 }
  
  /**
   * Validates the inventory cursor index based on the specified direction.
   *
   * @param index     The current index of the inventory cursor.
   * @param direction The direction in which the cursor is moving.
   * @return true if the movement is valid, false otherwise.
   */
  private final boolean cursorIndexValidation(int index,Direction direction) {
    
    if((index == 0 || index == 1 || index == 2) && direction == Direction.UP) {
      return false;
    }
    if((index == 0 || index == 3 || index == 6) && direction == Direction.LEFT) {
      return false;
    }
    if((index == 2 || index == 5 || index == 8) && direction == Direction.RIGHT) {
      return false;
    }
    if((index == 6 || index == 7 || index == 8) && direction == Direction.DOWN) {
      return false;
    }
    return true;
  }


  /**
   * Updates the case at the specified position with the given object.
   *
   * @param <T>      The type of the object.
   * @param position The position of the case to be updated.
   * @param object   The object used to update the case.
   * @param updater  The updater defining how the object updates the case.
   * @throws IllegalArgumentException if the position does not exist in the grid.
   */
  protected final <T> void updateCase(Position position, T object, CaseUpdater<T> updater) {
    Objects.requireNonNull(position);
    Objects.requireNonNull(object);
    Objects.requireNonNull(updater);
    Case gridCase = grid.get(position);

    if (gridCase != null) {
        Case updatedCase = updater.update(gridCase, object);
        grid.put(position, updatedCase);
    } else {
        throw new IllegalArgumentException("This position does not exist in the grid\n");
    }
}

  
  /**
   * Retrieves a BufferedImage from the file system based on the specified image name.
   *
   * @param imgName The name of the image to load.
   * @return The loaded BufferedImage.
   * @throws IOException If an I/O error occurs during image loading.
   */
  private static final BufferedImage getImageFromFile(String imgName ) throws IOException {
    BufferedImage img;
    String pathToImg;
    pathToImg = "/img/" + imgName + ".png";
    
    img = Graph.loadImage(pathToImg);
    
    
    return img;
    
  }
  
  /**
   * Initializes a map of BufferedImages from a list of ObjectFromSkin instances.
   *
   * @param objects The list of ObjectFromSkin instances.
   * @return A map associating object skins with their corresponding BufferedImages.
   * @throws IOException If an I/O error occurs during image loading.
   */
  private static final Map<String, BufferedImage> initImagesMap(List<ObjectFromSkin> objects) throws IOException {
    Map<String, BufferedImage> imagesMap = new HashMap<>();

    for (ObjectFromSkin object : objects) {
        String skin = object.getObjectSkin();
        if (!imagesMap.containsKey(skin) && !skin.equals("VOID")) {  
            BufferedImage img = getImageFromFile(skin);
            imagesMap.put(skin, img);
        }
    }

    return imagesMap;
  }
  
  


  /**
   * Initializes and returns the game map grid from a specified file path.
   *
   * @param filepath The path to the file used to initialize the map.
   * @return The initialized game map grid.
   * @throws IOException If an I/O error occurs reading from the file.
   */
  public static final GameMap initMap(String filepath) throws IOException {
    Objects.requireNonNull(filepath);
    
    LoadObjectsFromMap objectsToLoad;
    MapSize mapSize;
    Map<Position, Case> grid = new HashMap<>();
    Map<String, BufferedImage> mapImages = new HashMap<>();
    List<ObjectFromSkin> objects;
    MapContainer mapContainer;
    mapContainer = ParseFile.execLexer(filepath);
    mapSize = new MapSize(mapContainer.mapDimensions()[1],mapContainer.mapDimensions()[0]);
    objects = mapContainer.buildAllMapObjects();
    objectsToLoad = new LoadObjectsFromMap(objects);

    mapImages = GameMap.initImagesMap(objects);
    GameMap gameMap = new GameMap(grid, mapSize,mapImages);
    
    objectsToLoad.loadObjectsInMap(gameMap);
    return gameMap;
  }
  
  /**
   * Checks the player's position relative to the map boundaries.
   *
   * @param grid The map grid.
   * @return -1 if the player is near the left boundary, 1 if near the right boundary, 0 otherwise.
   */
  public int checkPlayerPosition(Map<Position, Case> grid) {
    Objects.requireNonNull(grid);


    int maxX = 0;
    for (Position pos : grid.keySet()) {
        if (pos.getX() > maxX) {
            maxX = pos.getX();
        }
    }

   
    if (playerPosition.getX() >= maxX - 5) {
        return -1; 
    } else if (playerPosition.getX() <= 5) {
        return 1; 
    } else {
        return 0; 
    }
}
  
  /**
   * Calculates the offset based on the player's position relative to the map boundaries.
   *
   * @param gameMap The game map.
   * @return The offset value (positive for right, negative for left).
   */
  public int calculateOffset(GameMap gameMap) {

    int offsetRight = 2;
    int offsetLeft = 0;
    
    int positionCheck = checkPlayerPosition(gameMap.getGrid());

    if (positionCheck == 1) {
        return offsetRight;
    } else if (positionCheck == -1) {
        return -offsetLeft;
    } else {
        return offsetRight; 
    }
  }
  
  
  /**
   * Updates the positions of inventory indexes on the game map.
   */
  public void updateInventoryIndexesPosition() {
    Position indexPos;
    int xPos,yPos,countIndex=0;
    
    for(int i =0;i<3;i++) {
      yPos = getInventoryPosition().getY()+i*60;
      for(int j =0;j<3;j++) {
        xPos = getInventoryPosition().getX()+j*60;
        indexPos = new Position(xPos,yPos);
        getInventoryIndexPositions().put(countIndex, indexPos);
        countIndex++;
      }
    }
    
    
    
    
  }
  
  /**
   * Retrieves the player character from the game map.
   *
   * @param gameMap The game map.
   * @return The player character.
   * @throws NoSuchElementException if no character is found at the player's position.
   */
  public final Character characterFromMap(GameMap gameMap) {
    Map<Position, Case> grid;
    grid =gameMap.getGrid();
    Optional<Character> optionalChar = grid.get(gameMap.getPlayerPosition()).getCharacter();
    
    Character character = optionalChar.orElseThrow(() -> new NoSuchElementException("No character found"));
    return character;
  }
  
  /**
   * Retrieves the size of the game map.
   *
   * @return The size of the game map.
   */
  public MapSize getSize() {
    return size;
  }

  /**
   * Retrieves the pictures associated with objects in the map.
   *
   * @return The map of object names to their corresponding images.
   */
  public Map<String,BufferedImage> getPicturesInMap() {
    return picturesInMap;
  }
  
  /**
   * Retrieves the position of the player in the game map.
   *
   * @return The position of the player.
   */
  public Position getPlayerPosition() {
    return playerPosition;
  }
  
  /**
   * Sets the position of the player in the game map.
   *
   * @param newPlayerPosition The new position of the player.
   */
  protected void setPlayerPosition(Position newPlayerPosition) {
    playerPosition.setNewPosition(newPlayerPosition);
  }
  
  /**
   * Retrieves the list of cases that need to be redrawn in the game map.
   *
   * @return The list of cases to redraw.
   */
  public ArrayList<Case> getCasesToReDraw() {
    return casesToReDraw;
  }
  
  /**
   * Checks if the map has been initialized.
   *
   * @return True if the map has been initialized, false otherwise.
   */
  public boolean isMapInitialised() {
    return mapDrawed;
  }
  
  /**
   * Sets the initialization status of the map.
   *
   * @param mapInitialised The new initialization status of the map.
   */
  public void setMapInitialised(boolean mapInitialised) {
    this.mapDrawed = mapInitialised;
  }

  /**
   * Retrieves the scale information for different aspects of the map.
   *
   * @return The map of scale factors.
   */
  public Map<String,Double> getMapScale() {
    return mapScale;
  }

  /**
   * Retrieves the dimensions of the inventory in the map.
   *
   * @return The map of inventory dimensions.
   */
  public Map<String,Integer> getMapInventoryDimensions() {
    return mapInventoryDimensions;
  }
  
  /**
   * Retrieves the position of the inventory in the map.
   *
   * @return The position of the inventory.
   */
  public Position getInventoryPosition() {
    return inventoryPosition;
  }
  
  /**
   * Retrieves the positions of the inventory indexes in the map.
   *
   * @return The map of inventory index positions.
   */
  public Map<Integer,Position> getInventoryIndexPositions() {
    return inventoryIndexPositions;
  }

  /**
   * Retrieves information about drawn objects in the map.
   *
   * @return The map of drawn objects information.
   */
  public Map<String,Boolean> getDrawedObjectsInfo() {
    return drawedObjectsInfo;
  }
  
  /**
   * Retrieves the index position of the inventory cursor in the map.
   *
   * @return The index position of the inventory cursor.
   */
  public int getInventoryCursorIndexPosition() {
    return inventoryCursorIndexPosition;
  }

  /**
   * Sets the index position of the inventory cursor in the map.
   *
   * @param inventoryCursorIndexPosition The new index position of the inventory cursor.
   */
  protected void setInventoryCursorIndexPosition(int inventoryCursorIndexPosition) {
    this.inventoryCursorIndexPosition = inventoryCursorIndexPosition;
  }
  
  /**
   * Retrieves and returns a map of enemy positions and their corresponding cases on the game grid.
   *
   * @return A map containing enemy positions as keys and their associated cases as values.
   */
  protected Map<Position, Case> getEnemies() {
      Map<Position, Case> enemies = new HashMap<>();
      for (Map.Entry<Position, Case> entry : grid.entrySet()) {
          Position position = entry.getKey();
          Case aCase = entry.getValue();
          if (aCase.getCaseState().equals(ActionType.CONTAINS_ENEMY)) {
              enemies.put(position, aCase);
          }
      }
      return enemies;
  }
  
  
  
  /**
   * Updates the movements of enemy characters on the game grid.
   * This method selects a random direction for each enemy and attempts to move them accordingly.
   * If no character is found in a case, it throws a NoSuchElementException.
   */
  protected void updateEnemiesMouvements() {
      Map<Position, Case> enemies = getEnemies();
      for (Map.Entry<Position, Case> entry : enemies.entrySet()) {
          Case aCase = entry.getValue();
          Optional<Character> tmpOptionalChar = aCase.getCharacter();
          Character tmpChar = tmpOptionalChar.orElseThrow(() -> new NoSuchElementException("No character found"));
          Direction aleatDir = Direction.getRandomDirection();
          moveMovableEntity(tmpChar, aleatDir, this);
      }
  }
  
  
  
  /**
   * Initiates enemy movements if a certain time interval has passed since the last event.
   * Enemy movements are updated using the updateEnemiesMouvements method.
   * The lastMonsterEvent timestamp is updated after initiating enemy movements.
   */
  public void enemiesMouvements() {
      if (Instant.now().getEpochSecond() - lastMonsterEvent.getEpochSecond() >= MONSTERINTERVAL) {
          updateEnemiesMouvements();
          lastMonsterEvent = Instant.now();
      }
  }




}

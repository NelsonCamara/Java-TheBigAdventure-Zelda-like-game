/**
 * The Graph class provides utility methods for drawing entities, maps, and handling graphics-related operations in the game.
 */
package com.TheBigAdventure.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.TheBigAdventure.characterEntities.Character;
import javax.imageio.ImageIO;

import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;

/**
 * The Graph class provides utility methods for drawing entities, maps, and handling graphics-related operations in the game.
 */
public final class Graph {
	
  /**
   * Loads an image from the specified path.
   *
   * @param imgPath The path of the image file.
   * @return The loaded BufferedImage.
   * @throws IOException If an error occurs while reading the image.
   */
  public static final BufferedImage loadImage(String imgPath) throws IOException {
    Objects.requireNonNull(imgPath);
    BufferedImage image = null;
    try (InputStream input = Graph.class.getResourceAsStream(imgPath)) {
      image = ImageIO.read(input);
    } catch (IOException e) {
      throw e;
    }
    return image;
    }
  
  


  /**
   * Draws a filled rectangle with the specified position, size, color, and graphics context.
   *
   * @param entityPosition The position of the rectangle.
   * @param sizeX          The width of the rectangle.
   * @param sizeY          The height of the rectangle.
   * @param graphics       The Graphics2D context to draw on.
   * @param color          The color of the rectangle.
   */ 
 public static final void  drawRect(Position entityPosition, int sizeX, int sizeY, Graphics2D graphics, Color color) {
    Objects.requireNonNull(entityPosition);
    Objects.requireNonNull(color);
    Objects.requireNonNull(graphics);

    graphics.setColor(color);
    graphics.fillRect(entityPosition.getX(), entityPosition.getY(), sizeX, sizeY);
    }
 


 /**
  * Draws an entity (image) with the specified position, size, image, and graphics context.
  *
  * @param entityPosition The position of the entity.
  * @param sizeX          The width of the entity.
  * @param sizeY          The height of the entity.
  * @param graphics       The Graphics2D context to draw on.
  * @param img            The image of the entity.
  */
  public static final void drawEntity(Position entityPosition, int sizeX, int sizeY,Graphics2D graphics,BufferedImage img ){
    Objects.requireNonNull(entityPosition);
    Objects.requireNonNull(img);
    Objects.requireNonNull(graphics);
    
    graphics.drawImage(img, entityPosition.getX(), entityPosition.getY(), sizeX, sizeY, null);
    }
  
  /**
   * Gets the screen width of the game map based on the map scale.
   *
   * @param gameMap The GameMap for which to calculate the screen width.
   * @return The calculated screen width.
   * @throws IllegalArgumentException If map scales are not initialized.
   */
  public static final int getScreenWidth(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    if(gameMap.getMapScale().containsKey("X")) {
      return (int) (gameMap.getMapScale().get("X")*gameMap.getSize().width());
    }
    else {
      throw new IllegalArgumentException("MapScales are not initialized");
      }
    }
  
  
  /**
   * Gets the screen height of the game map based on the map scale.
   *
   * @param gameMap The GameMap for which to calculate the screen height.
   * @return The calculated screen height.
   * @throws IllegalArgumentException If map scales are not initialized.
   */
  private static final int getScreenHeight(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    if(gameMap.getMapScale().containsKey("Y")) {
      return (int) (gameMap.getMapScale().get("Y")*gameMap.getSize().height());
      }
    else {
      throw new IllegalArgumentException("MapScales are not initialized");
      }
    }
  
  /**
   * Draws the entire game map on the provided graphics context.
   *
   * @param gameMap  The GameMap to be drawn.
   * @param graphics The Graphics2D context to draw on.
   */
  public static final void drawMap(GameMap gameMap, Graphics2D graphics) {
    Objects.requireNonNull(gameMap);
    Objects.requireNonNull(graphics);
    
    for (Case caseToDraw : gameMap.getGrid().values()) {
      if (caseToDraw != null) {
        caseToDraw.draw(graphics,1,1,gameMap);
        }
      }
    
    gameMap.setMapInitialised(true);
    }
  
  /**
   * Generates a set of positions for the inventory zone around the specified center position on the game map.
   *
   * @param centerPosition The center position of the inventory zone.
   * @param gameMap        The GameMap.
   * @param offsetToPlayer The offset to the player position.
   * @return The set of positions representing the inventory zone.
   * @throws NullPointerException if centerPosition or gameMap is null.
   */
  public static final Set<Position> inventoryZone(Position centerPosition,GameMap gameMap,int offsetToPlayer) {
    Objects.requireNonNull(centerPosition);
    Objects.requireNonNull(gameMap);
    Set<Position> squarePositions = new HashSet<>();

    int startX = centerPosition.getX() + offsetToPlayer;
    int startY = centerPosition.getY();

    for (int x = startX; x < startX + 6; x++) {
        for (int y = startY-1; y < startY + 6; y++) {
            Position currentPosition = new Position(x, y);
            if (gameMap.getGrid().containsKey(currentPosition)) {
                squarePositions.add(currentPosition);
                gameMap.getCasesToReDraw().add(gameMap.getGrid().get(currentPosition));
                }
            }
        }
    return squarePositions;
    }
  
  /**
   * Determines the inventory zone based on the player's position and updates the game map accordingly.
   *
   * @param gameMap The GameMap.
   * @throws NullPointerException if gameMap is null.
   */
  public static final void detInventoryZoneCases(GameMap gameMap) {
    int inventoryLeftOrRight = gameMap.checkPlayerPosition(gameMap.getGrid());
    
    if(inventoryLeftOrRight == -1) {
      inventoryZone(gameMap.getPlayerPosition(),gameMap,-3);
    }
    else {
      inventoryZone(gameMap.getPlayerPosition(),gameMap,1);
    }
  }

  
  /**
   * Draws a grid on the specified graphics context with the given start position, number of cases in the X and Y directions, and cell size.
   *
   * @param graphics    The Graphics2D context to draw on.
   * @param startPosition The starting position of the grid.
   * @param nbCasesX     The number of cases in the X direction.
   * @param nbCasesY     The number of cases in the Y direction.
   * @param cellSize     The size of each cell in the grid.
   * @throws NullPointerException if graphics or startPosition is null.
   */
  private static final void drawGrid(Graphics2D graphics, Position startPosition, int nbCasesX, int nbCasesY, int cellSize) {
    int endX = startPosition.getX() + nbCasesX * cellSize;
    int endY = startPosition.getY() + nbCasesY * cellSize;
    graphics.setColor(Color.black);
    
    for (int i = 0; i <= nbCasesX; i++) {
        int xLine = startPosition.getX() + i * cellSize;

        graphics.drawLine(xLine, startPosition.getY(), xLine, endY);
        }

    for (int i = 0; i <= nbCasesY; i++) {
        int yLine = startPosition.getY() + i * cellSize;
        graphics.drawLine(startPosition.getX(), yLine, endX, yLine);
        }
    }


  /**
   * Draws the inventory on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  public static final void drawInventory(GameMap gameMap, Graphics2D graphics) {
    Objects.requireNonNull(gameMap);
    Objects.requireNonNull(graphics);

    AffineTransform originalTransform = graphics.getTransform();
    prepareInventory(gameMap, graphics);
    
    drawInventoryBackground(gameMap, graphics);
    
    updateAndDrawInventoryIndex(gameMap, graphics);
    drawInventoryItems(gameMap, graphics);
    drawInventoryGrid(gameMap, graphics);
    graphics.setTransform(originalTransform);
  }
  
  /**
   * Prepares the inventory on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  private static final void prepareInventory(GameMap gameMap, Graphics2D graphics) {
    int cellSize = 60, nbCasesX = 3, nbCasesY = 3, inventorySizeX = nbCasesX * cellSize , inventorySizeY = nbCasesY * cellSize;
    
    gameMap.getMapInventoryDimensions().put("X", inventorySizeX);
    gameMap.getMapInventoryDimensions().put("Y", inventorySizeY);


    graphics.setTransform(new AffineTransform());

    Position adjustedPlayerPosition = new Position(
        getScreenWidth(gameMap)/2 ,
        getScreenHeight(gameMap)/2)
    ;
    Position inventoryPosition = new Position(adjustedPlayerPosition.getX(), adjustedPlayerPosition.getY());
    gameMap.getInventoryPosition().setNewPosition(inventoryPosition);
    }
  
  /**
   * Draws the background of the inventory on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  private static final void drawInventoryBackground(GameMap gameMap, Graphics2D graphics) {
    Position inventoryPosition = gameMap.getInventoryPosition();
    int inventorySizeX = gameMap.getMapInventoryDimensions().get("X");
    int inventorySizeY = gameMap.getMapInventoryDimensions().get("Y");

    drawRect(inventoryPosition, inventorySizeX, inventorySizeY, graphics, Color.DARK_GRAY);
    }
  
  /**
   * Draws the grid of the inventory on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  private static final void drawInventoryGrid(GameMap gameMap, Graphics2D graphics) {
    Position inventoryPosition = gameMap.getInventoryPosition();
    int cellSize = 60, nbCasesX = 3, nbCasesY = 3;

    drawGrid(graphics, inventoryPosition, nbCasesX, nbCasesY, cellSize);
    }
  
  /**
   * Updates and draws the inventory index on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  private static final void updateAndDrawInventoryIndex(GameMap gameMap, Graphics2D graphics) {
    gameMap.updateInventoryIndexesPosition();
    Position indexPosition = gameMap.getInventoryIndexPositions().get(gameMap.getInventoryCursorIndexPosition());
    
    drawRect(indexPosition, 60,60, graphics, Color.RED);
    }
  
  /**
   * Draws the items in the inventory on the specified graphics context.
   *
   * @param gameMap  The GameMap.
   * @param graphics The Graphics2D context to draw on.
   * @throws NullPointerException if gameMap or graphics is null.
   */
  private static final void drawInventoryItems(GameMap gameMap, Graphics2D graphics) {
    Character character = gameMap.getGrid().get(gameMap.getPlayerPosition()).getCharacter()
                               .orElseThrow(NoSuchElementException::new);

    character.characterGetInventory().drawItems(graphics, gameMap);
    }

  /**
   * Retrieves hidden positions to redraw based on the specified drawing position, size, and the GameMap.
   *
   * @param drawingPosition The position to start drawing.
   * @param drawingSizeX    The width of the drawing.
   * @param drawingSizeY    The height of the drawing.
   * @param gameMap         The GameMap.
   * @throws NullPointerException if drawingPosition or gameMap is null.
   */
  protected static final void getHiddenPositionsToReDraw(Position drawingPosition, int drawingSizeX, int drawingSizeY,GameMap gameMap) {
    Objects.requireNonNull(drawingPosition);
    Objects.requireNonNull(gameMap);
    Set<Position> hiddenPositions = new HashSet<>();

    int drawingEndX = drawingPosition.getX() + drawingSizeX;
    int drawingEndY = drawingPosition.getY() + drawingSizeY;


    for (Position pos : gameMap.getGrid().keySet()) {
        if (pos.getX() >= drawingPosition.getX() && pos.getX() < drawingEndX &&
            pos.getY() >= drawingPosition.getY() && pos.getY() < drawingEndY) {
            hiddenPositions.add(pos);
            }
        }
    gameMap.getCasesToReDraw().addAll(
        hiddenPositions.stream()
                       .map(gameMap.getGrid()::get)
                       .filter(Objects::nonNull)
                       .collect(Collectors.toList())
        );
    return;
    }
  
  /**
   * Initializes the game map based on the provided map file path.
   *
   * @param mapPath The path to the map file.
   * @return The initialized GameMap, or null if an error occurs during initialization.
   * @throws NullPointerException if mapPath is null.
   */
  public static final GameMap initGameMap(String mapPath) {
    try {
        return GameMap.initMap(mapPath);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
        }
    }
  
  /**
   * Configures the map scale based on the screen information.
   *
   * @param gameMap    The GameMap to configure.
   * @param screenInfo The screen information.
   * @throws NullPointerException if gameMap or screenInfo is null.
   */
  public static final void configureMapScale(GameMap gameMap, ScreenInfo screenInfo) {
    gameMap.getMapScale().put("X", screenInfo.getWidth() / (double) gameMap.getSize().width());
    gameMap.getMapScale().put("Y", screenInfo.getHeight() / (double) gameMap.getSize().height());
    }
  
  /**
   * Draws the initial map based on the provided GameMap, Graphics2D, and ScreenInfo.
   *
   * @param gameMap   The GameMap to draw.
   * @param graphics  The Graphics2D context to draw on.
   * @param screenInfo The screen information.
   * @throws NullPointerException if gameMap, graphics, or screenInfo is null.
   */
  public static final void drawInitialMap(GameMap gameMap, Graphics2D graphics, ScreenInfo screenInfo) {

    graphics.setTransform(zoomedOnPlayer(gameMap, screenInfo.getWidth(),screenInfo.getHeight()));
    drawMap(gameMap, graphics); 
    }
  
  /**
   * The game loop that continuously renders frames using the provided ApplicationContext and GameMap.
   *
   * @param context The ApplicationContext.
   * @param gameMap The GameMap to render.
   * @throws NullPointerException if context or gameMap is null.
   */
  public static final void gameLoop(ApplicationContext context, GameMap gameMap) {
    Optional<Character> optionalPlayer;
    Character player;
    while (true) {
        optionalPlayer = gameMap.getCaseAt(gameMap.getPlayerPosition()).getCharacter();
        player = optionalPlayer.orElseThrow(() -> new NoSuchElementException("No character found"));
        if(player.getHealth() <= 0) {
          System.out.println("GAME LOST\n");
          break;
        }
        context.renderFrame(graphics -> renderGameFrame(gameMap, graphics,GameEventsListener.listenEvents(context, gameMap)));
        gameMap.enemiesMouvements();
        
        gameMap.getCasesToReDraw().clear();
        }
    }
  

  /**
   * Applies a zoom transformation centered on the player's position.
   *
   * @param gameMap      The GameMap containing the player's position.
   * @param screenWidth  The width of the screen.
   * @param screenHeight The height of the screen.
   * @return The AffineTransform representing the zoomed transformation.
   * @throws NullPointerException if gameMap is null.
   */
  private static final AffineTransform zoomedOnPlayer(GameMap gameMap,double screenWidth,double screenHeight) {
    Position playerPosition = gameMap.getPlayerPosition();
    double transX,transY;
    double zoomFactor = 6.0; 
    double tileWidth = gameMap.getMapScale().get("X");
    double tileHeight = gameMap.getMapScale().get("Y"); 

    double playerPixelX = playerPosition.getX() * tileWidth;
    double playerPixelY = playerPosition.getY() * tileHeight;
    transX = (-playerPixelX*6)+(screenWidth / 2.0);
    transY = (-playerPixelY*6)+(screenHeight/ 2.0);
    double scalex = tileWidth * zoomFactor;
    double scaley = tileHeight * zoomFactor;
    
    if(playerPosition.getX() <= 5) transX = ((-(6 * tileWidth))*6)+(screenWidth/ 2.0);
    if(playerPosition.getX() >=gameMap.getSize().width()-5) transX = ((-(gameMap.getSize().width()-6)*tileWidth)*6)+(screenWidth / 2.0);
    if(playerPosition.getY() <= 5) transY = ((-(5 * tileHeight))*6)+(screenHeight/ 2.0);
    if(playerPosition.getY() >= gameMap.getSize().height()-5) transY =  ((-(gameMap.getSize().height()-5)*tileHeight)*6)+(screenHeight / 2.0);
    
      
    
    AffineTransform transform = new AffineTransform();
    transform.translate(transX,transY);
    transform.scale(scalex, scaley);
    
    return transform;
}

 
  /**
   * Renders a game frame based on the provided GameMap, Graphics2D, and draw map decider.
   *
   * @param gameMap        The GameMap to render.
   * @param graphics       The Graphics2D context to draw on.
   * @param drawMapDecider The draw map decider indicating whether to draw the map.
   * @throws NullPointerException if gameMap or graphics is null.
   */
 static final void renderGameFrame(GameMap gameMap, Graphics2D graphics,int drawMapDecider) {

   double screenWidth,screenHeight;
   screenWidth = gameMap.getMapScale().get("X")*gameMap.getSize().width();
   screenHeight = gameMap.getMapScale().get("Y")*gameMap.getSize().height();
   AffineTransform zoomed = zoomedOnPlayer(gameMap, screenWidth, screenHeight);
   graphics.setTransform(zoomed);
   
   if(drawMapDecider == 0 ||gameMap.getDrawedObjectsInfo().get("EraseInventory").equals(true)) {
     drawMap(gameMap, graphics);
   }
   handleInventoryDrawing(gameMap, graphics);

   
   }
 /**
  * Handles the drawing of the inventory based on the provided GameMap and Graphics2D.
  *
  * @param gameMap  The GameMap containing inventory information.
  * @param graphics The Graphics2D context to draw on.
  * @throws NullPointerException if gameMap or graphics is null.
  */
private static final void handleInventoryDrawing(GameMap gameMap, Graphics2D graphics) {
    if (gameMap.getDrawedObjectsInfo().get("DrawInventory").equals(true)) {
        Graph.drawInventory(gameMap, graphics);
        gameMap.getDrawedObjectsInfo().replace("DrawInventory", false);
        gameMap.getDrawedObjectsInfo().replace("InventoryDrawed", true);
        }
    if (gameMap.getDrawedObjectsInfo().get("EraseInventory").equals(true)) {
        
        gameMap.getDrawedObjectsInfo().replace("EraseInventory", false);
        gameMap.getDrawedObjectsInfo().replace("InventoryDrawed", false);
        }
    }



}

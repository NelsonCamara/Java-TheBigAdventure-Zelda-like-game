package com.TheBigAdventure.groundEntities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * Represents a decorative element in the game world.
 * Manages the position and rendering of decorative elements.
 */
public record DecorativeElement(DecorationType decorationType, Position decorationPosition) implements Environnement {
	
    /**
     * Gets the position of the decorative element.
     *
     * @return The position of the decorative element.
     */
  @Override
  public Position getPosition() {
    return decorationPosition;
  }
  
  /**
   * Checks if the decorative element is an obstacle.
   *
   * @return Always returns {@code false} for decorative elements.
   */
  @Override
  public boolean isObstacle() {
    return false;
  }
  
  /**
   * Checks if the entity is a decoration.
   *
   * @return true, indicating that this entity is a decoration.
   */
  @Override
  public boolean isDecoration() {
    return true;
  }
  
  /**
   * Draws the decoration on the screen using the specified graphics context,
   * size dimensions, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the decoration.
   * @param sizeY      The height of the decoration.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap){

    if(decorationType.equals(DecorationType.VOID)) {
      if(gameMap.isMapInitialised()) {
        Graph.drawRect(decorationPosition, sizeX, sizeY, graphics,Color.LIGHT_GRAY);
        return;
        
      }
      else {
        return;
      }
    }
 
    Graph.drawRect(decorationPosition, sizeX, sizeY, graphics,Color.LIGHT_GRAY);
    BufferedImage img = gameMap.getPicturesInMap().get(decorationType.toString());
    Graph.drawEntity(decorationPosition, sizeX, sizeY,graphics,img);

  }
  
  /**
   * Converts the decoration type to its string representation.
   *
   * @return A string representation of the decoration type.
   */
  @Override
  public String typeToString() {
    // TODO Auto-generated method stub
    return decorationType.toString();
  }

}

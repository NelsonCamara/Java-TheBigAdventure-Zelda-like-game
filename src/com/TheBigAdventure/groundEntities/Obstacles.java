package com.TheBigAdventure.groundEntities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The Obstacles class represents obstacles in the game, managing their position and rendering.
 * It implements the Environnement interface, providing methods to retrieve the position,
 * check if it is an obstacle, draw the obstacle on the screen, convert the type to a string representation,
 * and distinguish it from decorations.
 * <p>
 * This class is a record, meaning it is immutable and automatically generates appropriate methods,
 * such as equals, hashCode, and toString, based on its components.
 */
public record Obstacles(ObstacleType obstacleType, Position obstaclePosition) implements Environnement {
	
	  /**
	   * Gets the position of the obstacle.
	   *
	   * @return The position of the obstacle.
	   */
  @Override
  public Position getPosition() {
    return obstaclePosition;
  }

  /**
   * Checks if the entity is an obstacle.
   *
   * @return true, indicating that this entity is an obstacle.
   */
  @Override
  public boolean isObstacle() {
    return true;
  }

  /**
   * Checks if the entity is a decoration.
   *
   * @return false, indicating that this entity is not a decoration.
   */
  @Override
  public boolean isDecoration() {
    return false;
  }
  
  /**
   * Draws the obstacle on the screen using the specified graphics context,
   * size dimensions, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the obstacle.
   * @param sizeY      The height of the obstacle.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap){
    
    BufferedImage img = gameMap.getPicturesInMap().get(obstacleType.toString());
    Graph.drawEntity(obstaclePosition, sizeX, sizeY,graphics,img);

  }

  /**
   * Converts the type of the obstacle to its string representation.
   *
   * @return A string representation of the obstacle type.
   */
  @Override
  public String typeToString() {
    return obstacleType.toString();
  }

}

/**
 * The {@code Biomes} class represents a biome entity in the game world.
 * It implements the {@code Environnement} interface and provides
 * information about the biome type and its position.
 * 
 */
package com.TheBigAdventure.groundEntities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * Represents a biome entity in the game world.
 */
public record Biomes(biomeType typeBiome, Position biomePosition) implements Environnement {
	
    /**
     * Gets the position of the biome.
     *
     * @return The position of the biome.
     */
  @Override
  public Position getPosition() {
    return biomePosition;
  }
  
  /**
   * Checks if the biome is an obstacle.
   *
   * @return {@code true} if the biome is an obstacle, {@code false} otherwise.
   */
  @Override
  public boolean isObstacle() {
    return true;
  }
  
  /**
   * Checks if the biome is a decoration.
   *
   * @return {@code true} if the biome is a decoration, {@code false} otherwise.
   */
  @Override
  public boolean isDecoration() {
    return false;
  }

  /**
   * Draws the biome entity on the graphics context.
   *
   * @param graphics The graphics context to draw on.
   * @param sizeX    The X-size of the entity.
   * @param sizeY    The Y-size of the entity.
   * @param gameMap  The game map containing pictures for different biomes.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap){


    
    BufferedImage img = gameMap.getPicturesInMap().get(typeBiome.toString());
    Graph.drawEntity(biomePosition, sizeX, sizeY,graphics,img);

  }
  
  /**
   * Converts the biome type to a string representation.
   *
   * @return The string representation of the biome type.
   */
  @Override
  public String typeToString() {
    // TODO Auto-generated method stub
    return typeBiome.toString();
  }

}

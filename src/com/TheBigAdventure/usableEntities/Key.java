package com.TheBigAdventure.usableEntities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The Key class represents a key item in the game that can be stored in the player's inventory.
 * It implements the InsideInventory interface, providing methods to retrieve information about the key,
 * such as its name, damage, heal amount (which is 0 for keys), whether it is activable on hand,
 * the type of the item, and methods to draw the key on the game map or in the player's inventory.
 * <p>
 * This class is a record, meaning it is immutable and automatically generates appropriate methods,
 * such as equals, hashCode, and toString, based on its components.
 */
public record Key(String name) implements InsideInventory{
	
	  /**
	   * Gets the name of the key.
	   *
	   * @return The name of the key.
	   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the damage value of the key.
   *
   * @return The damage value of the key, which is always 0 for keys.
   */
  @Override
  public int getDamage() {
    return 0;
  }
  
  /**
   * Checks if the key is activable on hand.
   *
   * @return true, indicating that the key is activable on hand.
   */
  @Override
  public boolean isActivableOnHand() {
    // TODO Auto-generated method stub
    return true;
  }

  /**
   * Draws the key on the game map using the specified graphics context,
   * size dimensions, position, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the key.
   * @param sizeY      The height of the key.
   * @param position   The position where the key should be drawn on the game map.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInMap(Graphics2D graphics, int sizeX, int sizeY, Position position, GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("KEY");
    Graph.drawEntity(position, sizeX, sizeY, graphics,img);
    
  }

  /**
   * Draws the key on the game map using the specified graphics context,
   * size dimensions, position, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the key.
   * @param sizeY      The height of the key.
   * @param position   The position where the key should be drawn on the game map.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInInventory(Graphics2D graphics, int index, GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("KEY");

    Graph.drawEntity(gameMap.getInventoryIndexPositions().get(index),35,35, graphics,img);
    
  }

  /**
   * Gets the type of the key.
   *
   * @return The ItemType enum value representing the key type.
   */
  @Override
  public ItemType typeOfItem() {
    return ItemType.KEY;
  }

  /**
   * Gets the heal amount provided by the key.
   *
   * @return The heal amount provided by the key, which is always 0 for keys.
   */
  @Override
  public int getHealAmount() {
    return 0;
  }



}

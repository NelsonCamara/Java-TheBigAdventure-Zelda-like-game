package com.TheBigAdventure.usableEntities;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The Sword class represents a sword item in the game that can be stored in the player's inventory.
 * It implements the InsideInventory interface, providing methods to retrieve information about the sword,
 * such as its name, damage value, whether it is activable on hand, the type of the item,
 * and methods to draw the sword on the game map or in the player's inventory.
 * <p>
 * This class is a record, meaning it is immutable and automatically generates appropriate methods,
 * such as equals, hashCode, and toString, based on its components.
 */
public record Sword(String name, int damage) implements InsideInventory {

	  /**
	   * Gets the name of the sword.
	   *
	   * @return The name of the sword.
	   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the damage value of the sword.
   *
   * @return The damage value of the sword.
   */
  @Override
  public int getDamage() {
    return damage;
  }

  /**
   * Checks if the sword is activable on hand.
   *
   * @return true, indicating that the sword is activable on hand.
   */
  @Override
  public boolean isActivableOnHand() {
    return true;
  }

  /**
   * Draws the sword on the game map using the specified graphics context,
   * size dimensions, position, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the sword.
   * @param sizeY      The height of the sword.
   * @param position   The position where the sword should be drawn on the game map.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInMap(Graphics2D graphics, int sizeX, int sizeY,Position position,GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("SWORD");
    Graph.drawEntity(position, sizeX, sizeY, graphics,img);
  }

  /**
   * Draws the sword in the player's inventory using the specified graphics context,
   * index, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param index      The index in the player's inventory where the sword should be drawn.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInInventory(Graphics2D graphics,int index, GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("SWORD");

    Graph.drawEntity(gameMap.getInventoryIndexPositions().get(index),35,35, graphics,img);
  }

  /**
   * Gets the type of the sword.
   *
   * @return The ItemType enum value representing the sword type.
   */
  @Override
  public ItemType typeOfItem() {
    return ItemType.SWORD;
  }

  /**
   * Gets the heal amount provided by the sword.
   *
   * @return The heal amount provided by the sword, which is always 0 for swords.
   */
  @Override
  public int getHealAmount() {
    return 0;
  }

}

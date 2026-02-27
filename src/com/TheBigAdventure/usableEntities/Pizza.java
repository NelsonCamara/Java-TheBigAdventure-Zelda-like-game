package com.TheBigAdventure.usableEntities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The Pizza class represents a pizza item in the game that can be stored in the player's inventory.
 * It implements the InsideInventory interface, providing methods to retrieve information about the pizza,
 * such as its name, damage (which is always 0 for pizzas), whether it is activable on hand,
 * the type of the item, heal amount provided by the pizza, and methods to draw the pizza on the game map
 * or in the player's inventory.
 * <p>
 * This class is a record, meaning it is immutable and automatically generates appropriate methods,
 * such as equals, hashCode, and toString, based on its components.
 */
public record Pizza(String name,int healAmount) implements InsideInventory {
	
	  /**
	   * Gets the name of the pizza.
	   *
	   * @return The name of the pizza.
	   */
  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return name;
  }
  
  /**
   * Gets the damage value of the pizza.
   *
   * @return The damage value of the pizza, which is always 0 for pizzas.
   */
  @Override
  public int getDamage() {
    return 0;
  }
  
  /**
   * Checks if the pizza is activable on hand.
   *
   * @return true, indicating that the pizza is activable on hand.
   */
  @Override
  public boolean isActivableOnHand() {
    return true;
  }
  
  /**
   * Gets the type of the pizza.
   *
   * @return The ItemType enum value representing the pizza type.
   */
  @Override
  public ItemType typeOfItem() {
    return ItemType.PIZZA;
  }
  
  /**
   * Draws the pizza on the game map using the specified graphics context,
   * size dimensions, position, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param sizeX      The width of the pizza.
   * @param sizeY      The height of the pizza.
   * @param position   The position where the pizza should be drawn on the game map.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInMap(Graphics2D graphics, int sizeX, int sizeY, Position position, GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("PIZZA");
    Graph.drawEntity(position, sizeX, sizeY, graphics,img);
    
  }

  /**
   * Draws the pizza in the player's inventory using the specified graphics context,
   * index, and game map information.
   *
   * @param graphics   The graphics context used for drawing.
   * @param index      The index in the player's inventory where the pizza should be drawn.
   * @param gameMap    The game map containing information about the game environment.
   */
  @Override
  public void drawInInventory(Graphics2D graphics, int index, GameMap gameMap) {
    BufferedImage img = gameMap.getPicturesInMap().get("PIZZA");

    Graph.drawEntity(gameMap.getInventoryIndexPositions().get(index),35,35, graphics,img);
    
  }
  
  /**
   * Gets the heal amount provided by the pizza.
   *
   * @return The heal amount provided by the pizza.
   */
  @Override
  public int getHealAmount() {
    // TODO Auto-generated method stub
    return 5;
  }

}

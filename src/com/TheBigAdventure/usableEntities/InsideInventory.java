package com.TheBigAdventure.usableEntities;

/**
 * 
 */

import java.awt.Graphics2D;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The InsideInventory interface represents items that the player can carry in their inventory.
 * It defines methods to retrieve information about the item, such as its name, damage, heal amount,
 * whether it is activable on hand, the type of the item, and methods to draw the item on the game map
 * or in the player's inventory.
 * <p>
 * This interface also includes a static method for updating an item in a specific game map case.
 * <p>
 * Implementing classes should provide concrete implementations for these methods based on the specific
 * characteristics of the items they represent.
 * <p>
 * This interface is designed to be implemented by entities that represent wearable items in the game.
 *
 */
public interface InsideInventory {

  public String getName();

  public int getDamage();
  
  public int getHealAmount();

  public boolean isActivableOnHand();

  public ItemType typeOfItem();

  public void drawInMap(Graphics2D graphics, int sizeX, int sizeY, Position position,GameMap gameMap);
  
  public void drawInInventory(Graphics2D graphics,int index,GameMap gameMap);
  
  /**
   * Updates the item in a specific game map case.
   *
   * @param caseToUpdate The Case to be updated.
   * @param item          The optional item to be set in the case.
   * @return              The new instance of Case with the updated item.
   * @throws NullPointerException If either caseToUpdate or item is null.
   */
  static Case updateItemInCase(Case caseToUpdate, Optional<InsideInventory> item) {
    Objects.requireNonNull(caseToUpdate);
    Objects.requireNonNull(item);
    return caseToUpdate.withItem(item);
}

}

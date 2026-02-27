/**
 * The Inventory class represents the management of items in an inventory and provides
 * functionality for interaction with other inventories.
 */
package com.TheBigAdventure.characterEntities;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.usableEntities.InsideInventory;

/**
 * Implementation of the management of items in the inventory and interaction
 * with another inventory
 *
 */
public final class Inventory {
  private final List<InsideInventory> items;
  private final int inventoryMaxSize;
  
  /**
   * Constructs an inventory with the specified maximum size.
   *
   * @param size The maximum size of the inventory.
   */
  protected Inventory(int size) {
    this.items = new ArrayList<>();
    this.inventoryMaxSize = size;
  }

  /**
   * Removes the item at the specified index from the inventory.
   *
   * @param index The index of the item to be removed.
   * @return The removed item, or null if the index is invalid.
   */
  protected final InsideInventory removeItemAtIndex(int index) {
    if (index >= 0 && index < inventoryMaxSize) {
      InsideInventory removedItem = items.get(index);
      items.set(index, null);
      return removedItem;
    }
    return null;
  }
  
  /**
   * Retrieves the item at the specified index from the inventory.
   *
   * @param index The index of the item to be retrieved.
   * @return The item at the specified index, or null if the index is invalid.
   */
  protected final InsideInventory getItem(int index) {
    if (index >= 0 && index < inventoryMaxSize) {
      return items.get(index);
    }
    return null;
  }
  
  /**
   * Swaps the items at the specified indices in the inventory.
   *
   * @param index1 The index of the first item to be swapped.
   * @param index2 The index of the second item to be swapped.
   * @return true if the items were successfully swapped, false otherwise.
   */
  protected final boolean swapItems(int index1, int index2) {
    if (isValidIndex(index1) && isValidIndex(index2)) {
      InsideInventory temp = items.get(index1);
      items.set(index1, items.get(index2));
      items.set(index2, temp);
      return true;
    }
    return false;
  }
  
  /**
   * Checks if the provided index is a valid index within the inventory.
   *
   * @param index The index to be checked.
   * @return {@code true} if the index is valid, {@code false} otherwise.
   */
  private final boolean isValidIndex(int index) {
    return index >= 0 && index < inventoryMaxSize && items.size()>0 && index < items.size();
  }
  
  /**
   * Sets the item at the specified index as the item on hand (index 0).
   *
   * @param index The index of the item to set as the item on hand.
   * @return {@code true} if the item on hand is set successfully, {@code false} otherwise.
   */
  public boolean setItemOnHand(int index) {
    if (isValidIndex(index)) {
      return swapItems(0, index);
    }
    return false;
  }
  
  /**
   * Retrieves the item currently set as the item on hand (index 0).
   *
   * @return The item currently set as the item on hand.
   */
  public final InsideInventory getItemOnHand() {
    return getItem(0);
  }
  
  /**
   * Exchanges the item at the specified index with an item from another inventory at a given index.
   *
   * @param otherInventory The other inventory to exchange items with.
   * @param thisIndex      The index of the item in this inventory to exchange.
   * @param otherIndex     The index of the item in the other inventory to exchange.
   * @return {@code true} if the exchange is successful, {@code false} otherwise.
   */
  protected final boolean exchangeWith(Inventory otherInventory, int thisIndex, int otherIndex) {
    if (isValidIndex(thisIndex) && otherInventory.isValidIndex(otherIndex)) {
      InsideInventory itemFromThis = items.get(thisIndex);
      InsideInventory itemFromOther = otherInventory.items.get(otherIndex);

      items.set(thisIndex, itemFromOther);
      otherInventory.items.set(otherIndex, itemFromThis);

      return true;
    }
    return false;
  }
  
  /**
   * Finds a free index in the inventory where an item can be placed.
   *
   * @return The first free index found or -1 if no free index is available.
   */
  private final int findFreeIndex() {
    for (int i = 1; i < inventoryMaxSize; i++) {
      if (items.get(i) == null) {
        return i;
      }
    }
    return items.get(0) == null ? 0 : -1;

  }
  
  /**
   * Sends the item at the specified index to another inventory, placing it in the first available slot.
   *
   * @param index          The index of the item to be sent.
   * @param otherInventory The inventory to which the item is sent.
   * @return {@code true} if the item is successfully sent, {@code false} otherwise.
   */
  protected final boolean sendTo(int index, Inventory otherInventory) {
    if (isValidIndex(index) && items.get(index) != null) {
      InsideInventory itemToSend = items.get(index);

      int otherIndex = otherInventory.findFreeIndex();
      if (otherIndex != -1) {
        otherInventory.items.set(otherIndex, itemToSend);
        items.set(index, null);
        return true;
      }
    }
    return false;
  }
  

  /**
   * Draws items from the inventory on the provided graphics context.
   *
   * @param graphics The graphics context to draw on.
   * @param gameMap  The game map containing information about the world.
   */
  public final void drawItems(Graphics2D graphics,GameMap gameMap) {
    Objects.requireNonNull(graphics);
    Objects.requireNonNull(gameMap);
    for (int index = 0; index < items.size(); index++) {
      InsideInventory item = items.get(index);
      if(item != null) item.drawInInventory(graphics, index, gameMap);
  }
 }
  
  /**
   * Retrieves the list of items in the inventory.
   *
   * @return The list of items in the inventory.
   */
  public final List<InsideInventory> getItems(){
    return items;
  }
  
  /**
   * Returns a string representation of the items in the inventory.
   *
   * @return A string representation of the items in the inventory.
   */
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append(items.toString());
    return builder.toString();
  }
  
  /**
   * Retrieves the maximum size of the inventory.
   *
   * @return The maximum size of the inventory.
   */
  public final int getInventoryMaxSize() {
    return inventoryMaxSize;
  }

}

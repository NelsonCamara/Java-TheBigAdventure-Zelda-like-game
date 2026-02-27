package com.TheBigAdventure.usableEntities;

import java.util.Map;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.ObjectFromSkin;

/**
 * The ItemType enum represents different types of items that can be created in the game.
 * Each item type has a specific implementation of the InsideInventory interface
 * and a method to create an instance of that item based on an ObjectFromSkin instance.
 */
public enum ItemType {
  SWORD {
	    /**
	     * Creates a Sword instance based on the provided ObjectFromSkin.
	     *
	     * @param object The ObjectFromSkin containing information about the sword.
	     * @return A new instance of Sword.
	     * @throws NullPointerException If the input ObjectFromSkin is null.
	     */
    @Override
    public InsideInventory createItem(ObjectFromSkin object) {
      Objects.requireNonNull(object);
      String swordName;
      int swordDamage;

      Map<String, String> swordFieldsMap = object.getObjectValues();
      swordName = swordFieldsMap.get("name");
      swordDamage = Integer.parseInt(swordFieldsMap.get("damage"));

      return new Sword(swordName, swordDamage);
    }
  },
  
  KEY{
	    /**
	     * Creates a Key instance based on the provided ObjectFromSkin.
	     *
	     * @param object The ObjectFromSkin containing information about the key.
	     * @return A new instance of Key.
	     * @throws NullPointerException If the input ObjectFromSkin is null.
	     */
    @Override
    public InsideInventory createItem(ObjectFromSkin object) {
      Objects.requireNonNull(object);
      String keyName;

      Map<String, String> itemFieldsMap = object.getObjectValues();
      keyName = itemFieldsMap.get("name");


      return new Key(keyName);
    }
  },
  PIZZA{
	    /**
	     * Creates a Pizza instance based on the provided ObjectFromSkin.
	     *
	     * @param object The ObjectFromSkin containing information about the pizza.
	     * @return A new instance of Pizza.
	     * @throws NullPointerException If the input ObjectFromSkin is null.
	     */
    @Override
    public InsideInventory createItem(ObjectFromSkin object) {
      Objects.requireNonNull(object);
      String pizzaName;
      int healAmount;
      Map<String, String> pizzaFieldsMap = object.getObjectValues();
      pizzaName= pizzaFieldsMap.get("name");
      healAmount = Integer.parseInt(pizzaFieldsMap.get("health"));
      return new Pizza(pizzaName,healAmount);
    }
    
  };
	
	  /**
	   * Converts a string representation of an item type to the corresponding enum value.
	   *
	   * @param skin The string representation of the item type.
	   * @return The ItemType enum value matching the input string.
	   * If no match is found, returns null.
	   */
  public static ItemType fromString(String skin) {

    for (ItemType type : ItemType.values()) {
      if (type.name().equalsIgnoreCase(skin)) {
        return type;
      }
    }
    return null;
  }
  
  /**
   * Creates an instance of the specific item type based on the provided ObjectFromSkin.
   *
   * @param object The ObjectFromSkin containing information about the item.
   * @return A new instance of InsideInventory representing the specific item type.
   * @throws NullPointerException If the input ObjectFromSkin is null.
   */
  public abstract InsideInventory createItem(ObjectFromSkin object);

}

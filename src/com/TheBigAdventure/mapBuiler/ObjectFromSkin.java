package com.TheBigAdventure.mapBuiler;

import java.util.Map;
import java.util.Objects;

import com.TheBigAdventure.characterEntities.CharacterType;
import com.TheBigAdventure.characterEntities.PlayerSkins;
import com.TheBigAdventure.groundEntities.DecorationType;
import com.TheBigAdventure.groundEntities.ObstacleType;
import com.TheBigAdventure.groundEntities.biomeType;
import com.TheBigAdventure.usableEntities.ItemType;

/**
 * Represents an object in the map based on its skin, values, and whether it's an entity or an element.
 */
public final class ObjectFromSkin {
  private final String objectSkin;
  private final Map<String, String> objectValues;
  private final boolean entityOrElement;
  
  /**
   * Constructs an ObjectFromSkin with the specified parameters.
   *
   * @param objectSkin     The skin of the object.
   * @param entityOrElement A boolean indicating whether the object is an entity or an element.
   * @param objectValues   The values associated with the object.
   */
  protected ObjectFromSkin(String objectSkin, boolean entityOrElement, Map<String, String> objectValues) {
    Objects.requireNonNull(objectSkin);
    Objects.requireNonNull(objectValues);
    this.objectSkin = objectSkin;
    this.objectValues = objectValues;
    this.entityOrElement = entityOrElement;
  }

  /**
   * Retrieves the values associated with the object.
   *
   * @return The object values.
   */
  public final Map<String, String> getObjectValues() {
    return objectValues;
  }
  
  /**
   * Checks if the object is an entity.
   *
   * @return true if the object is an entity, false otherwise.
   */
  protected boolean getIsEntityOrElement() {
    return entityOrElement;
  }

  /**
   * Retrieves the skin of the object.
   *
   * @return The object skin.
   */
  public String getObjectSkin() {
    return objectSkin;
  }

  /**
   * Parses the position value from the object values and returns a Position object.
   *
   * @param value The position value from the object values.
   * @return A Position object representing the parsed position.
   */
  public Position parsePosition(String value) {
    String[] parts = value.replaceAll("[()]", "").split(",");
    int x = Integer.parseInt(parts[0].trim());
    int y = Integer.parseInt(parts[1].trim());
    return new Position(x, y);
  }

  /**
   * Retrieves the position of the object.
   *
   * @return The position of the object.
   */
  protected Position getPosition() {
    return parsePosition(objectValues.get("position"));
  }
  
  /**
   * Checks if the object is a character.
   *
   * @return true if the object is a character, false otherwise.
   */
  protected boolean isCharacter() {
    if (CharacterType.fromString(objectSkin) != null || PlayerSkins.fromString(objectSkin) != null) {
      return true;
    }
    return false;

  }

  /**
   * Checks if the object is an environment element.
   *
   * @return true if the object is an environment element, false otherwise.
   */
  protected boolean isEnvironnement() {
    if (ObstacleType.fromString(objectSkin) != null || DecorationType.fromString(objectSkin) != null || biomeType.fromString(objectSkin)!=null) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the object is inside an inventory.
   *
   * @return true if the object is inside an inventory, false otherwise.
   */
  protected boolean isInsideInventory() {
    if (ItemType.fromString(objectSkin) != null) {
      return true;
    }
    return false;

  }

  /**
   * Checks if the object is a player.
   *
   * @return true if the object is a player, false otherwise.
   */
  protected boolean isPlayer() {
    Map<String, String> characterFieldsMap = getObjectValues();
    if (characterFieldsMap.containsKey("player") && characterFieldsMap.get("player").equals("true")) {
      return true;
    }
    return false;

  }

  /**
   * Determines the type of the object (Character, Environment, InsideInventory).
   *
   * @return The type of the object.
   */
  protected String detType() {
    if (isCharacter()) {
      return "Character";
    }
    if (isEnvironnement()) {
      return "Environnement";
    }
    if (isInsideInventory()) {
      return "InsideInventory";
    }
    return "ERROR no type found for entitySkin";

  }

  /**
   * Returns a string representation of the object.
   *
   * @return A string representation of the object.
   */
  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append(objectSkin).append(" : ").append(objectValues.toString());
    return builder.toString();
  }

}

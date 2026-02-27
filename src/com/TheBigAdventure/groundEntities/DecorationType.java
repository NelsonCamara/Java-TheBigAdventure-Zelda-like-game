package com.TheBigAdventure.groundEntities;

import java.util.Map;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * Represents different decoration models in the game world.
 */
public enum DecorationType {
  ALGAE, CLOUD, FLOWER, FOLIAGE, GRASS, LADDER, LILY, PLANK, REED, ROAD, SPROUT, TILE, TRACK, VINE,VOID;
	
    /**
     * Converts a string representation of a decoration type to the corresponding enum constant.
     *
     * @param skin The string representation of the decoration type.
     * @return The decoration type enum constant.
     */
  public static DecorationType fromString(String skin) {
    for (DecorationType type : DecorationType.values()) {
      if (type.name().equalsIgnoreCase(skin)) {
        return type;
      }
    }
    return null;
  }
  
  /**
   * Loads an environment based on the provided object skin and values.
   *
   * @param object The object containing skin and values information.
   * @return The loaded environment represented by an instance of {@code DecorativeElement}.
   * @throws NullPointerException If the provided object is null.
   */
  public static Environnement loadEnvironnement(ObjectFromSkin object) {
    Objects.requireNonNull(object);
    DecorationType decoType;
    Position decoPosition;
    decoType = DecorationType.fromString(object.getObjectSkin());
    Map<String, String> decoFieldsMap = object.getObjectValues();
    decoPosition = object.parsePosition(decoFieldsMap.get("position"));
    return new DecorativeElement(decoType, decoPosition);
  }
}

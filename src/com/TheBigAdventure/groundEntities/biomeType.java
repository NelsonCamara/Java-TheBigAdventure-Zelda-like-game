package com.TheBigAdventure.groundEntities;

import java.util.Map;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * Represents different types of biomes in the game world.
 */
public enum biomeType {
  ICE, LAVA, WATER;

	
    /**
     * Converts a string representation of a biome type to the corresponding enum constant.
     *
     * @param skin The string representation of the biome type.
     * @return The biome type enum constant.
     * @throws IllegalArgumentException If the provided string does not match any biome type.
     */
  public static biomeType fromString(String skin) throws IllegalArgumentException {
    for (biomeType type : biomeType.values()) {
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
   * @return The loaded environment represented by an instance of {@code Biomes}.
   * @throws NullPointerException     If the provided object is null.
   * @throws IllegalArgumentException If the object skin does not match any biome type.
   */
  public static Environnement loadEnvironnement(ObjectFromSkin object) {
    Objects.requireNonNull(object);
    biomeType typeBiome;
    Position biomePosition;

    typeBiome = biomeType.fromString(object.getObjectSkin());
    Map<String, String> decoFieldsMap = object.getObjectValues();
    biomePosition = object.parsePosition(decoFieldsMap.get("position"));

    return new Biomes(typeBiome,biomePosition);
  }

}

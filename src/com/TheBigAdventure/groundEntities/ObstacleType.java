package com.TheBigAdventure.groundEntities;

import java.util.Map;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The ObstacleType enum represents different obstacle models in the game.
 * It includes various types of obstacles that can be used in the game environment.
 */
public enum ObstacleType {
  BED, BOG, BOMB, BRICK, CHAIR, CLIFF, DOOR, FENCE, FORT, GATE, HEDGE, HOUSE, HUSK, HUSKS, LOCK, MONITOR, PIANO, PILLAR,
  PIPE, ROCK, RUBBLE, SHELL, SIGN, SPIKE, STATUE, STUMP, TABLE, TOWER, TREE, TREES, WALL;

	  /**
	   * Converts a string representation of an obstacle type to the corresponding enum value.
	   *
	   * @param skin The string representation of the obstacle type.
	   * @return The ObstacleType enum value matching the input string.
	   * @throws IllegalArgumentException If the input string does not match any enum value.
	   */
  public static ObstacleType fromString(String skin) throws IllegalArgumentException {
    for (ObstacleType type : ObstacleType.values()) {
      if (type.name().equalsIgnoreCase(skin)) {
        return type;
      }
    }
    return null;
  }

  /**
   * Loads an environment entity based on the provided ObjectFromSkin instance.
   *
   * @param object The ObjectFromSkin instance containing information about the environment entity.
   * @return The loaded environment entity.
   * @throws NullPointerException     If the input ObjectFromSkin is null.
   * @throws IllegalArgumentException If the obstacle type cannot be determined from the input skin string.
   */
  public static final Environnement loadEnvironnement(ObjectFromSkin object) {
    Objects.requireNonNull(object);
    ObstacleType obstacleType;
    Position obstaclePosition;

    obstacleType = ObstacleType.fromString(object.getObjectSkin());
    Map<String, String> decoFieldsMap = object.getObjectValues();
    obstaclePosition = object.parsePosition(decoFieldsMap.get("position"));

    return new Obstacles(obstacleType, obstaclePosition);
  }

}

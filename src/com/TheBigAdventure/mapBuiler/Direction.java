package com.TheBigAdventure.mapBuiler;

import java.util.Random;

/**
 * Enum representing directions for future movements. Provides predefined
 * directions: UP, DOWN, LEFT, RIGHT, and INIT.
 */
public enum Direction {
  UP, DOWN, LEFT, RIGHT, INIT;

  /**
   * Generates and returns a random Direction enum value.
   *
   * @return A random Direction enum value.
   */
  public static Direction getRandomDirection() {
    Direction[] directions = Direction.values();
    Random random = new Random();

    int randomIndex = random.nextInt(directions.length - 1); 
    return directions[randomIndex];
  }

  /**
   * Converts a string representation of a direction to the corresponding
   * Direction enum.
   *
   * @param directionString The string representation of the direction.
   * @return The Direction enum corresponding to the provided string.
   * @throws IllegalArgumentException If the provided string does not match any
   *                                  valid direction.
   */
  public static Direction fromString(String directionString) throws IllegalArgumentException {
    for (Direction type : Direction.values()) {
      if (type.name().equalsIgnoreCase(directionString)) {
        return type;
      }
    }
    return null;
  }

}

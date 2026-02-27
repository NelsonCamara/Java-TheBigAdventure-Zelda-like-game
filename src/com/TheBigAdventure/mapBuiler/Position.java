package com.TheBigAdventure.mapBuiler;

import java.util.Objects;

/**
 * Represents a position on a grid and provides methods for managing and manipulating positions.
 *
 * <p>The coordinates (x, y) specify the position on the grid. This class includes methods for
 * retrieving the coordinates, setting a new position, calculating the Manhattan distance to another
 * position, checking if two positions are in contact, and calculating a new position based on a
 * specified direction.
 *
 */
public class Position {
  private int x;
  private int y;
  
  /**
   * Constructs a Position with the specified x and y coordinates.
   *
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   */
  public Position(int x, int y) {
      this.x = x;
      this.y = y;
  }
  
  /**
   * Gets the x-coordinate of the position.
   *
   * @return The x-coordinate.
   */
  public int getX() {
    return x;
  }
  
  /**
   * Gets the y-coordinate of the position.
   *
   * @return The y-coordinate.
   */
  public int getY() {
    return y;
  }
  
  /**
   * Sets a new position based on the coordinates of the provided Position.
   *
   * @param newPos The new position.
   */
  public void setNewPosition(Position newPos) {
    x = newPos.getX();
    y = newPos.getY();
  }
  
  /**
   * Compares this position to the specified object for equality.
   *
   * @param o The object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Position position = (Position) o;
      return x == position.x && y == position.y;
  }

  /**
   * Generates a hash code for this position.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
      return Objects.hash(x, y);
  }
  
  /**
   * Calculates the Manhattan distance between this position and another position.
   *
   * @param other The other position.
   * @return The Manhattan distance.
   */
  public int manhattanDistanceTo(Position other) {
    return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
  }
  
  /**
   * Checks if this position is in contact with another position (Manhattan distance is 1).
   *
   * @param other The other position.
   * @return {@code true} if in contact, {@code false} otherwise.
   */
  public boolean isInContactWith(Position other) {
    return manhattanDistanceTo(other) <= 1;
  }
  
  /**
   * Calculates a new position based on a specified direction.
   *
   * @param direction The direction in which to calculate the new position.
   * @return The new position.
   * @throws IllegalArgumentException If an unknown direction is provided.
   */
  public Position calculateNewPosition(Direction direction) {
    switch (direction) {
    case UP:
      return new Position(this.x, this.y - 1);
    case DOWN:
      return new Position(this.x, this.y + 1);
    case RIGHT:
      return new Position(this.x + 1, this.y);
    case LEFT:
      return new Position(this.x - 1, this.y);
    default:
      throw new IllegalArgumentException("Unknown direction: " + direction);
    }
  }
  
  /**
   * Returns a string representation of this position.
   *
   * @return A string representation including the x and y coordinates.
   */
  @Override
  public String toString() {
      return "Position{" +
              "x=" + x +
              ", y=" + y +
              '}';
  }

}

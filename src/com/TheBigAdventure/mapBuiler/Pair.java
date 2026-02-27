package com.TheBigAdventure.mapBuiler;

/**
 * A generic class representing a pair of objects.
 *
 * @param <T> The type of objects in the pair.
 */
public class Pair<T> {
  /*
   * Permit to stock to Objects in one.
   * 
   * 
   * 
   */
  private T first;
  private T second;

  /**
   * Constructs a Pair with the specified first and second objects.
   *
   * @param first  The first object.
   * @param second The second object.
   */
  public Pair(T first, T second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Retrieves the first object in the pair.
   *
   * @return The first object.
   */
  public T getFirst() {
    return first;
  }
  
  /**
   * Sets the first object in the pair.
   *
   * @param first The new value for the first object.
   */
  public void setFirst(T first) {
    this.first = first;
  }
  
  /**
   * Retrieves the second object in the pair.
   *
   * @return The second object.
   */
  public T getSecond() {
    return second;
  }
  
  /**
   * Sets the second object in the pair.
   *
   * @param second The new value for the second object.
   */
  public void setSecond(T second) {
    this.second = second;
  }

}

package com.TheBigAdventure.mapBuiler;

/**
 * Interface to manipulate entities that need to be updated in the game.
 * 
 * <p>The {@code Updatable} interface defines a method {@code update()} that should be implemented
 * by classes representing entities requiring periodic updates in the game.
 * 
 * <p>Implementing this interface allows an entity to define custom logic for its update process,
 * which is typically called at regular intervals during the game loop.
 */
public interface Updatable {

  void update();
}

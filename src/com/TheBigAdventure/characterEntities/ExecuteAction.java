/**
 * The ExecuteAction interface represents an action that can be executed by a character in the game.
 * Implementing classes define specific actions that can be performed.
 */
package com.TheBigAdventure.characterEntities;
import com.TheBigAdventure.mapBuiler.GameMap;

public interface ExecuteAction {
  /**
   * Executes the defined action on the given character within the context of the game map.
   *
   * @param character The character on which the action is executed.
   * @param gameMap   The game map providing the context for the action.
   */
  void execute(Character character,GameMap gameMap);
}

/**
 * The GameEventsListener class is responsible for handling keyboard events in the game.
 * It processes user input and triggers corresponding actions in the game map and characters.
 */
package com.TheBigAdventure.graphic;

import java.util.Objects;

import com.TheBigAdventure.characterEntities.Character;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;

import com.TheBigAdventure.mapBuiler.Direction;
import com.TheBigAdventure.mapBuiler.GameMap;

/**
 * The GameEventsListener class is responsible for handling keyboard events in the game.
 * It processes user input and triggers corresponding actions in the game map and characters.
 */
public final class GameEventsListener {
  private static Event lastEvent = null;
  private static long lastEventTime = 0;
  
  /**
   * Gets the keyboard event from the provided ApplicationContext and returns it as a string.
   *
   * @param context The ApplicationContext for handling events.
   * @return The string representation of the keyboard event.
   */
  private static final String getKeyboardEvent(ApplicationContext context) {
    Event event = context.pollOrWaitEvent(10);
    if (event == null) {
      return null;
    }
    if (event.getAction() == Event.Action.KEY_RELEASED) {
      return null;
    }

    long currentTime = System.currentTimeMillis();

    if (lastEvent != null && event.getKey().equals(lastEvent.getKey()) && (currentTime - lastEventTime) < 200) {
      return null;
    } else {
      lastEvent = event;
      lastEventTime = currentTime;
      return event.getKey().toString();
    }
  }
  
  /**
   * Listens for events from the provided ApplicationContext and performs corresponding actions
   * in the specified GameMap.
   *
   * @param context  The ApplicationContext for handling events.
   * @param gameMap  The GameMap to apply actions on.
   * @return Returns 1 if an action related to inventory is performed, 0 for non-inventory actions, and -1 otherwise.
   */
  protected static int listenEvents(ApplicationContext context, GameMap gameMap) {
    Objects.requireNonNull(context);
    Objects.requireNonNull(gameMap);

    String eventString = getKeyboardEvent(context);
    Character character = gameMap.characterFromMap(gameMap);
    if (eventString != null) {
      if (gameMap.getDrawedObjectsInfo().get("InventoryDrawed").equals(true)) {
        handleInventoryEvents(eventString, gameMap, character);
        return 1;
      } else {
        handleNonInventoryEvents(eventString, gameMap, character);
        
        return 0;
      }

    }
    return -1;
  }
  
  /**
   * Handles events related to the inventory, such as cursor movement and item manipulation.
   *
   * @param eventString The string representation of the keyboard event.
   * @param gameMap     The GameMap to apply actions on.
   * @param character   The Character interacting with the inventory.
   */
  private static void handleInventoryEvents(String eventString, GameMap gameMap, Character character) {
    Direction cursorDirection = Direction.fromString(eventString);
    if (cursorDirection != null) {
      handleInventoryCursor(cursorDirection, gameMap);
    } else {
      processInventoryCommands(eventString, gameMap, character);
    }
  }

  /**
   * Handles cursor movement in the inventory based on the specified direction.
   *
   * @param cursorDirection The direction of the inventory cursor movement.
   * @param gameMap         The GameMap to apply actions on.
   */
  private static void handleInventoryCursor(Direction cursorDirection, GameMap gameMap) {
    gameMap.moveInventoryCursor(cursorDirection, gameMap);
    gameMap.getDrawedObjectsInfo().replace("DrawInventory", true);
  }
  
  /**
   * Processes commands related to the inventory, such as opening/closing and item selection.
   *
   * @param eventString The string representation of the keyboard event.
   * @param gameMap     The GameMap to apply actions on.
   * @param character   The Character interacting with the inventory.
   */
  private static void processInventoryCommands(String eventString, GameMap gameMap, Character character) {
    if (eventString.equals("I")) {
      gameMap.getDrawedObjectsInfo().replace("EraseInventory", true);
    }

    if (eventString.equals("SPACE")) {
      if (character.characterGetInventory().setItemOnHand(gameMap.getInventoryCursorIndexPosition())) {
        gameMap.getDrawedObjectsInfo().replace("DrawInventory", true);
        System.out.println(character.characterGetInventory());
        return;
      }
      gameMap.getDrawedObjectsInfo().replace("DrawInventory", false);
      return;

    }

  }
  
  /**
   * Handles non-inventory events, such as player movement and special actions.
   *
   * @param eventString The string representation of the keyboard event.
   * @param gameMap     The GameMap to apply actions on.
   * @param character   The Character performing non-inventory actions.
   */
  private static void handleNonInventoryEvents(String eventString, GameMap gameMap, Character character) {
    Direction playerDirection = Direction.fromString(eventString);
    if (playerDirection != null) {
      handlePlayerMovement(playerDirection, gameMap, character);
    } else {
      handleSpecialActions(eventString, gameMap, character);
    }
  }
  
  /**
   * Handles player movement based on the specified direction.
   *
   * @param playerDirection The direction of player movement.
   * @param gameMap         The GameMap to apply actions on.
   * @param character       The Character performing the movement.
   */
  private static void handlePlayerMovement(Direction playerDirection, GameMap gameMap, Character character) {
    gameMap.moveMovableEntity(character, playerDirection, gameMap);
  }
  
  /**
   * Handles special actions triggered by keyboard events.
   *
   * @param eventString The string representation of the keyboard event.
   * @param gameMap     The GameMap to apply actions on.
   * @param character   The Character performing special actions.
   */
  private static void handleSpecialActions(String eventString, GameMap gameMap, Character character) {
    if (eventString.equals("SPACE")) {
      gameMap.getGrid().get(character.getPosCaseInFront()).getCaseState().execute(character, gameMap);
      return;
    }

    if (eventString.equals("I")) {

      System.out.println(gameMap.getDrawedObjectsInfo().toString());
      if (gameMap.getDrawedObjectsInfo().get("InventoryDrawed").equals(false)) {
        gameMap.getDrawedObjectsInfo().replace("DrawInventory", true);
        return;
      }

      if (gameMap.getDrawedObjectsInfo().get("InventoryDrawed").equals(true)) {
        gameMap.getDrawedObjectsInfo().replace("EraseInventory", true);
        return;
      }
    }
  }

}

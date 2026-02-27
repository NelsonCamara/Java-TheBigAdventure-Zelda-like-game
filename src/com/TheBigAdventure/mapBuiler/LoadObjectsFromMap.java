package com.TheBigAdventure.mapBuiler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.characterEntities.Character;
import com.TheBigAdventure.characterEntities.CharacterType;
import com.TheBigAdventure.characterEntities.Player;
import com.TheBigAdventure.groundEntities.DecorationType;
import com.TheBigAdventure.groundEntities.Environnement;
import com.TheBigAdventure.groundEntities.ObstacleType;
import com.TheBigAdventure.groundEntities.biomeType;
import com.TheBigAdventure.usableEntities.InsideInventory;
import com.TheBigAdventure.usableEntities.ItemType;

public class LoadObjectsFromMap {

  private final List<ObjectFromSkin> objectsToLoad;

  /**
   * The class responsible for loading various objects from a list of
   * ObjectFromSkin into the game map. This includes loading characters, items,
   * and environmental elements at specified positions.
   */

  public LoadObjectsFromMap(List<ObjectFromSkin> objectToLoad) {
    Objects.requireNonNull(objectToLoad);
    this.objectsToLoad = objectToLoad;

  }

  /**
   * Loads a character into the game map based on the given ObjectFromSkin and
   * position. It determines whether the character is a player or another type of
   * character.
   *
   * @param gameMap        The game map where the character will be loaded.
   * @param object         ObjectFromSkin containing the character's data.
   * @param objectPosition The position on the map where the character will be
   *                       placed.
   * @param grid           The grid representing the current state of the game
   *                       map.
   */
  private void loadCharacter(GameMap gameMap, ObjectFromSkin object, Position objectPosition,
      Map<Position, Case> grid) {

    if (object.isCharacter()) {

      Character characterEntity;
      Case caseGrid;

      if (object.isPlayer()) {

        characterEntity = Player.playerFromFile(object);
        gameMap.setPlayerPosition(objectPosition);
        // System.out.println(characterEntity.toString());
      } else {
        CharacterType entityType;
        entityType = CharacterType.fromString(object.getObjectSkin());
        characterEntity = entityType.createCharacter(object);
      }

      caseGrid = grid.get(objectPosition);
      if (caseGrid == null) {
        grid.put(objectPosition, new Case(characterEntity));

      } else {

        gameMap.updateCase(objectPosition, Optional.ofNullable(characterEntity), Character::updateCharacterInCase);

      }

      return;

    }
  }

  /**
   * Loads an item into the game map based on the given ObjectFromSkin and
   * position. It creates an item of a specific type based on the information in
   * ObjectFromSkin.
   *
   * @param gameMap        The game map where the item will be loaded.
   * @param object         ObjectFromSkin containing the item's data.
   * @param objectPosition The position on the map where the item will be placed.
   * @param grid           The grid representing the current state of the game
   *                       map.
   */
  private void loadItem(GameMap gameMap, ObjectFromSkin object, Position objectPosition, Map<Position, Case> grid) {

    if (object.isInsideInventory()) {
      ItemType entityItemType;
      Case caseGrid;
      InsideInventory item;

      entityItemType = ItemType.fromString(object.getObjectSkin());
      item = entityItemType.createItem(object);
      caseGrid = grid.get(objectPosition);
      if (caseGrid == null) {
        grid.put(objectPosition, new Case(item));

      } else {
        gameMap.updateCase(object.getPosition(), Optional.ofNullable(item), InsideInventory::updateItemInCase);

      }

      return;
    }

  }

  /**
   * Loads an environmental element into the game map based on the given
   * ObjectFromSkin and position. It determines the type of environment to create
   * based on the information in ObjectFromSkin.
   *
   * @param gameMap        The game map where the environmental element will be
   *                       loaded.
   * @param object         ObjectFromSkin containing the environmental element's
   *                       data.
   * @param objectPosition The position on the map where the environmental element
   *                       will be placed.
   * @param grid           The grid representing the current state of the game
   *                       map.
   */
  private void loadEnvironnement(GameMap gameMap, ObjectFromSkin object, Position objectPosition,
      Map<Position, Case> grid) {

    if (object.isEnvironnement()) {
      Case caseGrid;
      Environnement environnementEntity = null;
      if (DecorationType.fromString(object.getObjectSkin()) != null) {
        environnementEntity = DecorationType.loadEnvironnement(object);

      }
      if (ObstacleType.fromString(object.getObjectSkin()) != null) {
        environnementEntity = ObstacleType.loadEnvironnement(object);

      }
      if (biomeType.fromString(object.getObjectSkin()) != null) {
        environnementEntity = biomeType.loadEnvironnement(object);

      }
      caseGrid = grid.get(objectPosition);
      if (caseGrid == null) {
        caseGrid = new Case(environnementEntity);
        grid.put(objectPosition, caseGrid);
        // System.out.println(grid.get(objectPosition).toString());
        // System.out.println(objectPosition.toString());

      } else {

        gameMap.updateCase(object.getPosition(), environnementEntity, Environnement::updateEnvironnementInCase);

      }

      return;
    }
  }

  /**
   * Loads all objects from the provided list into the game map. This method
   * iterates through each ObjectFromSkin and determines its type (character,
   * item, or environment) before loading it into the map.
   *
   * @param gameMap The game map where objects will be loaded.
   * @return The updated grid of the game map after all objects have been loaded.
   */
  public Map<Position, Case> loadObjectsInMap(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    Position objectPosition;
    Map<Position, Case> grid = null;
    for (ObjectFromSkin object : objectsToLoad) {
      Objects.requireNonNull(objectsToLoad);

      objectPosition = object.getPosition();
      
      grid = gameMap.getGrid();
      loadCharacter(gameMap, object, objectPosition, grid);
      loadEnvironnement(gameMap, object, objectPosition, grid);
      loadItem(gameMap, object, objectPosition, grid);

    }
    return gameMap.getGrid();
  }

}

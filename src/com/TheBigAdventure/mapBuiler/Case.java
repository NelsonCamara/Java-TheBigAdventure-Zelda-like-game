package com.TheBigAdventure.mapBuiler;

import java.awt.Graphics2D;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.characterEntities.ActionType;
import com.TheBigAdventure.characterEntities.Character;
import com.TheBigAdventure.groundEntities.Environnement;
import com.TheBigAdventure.usableEntities.InsideInventory;

/**
 * The Case class represents a grid cell in the game map. It can contain various entities such as
 * Environnement, Character, and InsideInventory. The state of the Case is determined by the ActionType,
 * indicating the type of action that can be performed on the cell.
 * <p>
 * This class provides methods to create instances of Case with different entities, check if the case is walkable,
 * retrieve information about the entities in the case, and draw the entities on the game map.
 * <p>
 * Instances of this class are immutable and can be updated using methods like withCharacterEntity, withItem, and withEnvironnementEntity.
 */
public final class Case {

  private final Environnement environnementEntity;
  private final Optional<Character> characterEntity;
  private final Optional<InsideInventory> item;
  private final ActionType caseState;

  protected Case(Environnement environnementEntity, Character characterEntity, InsideInventory item) {
    Objects.requireNonNull(environnementEntity, "Environnement cant be null");


    this.environnementEntity = environnementEntity;
    this.characterEntity = Optional.ofNullable(characterEntity);
    this.item = Optional.ofNullable(item);
    
    if(environnementEntity.typeToString().equals("DOOR")) this.caseState = ActionType.CONTAINS_DOOR;
    else if(item != null) {
      this.caseState = ActionType.CONTAINS_ITEM; 
    }
    else if(characterEntity != null) {
      if(characterEntity.isEnemy()) this.caseState = ActionType.CONTAINS_ENEMY;
      else this.caseState = ActionType.NO_ACTION_AVAILBLE; 
      
    }
    else {
      this.caseState = ActionType.NO_ACTION_AVAILBLE; 
    }

  }

  protected Case(Character characterEntity) {

    Objects.requireNonNull(characterEntity);

    this.environnementEntity = null;
    this.characterEntity = Optional.ofNullable(characterEntity);
    this.item = Optional.ofNullable(null);
    if(characterEntity.isEnemy()) this.caseState = ActionType.CONTAINS_ENEMY;
    else this.caseState = ActionType.NO_ACTION_AVAILBLE; 
    
  

  }

  protected Case(Environnement environnementEntity) {
    Objects.requireNonNull(environnementEntity, "Environnement cant be null");

    this.environnementEntity = environnementEntity;
    this.characterEntity = Optional.ofNullable(null);
    this.item = Optional.ofNullable(null);
    if(environnementEntity.typeToString().equals("DOOR")) this.caseState = ActionType.CONTAINS_DOOR;
    else this.caseState = ActionType.NO_ACTION_AVAILBLE; 

  }

  protected Case(InsideInventory item) {

    Objects.requireNonNull(item);

    this.environnementEntity = null;
    this.characterEntity = Optional.ofNullable(null);
    this.item = Optional.ofNullable(item);
    this.caseState = ActionType.CONTAINS_ITEM; 
 
  }
  
  /**
   * Returns a new Case instance with the specified character entity.
   *
   * @param newCharacterEntity The new character entity to be set in the case.
   * @return A new Case instance with the updated character entity.
   */
  public final Case withCharacterEntity(Optional<Character> newCharacterEntity) {
    return new Case(this.environnementEntity, newCharacterEntity.orElse(null), this.item.orElse(null));
  }
  
  /**
   * Returns a new Case instance with the specified item.
   *
   * @param newItem The new item to be set in the case.
   * @return A new Case instance with the updated item.
   */
  public final Case withItem(Optional<InsideInventory> newItem) {
    return new Case(this.environnementEntity, this.characterEntity.orElse(null), newItem.orElse(null));
  }
  
  /**
   * Returns a new Case instance with the specified environnement entity.
   *
   * @param newEnvironnementEntity The new environnement entity to be set in the case.
   * @return A new Case instance with the updated environnement entity.
   */
  public final Case withEnvironnementEntity(Environnement newEnvironnementEntity) {
    return new Case(newEnvironnementEntity, this.characterEntity.orElse(null), this.item.orElse(null));
  }

  /**
   * Checks if the provided Case instance is empty, meaning it does not contain an environment entity,
   * character entity, or an item inside the inventory.
   *
   * @param caseGrid The Case instance to be checked for emptiness.
   * @return true if the Case is empty (no environment entity, character entity, or item inside the inventory),
   *         false otherwise.
   */
  protected static final boolean isEmpty(Case caseGrid) {
    if (caseGrid.getEnvironnement() == null && caseGrid.getCharacter() == null
        && caseGrid.getInsideInventory() == null) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the case is walkable, meaning it does not contain an obstacle, character, or item.
   *
   * @return true if the case is walkable, false otherwise.
   */
  public final boolean isWalkable() {

    return !environnementEntity.isObstacle() && 
           !characterEntity.isPresent() && 
           !item.isPresent();
}

  /**
   * Gets the position of the environnement entity in the case.
   *
   * @return The position of the environnement entity.
   */
  private final Position getPosition() {
    return environnementEntity.getPosition();
  }

  
  /**
   * Gets the environnement entity in the case.
   *
   * @return The environnement entity.
   */
  public final Environnement getEnvironnement() {
    return environnementEntity;
  }

  /**
   * Gets the character entity in the case, if present.
   *
   * @return An Optional containing the character entity, or empty if not present.
   */
  public final Optional<Character> getCharacter() {
    return characterEntity;
  }
  
  /**
   * Gets the item in the case, if present.
   *
   * @return An Optional containing the item, or empty if not present.
   */
  public final Optional<InsideInventory> getInsideInventory() {
    return item;
  }
  
  /**
   * Gets the ActionType indicating the state of the case.
   *
   * @return The ActionType indicating the state of the case.
   */
  public final ActionType getCaseState() {
    return caseState;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    if (environnementEntity != null && item == null && characterEntity == null) {
      builder.append(environnementEntity.toString()).append("\n");
    }

    if (characterEntity != null && item == null) {
      if (environnementEntity != null) {
        builder.append(environnementEntity.toString()).append("\n").append(characterEntity.toString());

      } else {
        builder.append(characterEntity.toString()).append("\n");
      }

    }
    if (item != null && characterEntity == null) {
      if (environnementEntity != null) {
        builder.append(environnementEntity.toString()).append("\n").append(item.toString()).append("\n");

      } else {
        builder.append(item.toString()).append("\n");
      }

    }

    if (characterEntity != null && item != null) {
      if (environnementEntity != null) {
        builder.append(environnementEntity.toString())

            .append(characterEntity.toString())

            .append(item).append("\n");

      } else {
        builder.append(characterEntity.toString()).append("\n").append(item).append("\n");
      }

    }
    return builder.toString();

  }

  /**
   * Draws the entities in the case on the game map using the specified graphics context,
   * size dimensions, and game map information.
   *
   * @param graphics The graphics context used for drawing.
   * @param sizeX    The width of the case.
   * @param sizeY    The height of the case.
   * @param gameMap  The game map containing information about the game environment.
   */
  public final void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap) {
    environnementEntity.draw(graphics, sizeX, sizeY,gameMap);
    if (characterEntity != null) {
      characterEntity.ifPresent(character -> character.draw(graphics, sizeX, sizeY,gameMap));
      
    }
    if (item != null) {
      item.ifPresent(insideinventory -> insideinventory.drawInMap(graphics, sizeX, sizeY, getPosition(),gameMap));
    }

  }

}
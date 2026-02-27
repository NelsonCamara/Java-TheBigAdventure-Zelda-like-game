/**
 * Package com.TheBigAdventure.characterEntities contains the definition of the Ally class,
 * representing an ally character in the game.
 */
package com.TheBigAdventure.characterEntities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;
import com.TheBigAdventure.usableEntities.InsideInventory;

/**
 * Represents an ally character in the game.
 */
public final class Ally implements Character{
  private final String allyName;
  private final CharacterType allySkin;
  private final Position allyPosition;
  private final Position caseInFrontPos;
  private final Inventory allyInventory;
  
  /**
   * Constructs an Ally object with the specified name, skin, and position.
   *
   * @param allyName       The name of the ally.
   * @param allySkin       The skin type of the ally.
   * @param allyPosition   The initial position of the ally.
   */
  protected Ally(String allyName,String allySkin,Position allyPosition) {
    Objects.requireNonNull(allyName);
    Objects.requireNonNull(allySkin);
    Objects.requireNonNull(allyPosition);
    
    this.allyName = allyName;
    this.allySkin =  CharacterType.fromString(allySkin);
    this.allyPosition = allyPosition;
    this.allyInventory = new Inventory(1);
    this.caseInFrontPos = new Position(0,0);
    
  }
  
  /**
   * Returns the position of the ally on the game map.
   *
   * @return The position of the ally.
   */
  @Override
  public Position getPosition() {
    return allyPosition;
  }

  /**
   * Indicates whether the ally can move.
   *
   * @return Always returns false as allies are not allowed to move.
   */
  @Override
  public boolean canMove() {
    return false;
  }
  
  /**
   * Adds an item to the ally's inventory.
   *
   * @param item The item to be added to the inventory.
   */
  @Override
  public void addToInventory(InsideInventory item) {
    Objects.requireNonNull(item);
    
  }
  
  /**
   * Returns an optional interface for fighting if the ally can fight.
   *
   * @return An optional interface for fighting, empty in the case of an ally.
   */
  @Override
  public Optional<CanFight> asCanFight() {
    // TODO Auto-generated method stub
    return Optional.empty();
  }
  
  /**
   * Draws the ally character on the graphics context.
   *
   * @param graphics The graphics context to draw on.
   * @param sizeX    The size of the entity in the X direction.
   * @param sizeY    The size of the entity in the Y direction.
   * @param gameMap  The game map containing information about the world.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY, GameMap gameMap) {
    //Position stringPos = new Position(allyPosition.getX(),allyPosition.getY()-1);
    
    
    BufferedImage img = gameMap.getPicturesInMap().get(allySkin.toString());
    Graph.drawEntity(allyPosition, sizeX, sizeY,graphics,img);
  }
  
  /**
   * Sets the position of the ally to the specified new position.
   *
   * @param newPosition The new position to set for the ally.
   */
  @Override
  public void setPosition(Position newPosition) {
    Objects.requireNonNull(newPosition);
    allyPosition.setNewPosition(newPosition);
  }
  
  /**
   * Sets the position of the case in front of the ally to the specified position.
   *
   * @param casePosition The position of the case in front of the ally.
   */
  @Override
  public void setCaseInFrontPosition(Position casePosition) {
    Objects.requireNonNull(casePosition);
    caseInFrontPos.setNewPosition(casePosition);
    
  }
  
  /**
   * Returns the ally's inventory.
   *
   * @return The inventory of the ally.
   */
  @Override
  public Inventory characterGetInventory() {
    // TODO Auto-generated method stub
    return allyInventory;
  }
  
  /**
   * Indicates whether the character is a player.
   *
   * @return Always returns false as allies are not players.
   */
  @Override
  public boolean isPlayer() {
    return false;
  }
  
  /**
   * Indicates whether the character is an enemy.
   *
   * @return Always returns false as allies are not enemies.
   */
  @Override
  public boolean isEnemy() {
    return false;
  }
  
  /**
   * Returns the case in front of the ally on the game map.
   *
   * @param gameMap The game map containing information about the world.
   * @return The case in front of the ally.
   */
  @Override
  public Case getCaseInFront(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    return gameMap.getCaseAt(caseInFrontPos);
  }
  
  /**
   * Returns the position of the case in front of the ally on the game map.
   *
   * @return The position of the case in front of the ally.
   */
  @Override
  public Position getPosCaseInFront() {
    return caseInFrontPos;
  }
  
  /**
   * Parses an ObjectFromSkin into an Ally character.
   *
   * @param object The ObjectFromSkin representing the ally character.
   * @return An Ally character created from the specified ObjectFromSkin.
   */
  protected static Character allyFromObject(ObjectFromSkin object) {
    Objects.requireNonNull(object);
    String allyName,allySkin;
    Position allyPosition;
    Map<String, String> allyFieldsMap = object.getObjectValues();
    allyName = allyFieldsMap.get("name");
    allySkin = allyFieldsMap.get("skin");
    allyPosition = object.parsePosition(allyFieldsMap.get("position"));
    
    return new Ally(allyName, allySkin, allyPosition);
  }
  
  /**
   * Indicates whether the character is an ally.
   *
   * @return Always returns true as this is an ally character.
   */
  @Override
  public boolean isAlly() {
    return true;
  }
  
  /**
   * Heals the ally character by the specified amount.
   *
   * @param healAmount The amount by which to heal the ally.
   */
  @Override
  public void healCharacter(int healAmount) {
    
  }
  
  /**
   * Returns the name of the ally.
   *
   * @return The name of the ally.
   */
  protected final String getAllyName() {
    return allyName;
  }

  /**
   * Returns the health of the ally.
   *
   * @return The health of the ally.
   */
  @Override
  public int getHealth() {
    return 0;
  }
}

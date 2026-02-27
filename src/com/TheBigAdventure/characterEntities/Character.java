/**
 * Package com.TheBigAdventure.characterEntities contains the definition of the Character interface,
 * representing entities in the game that can be enemies, players, or allies.
 */
package com.TheBigAdventure.characterEntities;

/**
 * 
 */

import java.awt.Graphics2D;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;
import com.TheBigAdventure.usableEntities.InsideInventory;

/**
 * An interface representing entities in the game, including enemies, players, and allies.
 */
public sealed interface Character permits Enemy,Player,Ally {

  public Position getPosition();

  public boolean canMove();

  public String toString();
  
  void addToInventory(InsideInventory item);
  
  Optional<CanFight> asCanFight();
  
  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap);

  public void setPosition(Position newPosition);
  
  public void setCaseInFrontPosition(Position casePosition);
  
  public Inventory characterGetInventory();
  
  public boolean isPlayer();
  
  public boolean isEnemy();
  public boolean isAlly();
  
  public int getHealth();
  
  void healCharacter(int healAmount);
  
  public Case getCaseInFront(GameMap gameMap);
  Position getPosCaseInFront(); 

  
  /**
   * Updates the character entity in the specified case with the provided character element.
   *
   * @param caseToUpdate   The case to update.
   * @param characterElem  The optional character element to set in the case.
   * @return The updated case with the new character entity.
   */
  static Case updateCharacterInCase(Case caseToUpdate, Optional<Character> characterElem) {
    Objects.requireNonNull(caseToUpdate);
    Objects.requireNonNull(characterElem);
    return caseToUpdate.withCharacterEntity(characterElem);
}


}

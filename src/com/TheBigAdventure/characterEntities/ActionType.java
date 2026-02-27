/**
 * Package com.TheBigAdventure.characterEntities contains the definition of ActionType enum
 * and its associated ExecuteAction interface for character actions in the game.
 */

package com.TheBigAdventure.characterEntities;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.groundEntities.DecorationType;
import com.TheBigAdventure.groundEntities.DecorativeElement;
import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;
import com.TheBigAdventure.usableEntities.InsideInventory;
import com.TheBigAdventure.usableEntities.ItemType;

/**
 * Enum representing different actions that a character can perform in the game.
 */
public enum ActionType implements ExecuteAction {
	
  /**
   * Action for a character to pick up an item from the case in front.
   * The picked item is added to the character's inventory, and the case is updated.
   */
  CONTAINS_ITEM {
    @Override
    public void execute(Character character, GameMap gameMap) {
      Objects.requireNonNull(character);
      Objects.requireNonNull(gameMap);
      InsideInventory item;
      Position caseInFrontPos;
      Optional<InsideInventory> OptionalItem = character.getCaseInFront(gameMap).getInsideInventory();

      caseInFrontPos = character.getPosCaseInFront();
      item = OptionalItem.orElseThrow(() -> new NoSuchElementException("No item found"));
      character.addToInventory(item);
      Case updatedCase = gameMap.getCaseAt(caseInFrontPos).withItem(Optional.empty());
      gameMap.getGrid().put(caseInFrontPos, updatedCase);
      gameMap.getCasesToReDraw().add(updatedCase);

    }
  },
  
  /**
   * Action for a character to engage in combat with an enemy character.
   * Health points are updated for both characters, and the game map is redrawn accordingly.
   */
  CONTAINS_ENEMY {

    @Override
    public void execute(Character character, GameMap gameMap) {
      Objects.requireNonNull(character);
      Objects.requireNonNull(gameMap);
      Character frontCharacter;

      Optional<CanFight> optionalEnemyFighter;
      Optional<CanFight> optionalSelfFighter;
      CanFight selfFighter, enemyFighter;
      Position caseInFrontPos;
      Optional<Character> optionalCharacter = character.getCaseInFront(gameMap).getCharacter();
      caseInFrontPos = character.getPosCaseInFront();
      frontCharacter = optionalCharacter.orElseThrow(() -> new NoSuchElementException("No character found"));
      optionalEnemyFighter = frontCharacter.asCanFight();
      optionalSelfFighter = character.asCanFight();
      selfFighter = optionalSelfFighter.orElseThrow(() -> new NoSuchElementException("No player found"));
      enemyFighter = optionalEnemyFighter.orElseThrow(() -> new NoSuchElementException("No enemy found"));
      selfFighter.attack(enemyFighter);
      enemyFighter.attack(selfFighter);
      if (enemyFighter.getHealth() <= 0) {
        Case updatedCase = gameMap.getCaseAt(caseInFrontPos).withCharacterEntity(Optional.empty());
        gameMap.getGrid().put(caseInFrontPos, updatedCase);
        gameMap.getCasesToReDraw().add(updatedCase);
        gameMap.getCasesToReDraw().add(gameMap
            .getCaseAt(new Position(frontCharacter.getPosition().getX(), frontCharacter.getPosition().getY() - 1)));
      } else {
        gameMap.getCasesToReDraw().add(gameMap
            .getCaseAt(new Position(frontCharacter.getPosition().getX(), frontCharacter.getPosition().getY() - 1)));
        gameMap.getCasesToReDraw().add(
            gameMap.getCaseAt(new Position(frontCharacter.getPosition().getX(), frontCharacter.getPosition().getY())));
      }

      System.out.println("HP PLAYER :" + selfFighter.getHealth());
      System.out.println("HP ENEMY :" + enemyFighter.getHealth());

    }

  },
  
  /**
   * Action for a character to interact with a door using a key.
   * If the character has a key in hand, the door is opened, and the game map is updated.
   */
  CONTAINS_DOOR {

    @Override
    public void execute(Character character, GameMap gameMap) {
      Objects.requireNonNull(character);
      Objects.requireNonNull(gameMap);
      Position caseInFrontPos;
      Inventory characterInventory;
      characterInventory = character.characterGetInventory();
      caseInFrontPos = character.getPosCaseInFront();
      if (characterInventory.getItems().size() > 0) {
        if (characterInventory.getItemOnHand() != null) {
          InsideInventory item;
          item = characterInventory.getItemOnHand();
          if (item.typeOfItem().equals(ItemType.KEY)) {

            if (gameMap.getCaseAt(caseInFrontPos).getEnvironnement().typeToString().equals("DOOR")) {
              Case updatedCase = gameMap.getCaseAt(caseInFrontPos)
                  .withEnvironnementEntity(new DecorativeElement(DecorationType.VOID, caseInFrontPos));
              gameMap.getGrid().put(caseInFrontPos, updatedCase);
            }

          }
        }
      }

    }

  },
  
  /**
   * Default action when no specific action is available.
   * Handles additional actions, such as healing the character with a pizza item.
   */
  NO_ACTION_AVAILBLE {
    @Override
    public void execute(Character character, GameMap gameMap) {
      Objects.requireNonNull(character);
      Objects.requireNonNull(gameMap);

      Inventory characterInventory;
      characterInventory = character.characterGetInventory();

      if (characterInventory.getItems().size() > 0) {
        if (characterInventory.getItemOnHand() != null) {
          InsideInventory item;
          item = characterInventory.getItemOnHand();
          if (item.typeOfItem().equals(ItemType.PIZZA)) {
            character.healCharacter(item.getHealAmount());
            characterInventory.removeItemAtIndex(0);
          }
            

        }
      }
    }
  };

}

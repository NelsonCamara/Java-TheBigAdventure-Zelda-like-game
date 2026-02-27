/**
 * Package com.TheBigAdventure.characterEntities contains the definition of the CharacterType enum,
 * representing different types of characters in the game.
 */
package com.TheBigAdventure.characterEntities;

import com.TheBigAdventure.mapBuiler.ObjectFromSkin;

/**
 * Enum representing different types of characters in the game.
 */
public enum CharacterType {
	
  /**
   * Represents a monster character type.
   */
  MONSTER {
    @Override
    public Character createCharacter(ObjectFromSkin object) {

      return Enemy.enemyFromObject(object);
    }
  },
  
  /**
   * Represents a bad character type.
   */
  BADBAD{

    @Override
    public Character createCharacter(ObjectFromSkin object) {
      return Ally.allyFromObject(object);
    }
    
  };
	
  /**
   * Converts a string to a corresponding CharacterType.
   *
   * @param skin The string representation of the character type.
   * @return The CharacterType corresponding to the input string, or null if not found.
   */
  public static CharacterType fromString(String skin) {
    for (CharacterType type : CharacterType.values()) {
      if (type.name().equalsIgnoreCase(skin)) {
        return type;
      }
    }
    return null;
  }
  
  /**
   * Creates a Character of the specified type from the given ObjectFromSkin.
   *
   * @param object The ObjectFromSkin representing the character.
   * @return The created Character instance.
   */
  public abstract Character createCharacter(ObjectFromSkin object);

}

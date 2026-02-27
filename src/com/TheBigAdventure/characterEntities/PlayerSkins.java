package com.TheBigAdventure.characterEntities;

/**
 * The different skins available for player characters.
 * Each skin represents a unique player character appearance.
 * 
 */
public enum PlayerSkins {
  BABA, BADBAD, FOFO, IT;
	
  /**
   * Converts a string representation of a player skin to the corresponding enum value.
   * 
   * @param skin The string representation of the player skin.
   * @return The PlayerSkins enum value corresponding to the input string, or null if not found.
   */
  public static PlayerSkins fromString(String skin) {
    for (PlayerSkins type : PlayerSkins.values()) {
      if (type.name().equalsIgnoreCase(skin)) {
        return type;
      }
    }
    return null;
  }

}

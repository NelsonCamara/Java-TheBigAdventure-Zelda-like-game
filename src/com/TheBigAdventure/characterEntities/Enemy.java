/**
 * Package com.TheBigAdventure.characterEntities contains the definition of the Enemy class,
 * representing an enemy character in the game.
 */
package com.TheBigAdventure.characterEntities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.TheBigAdventure.graphic.Graph;
import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;
import com.TheBigAdventure.usableEntities.InsideInventory;

/**
 * Represents an enemy character in the game.
 */
public final class Enemy implements Character, CanFight {
  private final String enemyName;
  private final CharacterType enemySkin;
  private int enemyHealth;
  private final Position enemyPosition;
  private final int enemyDamage;
  private final int enemyBonusDamage;
  private final int zoneHeight;
  private final int zoneWidth;
  private final int minZoneHeight;
  private final int minZoneWidth;
  private final int maxZoneHeight;
  private final int maxZoneWidth;
  private final Position caseInFrontPos;
  

  /**
   * Initializes a new Enemy character with the specified attributes.
   *
   * @param enemyName      The name of the enemy character. Must not be null.
   * @param enemyPosition  The initial position of the enemy on the game map. Must not be null.
   * @param enemyDamage    The base damage inflicted by the enemy in combat.
   * @param enemyBonusDamage  Bonus damage that the enemy can inflict in combat.
   * @param enemyHealth    The initial health points of the enemy. Must be greater than 0.
   * @param enemySkin      The skin or appearance of the enemy character.
   * @param zoneHeight     The height of the zone in which the enemy can move.
   * @param zoneWidth      The width of the zone in which the enemy can move.
   * @throws IllegalArgumentException if enemyHealth is less than or equal to 0.
   */
  protected Enemy(String enemyName, Position enemyPosition, int enemyDamage, int enemyBonusDamage, int enemyHealth,String enemySkin,int zoneHeight,int zoneWidth) {
    Objects.requireNonNull(enemyName);
    Objects.requireNonNull(enemyPosition);
    Objects.requireNonNull(enemySkin);
    if (enemyHealth <= 0) {
      throw new IllegalArgumentException("Health needs to be superior to 0");
    }

    this.enemyName = enemyName;
    this.enemySkin = CharacterType.fromString(enemySkin);
    this.enemyPosition = enemyPosition;
    this.enemyBonusDamage = enemyBonusDamage;
    this.enemyDamage = enemyDamage;
    this.enemyHealth = enemyHealth;
    this.caseInFrontPos = new Position(0,0);
    this.zoneHeight = zoneHeight;
    this.zoneWidth = zoneWidth;
    this.maxZoneHeight = enemyPosition.getY()+zoneHeight;
    this.maxZoneWidth = enemyPosition.getX()+zoneWidth;
    this.minZoneHeight = enemyPosition.getY();
    this.minZoneWidth = enemyPosition.getX();
    
  }
  
  /**
   * Gets the total damage dealt by the enemy, including bonus damage.
   *
   * @param bonusDamage The bonus damage to be added to the base damage.
   * @return The total damage dealt by the enemy.
   */
  @Override
  public int getDamage(int bonusDamage) {
    return enemyDamage + bonusDamage;
  }
  
  /**
   * Gets the current health points of the enemy.
   *
   * @return The current health points of the enemy.
   */
  @Override
  public int getHealth() {
    return enemyHealth;
  }
  
  /**
   * Performs an attack on the specified target entity.
   *
   * @param target The target entity to be attacked.
   * @return The amount of damage dealt to the target entity.
   */
  @Override
  public int attack(CanFight cible) {
    Objects.requireNonNull(cible);
    if (canAttack(cible)) {
      
      
      
      return cible.takeDamage(getDamage(enemyBonusDamage));
    }
    return 0;


  }
  
  /**
   * Indicates whether the enemy can move.
   *
   * @return Always returns false as enemies are not allowed to move.
   */
  @Override
  public boolean canMove() {
    return false;
  }
  
  /**
   * Creates an Enemy instance from the provided ObjectFromSkin.
   *
   * @param object The ObjectFromSkin representing the enemy.
   * @return The created Enemy instance.
   */
  protected static Character enemyFromObject(ObjectFromSkin object) {
    Objects.requireNonNull(object);
    String enemyName;
    int enemyHealth;
    int enemyDamage;
    int zoneHeight;
    int zoneWidth;
    int[] monsterZoneDimensions;
    Position enemyPosition;
    Map<String, String> enemyFieldsMap = object.getObjectValues();
    enemyName = enemyFieldsMap.get("name");
    enemyHealth = Integer.parseInt(enemyFieldsMap.get("health"));
    enemyPosition = object.parsePosition(enemyFieldsMap.get("position"));
    enemyDamage = Integer.parseInt(enemyFieldsMap.get("damage"));
    monsterZoneDimensions = Enemy.extractMonsterZone(enemyFieldsMap.get("zone"));
    zoneHeight = monsterZoneDimensions[1];
    zoneWidth = monsterZoneDimensions[0];
    return new Enemy(enemyName, enemyPosition, enemyDamage, 0, enemyHealth, object.getObjectSkin(),zoneHeight,zoneWidth);
  }
  
  /**
   * Gets the position of the enemy on the game map.
   *
   * @return The position of the enemy.
   */
  @Override
  public Position getPosition() {
    return enemyPosition;
  }

  /**
   * Returns the skin type of the enemy.
   *
   * @return The skin type of the enemy.
   */
  protected final CharacterType getSkin() {
    return enemySkin;
  }
  
  /**
   * Returns a string representation of the enemy.
   *
   * @return A string representation of the enemy.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(enemyName).append("\n").append(enemySkin.toString()).append("\nHealth :").append(enemyHealth)
        .append("\nPosition :").append(enemyPosition.toString()).append("\nDefault Damage:").append(enemyDamage)
        .append("\nBonus Damage :").append(enemyBonusDamage);

    return builder.toString();
  }
  
  /**
   * Draws the enemy on the graphics context.
   *
   * @param graphics The graphics context to draw on.
   * @param sizeX    The size of the enemy in the X direction.
   * @param sizeY    The size of the enemy in the Y direction.
   * @param gameMap  The game map containing information about the world.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap){


    Position stringPos = new Position(enemyPosition.getX(),enemyPosition.getY()-1);
    BufferedImage img = gameMap.getPicturesInMap().get(enemySkin.toString());
    Graph.drawEntity(enemyPosition, sizeX, sizeY,graphics,img);
    graphics.setColor(Color.black);
    graphics.setFont(new Font("Calibri",Font.TYPE1_FONT,1));
    
    if(gameMap.getGrid().get(stringPos).isWalkable()) {
      
      graphics.drawString(String.valueOf(enemyHealth),enemyPosition.getX(),enemyPosition.getY());
      
    }

  }
  
  /**
   * Sets the position of the enemy to the specified new position.
   *
   * @param newPosition The new position to set for the enemy.
   */
  @Override
  public void setPosition(Position newPosition) {
    Objects.requireNonNull(newPosition);
    enemyPosition.setNewPosition(newPosition);
    
  }
  
  /**
   * Indicates whether the character is a player.
   *
   * @return false, as the character is not a player.
   */
  @Override
  public boolean isPlayer() {
    return false;
  }
  
  /**
   * Sets the position of the case in front of the enemy to the specified position.
   *
   * @param casePosition The position of the case in front of the enemy.
   */
  @Override
  public void setCaseInFrontPosition(Position casePosition) {
    Objects.requireNonNull(casePosition);
    caseInFrontPos.setNewPosition(casePosition);
    
  }
  
  /**
   * Returns the position of the case in front of the enemy on the game map.
   *
   * @return The position of the case in front of the enemy.
   */
  @Override
  public Position getPosCaseInFront() {
    return caseInFrontPos;
  }
  
  /**
   * Returns the case in front of the enemy on the game map.
   *
   * @param gameMap The game map containing information about the world.
   * @return The case in front of the enemy.
   */
  @Override
  public Case getCaseInFront(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    return gameMap.getCaseAt(caseInFrontPos);
  }
  
  /**
   * Adds an item to the enemy's inventory.
   *
   * @param item The item to be added to the inventory.
   */
  @Override
  public void addToInventory(InsideInventory item) {
    
  }
  
  /**
   * Returns the inventory of the enemy character.
   *
   * @return The inventory of the enemy.
   */
  @Override
  public Inventory characterGetInventory() {
    return null;
  }
  
  /**
   * Converts the enemy character to an instance of CanFight.
   *
   * @return An Optional containing this enemy as a CanFight instance.
   */
  @Override
  public Optional<CanFight> asCanFight() {
    // TODO Auto-generated method stub
    return Optional.of(this);
  }
  
  /**
   * Indicates whether the character is an enemy.
   *
   * @return true if the character is an enemy, false otherwise.
   */
  @Override
  public boolean isEnemy() {
    return true;
  }
  
  /**
   * Reduces the health of the enemy character by the specified amount.
   *
   * @param amountOfDamage The amount of damage to be taken.
   * @return The actual amount of damage taken.
   */
  @Override
  public int takeDamage(int amountOfDamage) {
    this.enemyHealth -= amountOfDamage;
    return amountOfDamage;
  }
  
  /**
   * Indicates whether the character is an ally.
   *
   * @return false as the character is not an ally.
   */
  @Override
  public boolean isAlly() {
    return false;
  }
  
  /**
   * Heals the enemy character by the specified amount.
   *
   * @param healAmount The amount by which the character is healed.
   */
  @Override
  public void healCharacter(int healAmount) {
    
  }
  
  /**
   * Extracts the dimensions of a Monster Zone from the given input string.
   *
   * @param chaine The input string containing the Monster Zone dimensions in the format "(height x width)".
   * @return An array of integers containing the height and width of the Monster Zone.
   * @throws IllegalArgumentException if the input string format is invalid.
   */
  
  
  private static int[] extractMonsterZone(String chaine) {
    String pattern = "\\((\\d+) x (\\d+)\\)";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(chaine);

    int[] result = new int[2];

    if (m.find()) {
        result[0] = Integer.parseInt(m.group(1));
        result[1] = Integer.parseInt(m.group(2));
    } else {
        throw new IllegalArgumentException("Invalid format for Monster Zone");
    }

    return result;
}

  /**
   * Returns the minimum height of the zone where the enemy can move.
   *
   * @return The minimum height of the zone.
   */
  public int getMinZoneHeight() {
      return minZoneHeight;
  }

  /**
   * Returns the minimum width of the zone where the enemy can move.
   *
   * @return The minimum width of the zone.
   */
  public int getMinZoneWidth() {
      return minZoneWidth;
  }

  /**
   * Returns the maximum height of the zone where the enemy can move.
   *
   * @return The maximum height of the zone.
   */
  public int getMaxZoneHeight() {
      return maxZoneHeight;
  }

  /**
   * Returns the maximum width of the zone where the enemy can move.
   *
   * @return The maximum width of the zone.
   */
  public int getMaxZoneWidth() {
      return maxZoneWidth;
  }

  /**
   * Returns the width of the zone where the enemy can move.
   *
   * @return The width of the zone.
   */
  public int getZoneWidth() {
      return zoneWidth;
  }

  /**
   * Returns the height of the zone where the enemy can move.
   *
   * @return The height of the zone.
   */
  public int getZoneHeight() {
      return zoneHeight;
  }
}

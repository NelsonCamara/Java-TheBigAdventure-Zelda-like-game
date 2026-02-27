package com.TheBigAdventure.characterEntities;

import com.TheBigAdventure.mapBuiler.Case;

import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.ObjectFromSkin;
import com.TheBigAdventure.mapBuiler.Position;
import com.TheBigAdventure.usableEntities.InsideInventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.TheBigAdventure.graphic.Graph;

/**
 * The Player class represents a player in the game. It manages the player's
 * attributes and behaviors, such as the skin, position, and interactions.
 */
public final class Player implements Character, CanFight {
  private final PlayerSkins skin;
  private int playerHealth;
  private final Inventory playerInventory;
  private final Position playerPosition;
  private final Position caseInFrontPos;
  private final int playerDefaultDamage;
  private int bonusDamage;
  private final String playerName;

  protected Player(PlayerSkins skin, int health, Position position, String playerName) {
    if (skin == null || health == 0 || position == null) {
      throw new IllegalArgumentException("None of the arguments can be null and health cant be <= 0");
    }
    this.skin = skin;
    this.playerHealth = health;
    this.playerInventory = new Inventory(6);
    this.playerPosition = position;
    this.caseInFrontPos = new Position(0, 0);
    this.playerDefaultDamage = 2;
    this.bonusDamage = 0;
    this.playerName = playerName;

  }
  
  /**
   * Creates a Player instance from the provided ObjectFromSkin.
   *
   * @param object The ObjectFromSkin representing the player.
   * @return The created Player instance.
   */
  public static Character playerFromFile(ObjectFromSkin object) {
    PlayerSkins playerSkin;
    int playerHealth;
    Position playerPosition;
    String playerName;

    Map<String, String> playerFieldsMap = object.getObjectValues();
    playerSkin = PlayerSkins.fromString(object.getObjectSkin());
    playerHealth = Integer.parseInt(playerFieldsMap.get("health"));
    playerName = playerFieldsMap.get("name");
    playerPosition = object.parsePosition(playerFieldsMap.get("position"));

    return new Player(playerSkin, playerHealth, playerPosition, playerName);
  }
  
  /**
   * Gets the player's skin type.
   *
   * @return The player's skin type.
   */
  protected final PlayerSkins getSkin() {
    return skin;
  }
  
  /**
   * Gets the player's inventory.
   *
   * @return The player's inventory.
   */
  protected final Inventory getPlayerInventory() {
    return playerInventory;
  }
  
  /**
   * Gets the total damage dealt by the player, including bonus damage.
   *
   * @param bonusDamage The bonus damage to be added to the base damage.
   * @return The total damage dealt by the player.
   */
  @Override
  public int getDamage(int bonusDamage) {
    return playerDefaultDamage + bonusDamage;
  }
  
  /**
   * Gets the current health points of the player.
   *
   * @return The current health points of the player.
   */
  @Override
  public int getHealth() {
    return playerHealth;
  }
  
  /**
   * Performs an attack on the specified target entity.
   *
   * @param cible The target entity to be attacked.
   * @return The amount of damage dealt to the target entity.
   */
  @Override
  public int attack(CanFight cible) {
    if (canAttack(cible)) {
      if (playerInventory.getItems().size() > 0)
        bonusDamage = playerInventory.getItemOnHand().getDamage();

      return cible.takeDamage(getDamage(bonusDamage));
    }
    return 0;

  }
  
  /**
   * Indicates whether the player can attack the specified target entity.
   *
   * @param cible The target entity.
   * @return {@code true} if the player can attack the target, {@code false} otherwise.
   */
  @Override
  public boolean canAttack(CanFight cible) {
    return playerPosition.isInContactWith(cible.getPosition());
  }
  
  /**
   * Gets the player's current position.
   *
   * @return The player's current position.
   */
  @Override
  public Position getPosition() {
    return playerPosition;
  }
  
  /**
   * Returns a string representation of the player, including player name, skin, health,
   * inventory, position, default damage, and bonus damage.
   *
   * @return A string representation of the player.
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append(getPlayerName()).append("\n").append(skin.toString()).append("\nHealth :").append(playerHealth)
        .append("\nInventaire :").append(playerInventory.toString()).append("\nPosition :")
        .append(playerPosition.toString()).append("\nDegats par Defaut:").append(playerDefaultDamage)
        .append("\nDegats :").append(bonusDamage);

    return builder.toString();
  }


  /**
   * Indicates whether the player can move.
   *
   * @return Always returns false as players are not allowed to move.
   */
  @Override
  public boolean canMove() {
    return false;
  }
  
  /**
   * Returns the name of the player.
   *
   * @return The name of the player.
   */
  protected final String getPlayerName() {
    return playerName;
  }
  
  /**
   * Draws the player on the graphics context along with their inventory items.
   *
   * @param graphics The graphics context to draw on.
   * @param sizeX    The size of the player in the X direction.
   * @param sizeY    The size of the player in the Y direction.
   * @param gameMap  The game map containing information about the world.
   */
  @Override
  public void draw(Graphics2D graphics, int sizeX, int sizeY, GameMap gameMap) {

    Position stringPos = new Position(playerPosition.getX(), playerPosition.getY() - 1);
    BufferedImage img = gameMap.getPicturesInMap().get(skin.toString());
    Graph.drawEntity(playerPosition, sizeX, sizeY, graphics, img);

    if (playerInventory.getItems().size() > 0) {
      if (playerInventory.getItemOnHand() != null) {
          Position itemPos = new Position(playerPosition.getX()-1, playerPosition.getY());
          playerInventory.getItemOnHand().drawInMap(graphics, 1,1,itemPos, gameMap);
      }
        

    }

    graphics.setColor(Color.black);
    graphics.setFont(new Font("Calibri", Font.TYPE1_FONT, 1));
    if (gameMap.getGrid().get(stringPos).isWalkable()) {
      graphics.drawString(String.valueOf(playerHealth), playerPosition.getX(), playerPosition.getY());
     
    }

  }
  
  /**
   * Sets the position of the player to the specified new position.
   *
   * @param newPosition The new position to set for the player.
   */
  @Override
  public void setPosition(Position newPosition) {
    playerPosition.setNewPosition(newPosition);

  }
  
  /**
   * Checks if the entity is a player.
   *
   * @return {@code true} if the entity is a player, {@code false} otherwise.
   */
  @Override
  public boolean isPlayer() {
    return true;
  }

  /**
   * Gets the position of the case in front of the player on the game map.
   *
   * @return The position of the case in front of the player.
   */
  @Override
  public Position getPosCaseInFront() {
    return caseInFrontPos;
  }
  
  /**
   * Sets the position of the case in front of the player to the specified position.
   *
   * @param casePosition The position of the case in front of the player.
   */
  @Override
  public void setCaseInFrontPosition(Position casePosition) {
    caseInFrontPos.setNewPosition(casePosition);

  }
  
  /**
   * Returns the case in front of the player on the game map.
   *
   * @param gameMap The game map containing information about the world.
   * @return The case in front of the player.
   */
  @Override
  public Case getCaseInFront(GameMap gameMap) {
    Objects.requireNonNull(gameMap);
    return gameMap.getCaseAt(caseInFrontPos);
  }
  
  /**
   * Adds an item to the player's inventory.
   *
   * @param item The item to be added to the inventory.
   */
  @Override
  public void addToInventory(InsideInventory item) {
    playerInventory.getItems().add(item);

  }
  
  /**
   * Gets the inventory of the player.
   *
   * @return The player's inventory.
   */
  @Override
  public Inventory characterGetInventory() {
    return playerInventory;
  }
  
  /**
   * Converts the player to an instance of CanFight.
   *
   * @return An Optional containing the CanFight instance of the player.
   */
  @Override
  public Optional<CanFight> asCanFight() {
    return Optional.of(this);
  }
  
  /**
   * Checks if the entity is an enemy.
   *
   * @return {@code true} if the entity is an enemy, {@code false} otherwise.
   */
  @Override
  public boolean isEnemy() {
    return false;
  }
  
  /**
   * Reduces the player's health by the specified amount.
   *
   * @param amountOfDamage The amount of damage to be applied.
   * @return The actual amount of damage applied to the player.
   */
  @Override
  public int takeDamage(int amountOfDamage) {
    this.playerHealth -= amountOfDamage;
    return amountOfDamage;
  }
  
  /**
   * Checks if the entity is an ally.
   *
   * @return {@code true} if the entity is an ally, {@code false} otherwise.
   */
  @Override
  public boolean isAlly() {
    return false;
  }
  
  /**
   * Increases the player's health by the specified amount.
   *
   * @param healAmount The amount of healing to be applied.
   */
  @Override
  public void healCharacter(int healAmount) {
    playerHealth += healAmount;
    
  }

}

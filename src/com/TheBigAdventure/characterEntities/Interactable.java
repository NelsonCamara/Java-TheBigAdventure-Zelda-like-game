package com.TheBigAdventure.characterEntities;

/**
 * The Interactable interface defines the contract for entities that can be interacted with in the game.
 * Implementing classes must provide a method to handle interactions with a player.
 */
public interface Interactable {
  void interact(Player player);
}

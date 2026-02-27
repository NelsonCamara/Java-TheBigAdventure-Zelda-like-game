/**
 * Package com.TheBigAdventure.characterEntities contains the definition of the CanFight interface,
 * representing entities that can engage in combat and deal damage to other entities.
 */
package com.TheBigAdventure.characterEntities;

import com.TheBigAdventure.mapBuiler.Position;

/**
 * An interface for entities capable of fighting and dealing damage to other entities in the game.
 *
 * This interface is sealed and permits the implementation by the Enemy and Player classes.
 */
public sealed interface CanFight permits Enemy,Player {
	
  int getDamage(int bonusDamage);
  
  int getHealth();
  
  Position getPosition();
  
  int attack(CanFight cible);

  default boolean canAttack(CanFight cible) {
    return getPosition().isInContactWith(cible.getPosition());
  }

  int takeDamage(int amountOfDamage);
}

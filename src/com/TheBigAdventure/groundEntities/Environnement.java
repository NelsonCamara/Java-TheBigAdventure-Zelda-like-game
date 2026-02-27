package com.TheBigAdventure.groundEntities;

import java.awt.Graphics2D;
import java.util.Objects;

import com.TheBigAdventure.mapBuiler.Case;
import com.TheBigAdventure.mapBuiler.GameMap;
import com.TheBigAdventure.mapBuiler.Position;

/**
 * The Environnement interface represents entities that can be part of the game environment,
 * including both obstacles and decorations. It defines methods to retrieve the position,
 * check if it is an obstacle or a decoration, convert the type to a string representation,
 * and draw the entity on the screen.
 * <p>
 * This interface also includes a static method for updating an environment entity in a specific game map case.
 */
public interface Environnement {
	
  public Position getPosition();

  public boolean isObstacle();

  public boolean isDecoration();

  public String typeToString();

  public void draw(Graphics2D graphics, int sizeX, int sizeY,GameMap gameMap);
  
  /**
   * Updates the environment entity in a specific game map case.
   *
   * @param caseToUpdate       The Case to be updated.
   * @param environnementElem  The new environment entity to be set in the case.
   * @return                    The new instance of Case with the updated environment entity.
   * @throws NullPointerException If either caseToUpdate or environnementElem is null.
   */
  static  Case updateEnvironnementInCase(Case caseToUpdate, Environnement environnementElem) {
    Objects.requireNonNull(caseToUpdate);
    Objects.requireNonNull(environnementElem);
    return caseToUpdate.withEnvironnementEntity(environnementElem); // Retournez la nouvelle instance de Case
}

}

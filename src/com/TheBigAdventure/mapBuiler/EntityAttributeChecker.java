package com.TheBigAdventure.mapBuiler;
import java.util.Set;
import java.util.HashSet;


/**
 * Utility class for checking if a given attribute is a valid entity attribute.
 * Provides a predefined set of valid entity attributes used for validation.
 */
public class EntityAttributeChecker {
  private static final Set<String> ENTITY_ATTRIBUTES = new HashSet<>();

  static {
      ENTITY_ATTRIBUTES.add("name");
      ENTITY_ATTRIBUTES.add("skin");
      ENTITY_ATTRIBUTES.add("player");
      ENTITY_ATTRIBUTES.add("position");
      ENTITY_ATTRIBUTES.add("health");
      ENTITY_ATTRIBUTES.add("kind");
      ENTITY_ATTRIBUTES.add("zone");
      ENTITY_ATTRIBUTES.add("behavior");
      ENTITY_ATTRIBUTES.add("damage");
  }
  
  /**
   * Checks if the provided attribute is a valid entity attribute.
   *
   * @param attribute The attribute to be checked.
   * @return true if the attribute is a valid entity attribute, false otherwise.
   */
  public static boolean isEntityAttribute(String attribute) {
      return ENTITY_ATTRIBUTES.contains(attribute);
  }



}

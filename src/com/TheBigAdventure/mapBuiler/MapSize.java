package com.TheBigAdventure.mapBuiler;

/**
 * Record representing the size of a map with height and width dimensions.
 */
public record MapSize(int height, int width) {
	
    /**
     * Parses a string representation of map size and returns a corresponding MapSize object.
     *
     * @param sizeOfMap The string representation of map size (e.g., "{height}x{width}").
     * @return A MapSize object with parsed height and width dimensions.
     */
  public static MapSize parseSize(String sizeOfMap) {
    String numericPart = sizeOfMap.split(":")[1].trim();

    String[] parts = numericPart.substring(1, numericPart.length() - 1).split("x");

    int width = Integer.parseInt(parts[0].trim());
    int height = Integer.parseInt(parts[1].trim());

    return new MapSize(height, width);
  }
}

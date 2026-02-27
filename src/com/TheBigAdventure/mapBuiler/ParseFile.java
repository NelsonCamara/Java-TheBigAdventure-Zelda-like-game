package com.TheBigAdventure.mapBuiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for parsing map files.
 */
public class ParseFile {
  
    /**
     * Validates the map by checking sections like size, encodings, and data.
     *
     * @param encodingsMap     The map containing encodings.
     * @param listOfElements   List of map elements.
     * @param mapSize          Size of the map.
     * @param dataSection      Data section of the map.
     */
  protected static void validateMap(Map<String, String> encodingsMap, List<Map<String, String>> listOfElements,String mapSize, String dataSection) {
    MapValidate.validateSectionNotEmpty(mapSize, encodingsMap, dataSection);
    MapValidate.validateSize(mapSize);
    MapValidate.validateEncoding(encodingsMap);
    MapValidate.validatedata(dataSection, encodingsMap, mapSize);
    MapValidate.getDataMapElements(listOfElements, dataSection, encodingsMap);
  }
  
  /**
   * Reads the content of a file and returns it as a string.
   *
   * @param pathFichier The path to the file.
   * @return The content of the file as a string.
   * @throws IOException If an I/O error occurs.
   */
  protected static String readFile(String pathFichier) throws IOException {
    StringBuilder data = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(pathFichier))) {
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line).append("\n");
        }
    }

    return data.toString();
  }
  
  /**
   * Updates element properties based on the content.
   *
   * @param lexer            The lexer for parsing content.
   * @param elementsPropreties Map containing element properties.
   * @param content           The content to be processed.
   */
  protected static void elementProprietiesUpdate(Lexer lexer, Map<String, String> elementsPropreties, String content) {
    if (EntityAttributeChecker.isEntityAttribute(content)) {
      String[] valueTab = lexer.nextThreeResultsContent();
      String key = content;
      String value = valueTab[1];
      elementsPropreties.put(key, value);
    }
  }

  /**
   * Extracts information from lexer results and adds it to the list of elements.
   *
   * @param elementsPropreties Map containing element properties.
   * @param listOfElements   List of map elements.
   */
  protected static void extracted(Map<String, String> elementsPropreties, List<Map<String, String>> listOfElements) {
    elementsPropreties.put("entityOrElement", "entity");
    listOfElements.add(new HashMap<>(elementsPropreties));
    elementsPropreties.clear();
  }

  /**
   * Checks if the content indicates the start of the encodings section.
   *
   * @param content The content to be checked.
   * @return True if the content indicates the start of the encodings section, otherwise false.
   */
  public static boolean isEncodingsSectionStarted(String content) {
    Objects.requireNonNull(content);

    if (content.equals("encodings")) {
      // System.out.println(content);

      return true;
    }
    return false;

  }

  /**
   * Checks if the content indicates the start of the element section.
   *
   * @param content The content to be checked.
   * @return True if the content indicates the start of the element section, otherwise false.
   */
  public static boolean isElementSectionStarted(String content) {
    Objects.requireNonNull(content);
    if (content.equals("[element]")) {
      return true;
    }
    return false;
  }

  /**
   * Fills the map size based on lexer results.
   *
   * @param result   The lexer result.
   * @param mapSize  The map size to be filled.
   * @param sizeAdded The number of size dimensions already added.
   * @return The updated sizeAdded value.
   */
  public static int fillSizeMap(Result result, Map<String, Integer> mapSize, int sizeAdded) {

    if (sizeAdded < 2) {
      if (result.token().equals(Token.NUMBER)) {
        if (sizeAdded == 0) {
          mapSize.put("width", Integer.parseInt(result.content().trim()));
          return 1;
        } else {
          mapSize.put("height", Integer.parseInt(result.content().trim()));
          return 2;
        }

      }
    }
    return 0;
  }
  
  /**
   * Checks if the content indicates the start of the size section.
   *
   * @param content The content to be checked.
   * @return True if the content indicates the start of the size section, otherwise false.
   */
  public static boolean isSizeSectionStarted(String content) {
    if (content.equals("size")) {
      return true;
    }
    return false;
  }
  
  /**
   * Checks if the size section is initialized.
   *
   * @param result     The lexer result.
   * @param sizeAdded  The number of size dimensions already added.
   * @param mapSize    The map size to be filled.
   * @return True if the size section is still being processed, otherwise false.
   */
  public static boolean sizeMapInit(Result result, int sizeAdded, Map<String, Integer> mapSize) {
    Objects.requireNonNull(result);
    Objects.requireNonNull(mapSize);

    if (fillSizeMap(result, mapSize, sizeAdded) == 1) {
      return false;
    } else {
      return true;
    }

  }

  /**
   * Initializes the encodings map based on lexer results.
   *
   * @param encodingsList          The list containing encodings.
   * @param result                 The lexer result.
   * @param encodingsMap           The encodings map to be filled.
   * @param content                The content to be processed.
   * @param insideEncodingsSection True if currently inside the encodings section, otherwise false.
   * @return The updated encodings map.
   */
  public static Map<String, String> encodingsMapInit(List<String> encodingsList, Result result,
      Map<String, String> encodingsMap, String content, boolean insideEncodingsSection) {
    if (insideEncodingsSection) {

      if (isEncodingsSectionEnded(content, insideEncodingsSection)) {

        encodingsMap = fillEncodingsMap(encodingsList);
        insideEncodingsSection = false;

        return encodingsMap;
      } else {

        fillEncodingsList(content, encodingsList, result);
        return null;
      }
    }
    return null;

  }
  
  /**
   * Checks if the content indicates the end of the encodings section.
   *
   * @param content              The content to be checked.
   * @param inEncodingsSection   True if currently inside the encodings section, otherwise false.
   * @return True if the content indicates the end of the encodings section, otherwise false.
   */
  public static boolean isEncodingsSectionEnded(String content, boolean inEncodingsSection) {

    if (inEncodingsSection
        && (content.equals("[") || content.equals("size") || content.equals("data") || content.equals("[element]"))) {

      return true;
    }
    return false;
  }
  
  /**
   * Fills the encodings list based on lexer results.
   *
   * @param content        The content to be processed.
   * @param encodingsList  The list containing encodings.
   * @param lexerResult    The lexer result.
   */
  public static void fillEncodingsList(String content, List<String> encodingsList, Result lexerResult) {

    if (!content.equals(":") && !content.equals("encodings")) {
      String contentOfEncoding = lexerResult.content().trim();
      if (!contentOfEncoding.equals("(") && !contentOfEncoding.equals(")")) {
        // System.out.println(content);

        encodingsList.add(content);
      }
    }
  }

  /**
   * Fills the encodings map based on the encodings list.
   *
   * @param encodingsList The list containing encodings.
   * @return The encodings map.
   */
  public static Map<String, String> fillEncodingsMap(List<String> encodingsList) {
    Map<String, String> encodings = new HashMap<>();
    for (int i = 0; i < encodingsList.size(); i++) {
      if (i + 1 < encodingsList.size() && i % 2 == 0) {

        encodings.put(encodingsList.get(i), encodingsList.get(i + 1));
      }

    }

    return encodings;
  }
  
  /**
   * Retrieves the encodings from the lexer.
   *
   * @param lexer The lexer to be used.
   * @return The encodings map.
   */
  public static Map<String, String> getEncodings(Lexer lexer) {

    Map<String, String> encodings = new HashMap<>();
    List<String> encodingsList = new ArrayList<>();
    boolean inEncodingsSection = false;

    Result lexerResult;
    while ((lexerResult = lexer.nextResult()) != null) {
      String content = lexerResult.content().trim();
      if (content.equals("encodings")) {

        inEncodingsSection = true;
        continue;
      }

      if (inEncodingsSection && (content.equals("[") || content.equals("size") || content.equals("data"))) {
        break;
      }

      if (inEncodingsSection && !content.equals(":") && !content.equals("encodings")) {

        if (lexerResult.token() != Token.LEFT_PARENS && lexerResult.token() != Token.RIGHT_PARENS) {

          encodingsList.add(content);
        }
      }
    }
    for (int i = 0; i < encodingsList.size(); i++) {
      if (i + 1 < encodingsList.size() && i % 2 == 0) {

        encodings.put(encodingsList.get(i), encodingsList.get(i + 1));
      }

    }

    return encodings;
  }
  
  /**
   * Extracts size dimensions from the given dimension string.
   *
   * @param dimension The dimension string.
   * @return An array containing the width and height dimensions.
   */
  protected static int[] extractSize(String dimension) {
    dimension = dimension.replace("(", "").replace(")", "");

    String[] parts = dimension.split(" x ");

    int[] dimensions = new int[2];

    dimensions[0] = Integer.parseInt(parts[0].trim()); 
    dimensions[1] = Integer.parseInt(parts[1].trim());

    return dimensions;
  }
  
  /**
   * Executes the lexer on the specified file path and returns the result as a MapContainer.
   *
   * @param filePath The path to the file.
   * @return A MapContainer containing map dimensions and elements.
   * @throws IOException If an I/O error occurs.
   */
  protected static MapContainer execLexer(String filePath) throws IOException {
    Objects.requireNonNull(filePath);
    System.out.println("Current working directory: " + System.getProperty("user.dir"));
    
    var text = ParseFile.readFile(filePath);
    var lexer = new Lexer(text);
    List<String> encodingsList = new ArrayList<>();
    Map<String, String> encodingsMap = new HashMap<>();
    Map<String, String> elementsPropreties = new HashMap<>();
    List<Map<String, String>> listOfElements = new ArrayList<>();
    Result result;
    boolean sizeSectionStarted = false;
    boolean sizeSectionEnded = false;
    boolean elementSectionStarted = false;
    String mapSize = null;
    String dataSection = null;
    boolean insideEncodingsSection = false;
    boolean isProcessing = true;
    int [] mapDimensions= null;
    while (isProcessing) {
      result = lexer.nextResult();

      if (result != null) {
          String content = result.content().trim();
          
          
         if(result.token().equals(Token.DATA_BLOCK))  dataSection = content;
         

          if (ParseFile.isElementSectionStarted(content)) {
              if (elementSectionStarted) ParseFile.extracted(elementsPropreties, listOfElements);
              else elementSectionStarted = true;
          }

          if (elementSectionStarted && !content.equals("[element]")) ParseFile.elementProprietiesUpdate(lexer, elementsPropreties, content);
          

          if (ParseFile.isEncodingsSectionStarted(content)) insideEncodingsSection = true;
          
          if (insideEncodingsSection) {
              encodingsMap = ParseFile.encodingsMapInit(encodingsList, result, encodingsMap, content, insideEncodingsSection);
              if (encodingsMap != null) insideEncodingsSection = false;
          }

          if (ParseFile.isSizeSectionStarted(content) && !sizeSectionEnded) {
              sizeSectionStarted = true;
              continue;
          }

          if (sizeSectionStarted && !sizeSectionEnded && result.token().equals(Token.SIZE)) {
              mapSize = content;
              sizeSectionEnded = true;
          }

      } else {
          if (elementSectionStarted) {
              elementsPropreties.put("entityOrElement", "entity");
              listOfElements.add(new HashMap<>(elementsPropreties));
          }
          isProcessing = false;
      }
    } 
   
    
    ParseFile.validateMap(encodingsMap, listOfElements, mapSize, dataSection);
    mapDimensions = extractSize(mapSize);
   
    return new MapContainer(mapDimensions, listOfElements);
  }

}

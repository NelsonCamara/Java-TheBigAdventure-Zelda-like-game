package com.TheBigAdventure.mapBuiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MapValidate {
	
	/**
	 * Validates that the section components are not empty.
	 * 
	 * @param sizeContent The size section.
	 * @param encodingContent The encoding section.
	 * @param dataContent The data section.
	 * 
	 * @throws IllegalArgumentException If the size content, encoding content, or data content is empty.
	 *     The exception message specifies which section is empty.
	 */
	public static void validateSectionNotEmpty(String sizeContent, Map<String, String> encodingContent, String dataContent) {
		if(sizeContent == null) {
			throw new IllegalArgumentException("Size section is not defined");
		}
		if(encodingContent.isEmpty()) {
			throw new IllegalArgumentException("Encodings  section is not defined");
		}
		if(dataContent == null) {
			throw new IllegalArgumentException("Data section is not defined");
		}
	}
	
	/**
	 * Parses a string representing dimensions in the format "(width x height)" and returns an array of strings containing the width and height.
	 *
	 * @param sizeContent A non-null string representing dimensions in the format "(width x height)".
	 * @return An array of strings where index 0 contains the width and index 1 contains the height. The strings are trimmed to remove leading and trailing spaces.
	 * @throws NullPointerException If the input sizeContent is null.
	 * 
	 * Example:
	 * If sizeContent is "(65 x 47)", the returned array will be {"65", "47"}.
	 */
	public static String[] getDimenssion(String sizeContent) {
    	Objects.requireNonNull(sizeContent);
		String[] sizeParts = sizeContent.replace("(", "").replace(")", "").split("x"); // => we then have: sizeParts[0] = "65 " and size[1] = "47 "
		sizeParts[0] = sizeParts[0].trim(); //we have : sizeParts[0] = "65"
		sizeParts[1] = sizeParts[1].trim(); //we have : sizeParts[1] = "47"
		return sizeParts;
	}
	
    /**
     * Validates the size of the map.
     *
     * @param sizeContent contains of the size section.
     */
    public static void validateSize(String sizeContent){
        Objects.requireNonNull(sizeContent);
        
        //conversion of map dimensions // Suppose the size is in the format "size: (65x47)" and sizeContent = (65x47)
        String[] sizeParts = getDimenssion(sizeContent); 

        if(validateFormat(sizeParts) != 0) { //check if the format is valid
        	throw new IllegalArgumentException("Size section format error");
        }
    }
    
    /**
     * Validates the format of the map dimensions extracted from a string.
     *
     * @param sizeParts Array containing the value extracted dimensions.
     */
    private static int validateFormat(String[] sizeParts) {
    	Objects.requireNonNull(sizeParts);
    	int erreur = 0;
    	if (sizeParts.length != 2) { //initial input format is wrong
    		System.out.println("Map dimensions Format is wrong the correct format is for example size :(10,10)");
    		erreur++;
        }if(!isInteger(sizeParts[0])) { //check if width is integer
    		System.out.println("The width needs to be an integer");
    		erreur++;
    	}if(!isInteger(sizeParts[1])) { //check if height is integer
    		System.out.println("The height needs to be an integer");
    		erreur++;
    	}
    	return erreur; //if there is no error then we return 0
    }
    
    /**
     * check if the string represents an integer.
     *
     * @param str a string than can be checked.
     * @return boolean value, true if str represents an integer and false otherwise.
     */
    private static boolean isInteger(String str) {
    	Objects.requireNonNull(str);
    	try {
    		Integer.parseInt(str); //converted a string to an integer
    		return true;
    	} catch(NumberFormatException e) {
    		return false;
    	}
    }
    
    /**
     * Validates the Encoding of the map.
     *
     * @param encodingContent Map containing the pair cle-valeur.
     */
	public static void validateEncoding(Map<String, String> encodingContent){
		Objects.requireNonNull(encodingContent);
		int erreur = 0;
		//we check the validity conditions :
		erreur += validateUniqueKey(encodingContent);
		erreur += validateUniqueValue(encodingContent);
		erreur += validateEmptyValue(encodingContent);
		erreur += validateIntegerKey(encodingContent);
		erreur += validateLetterKey(encodingContent);
		if(erreur != 0) {
			throw new IllegalArgumentException("Encoding section error");
		}
		
	}
	
	/**
	 * Validates that the keys in the given encoding section are unique.
	 *
	 * This method checks whether there are any duplicate keys in the provided encoding section.
	 * If a duplicate key is found, an IllegalArgumentException is thrown with a message
	 * indicating the presence of the duplicated element.
	 *
	 * @param encodingMap The encoding section to validate for unique keys.
	 * @throws IllegalArgumentException If duplicate keys are found in the encodingMap.
	 */
	private static int validateUniqueKey(Map<String, String > encodingMap) {
		Objects.requireNonNull(encodingMap);
		int erreur = 0;
		Set<String> keys = new HashSet<>(); //collection which will store the encodingMap keys
		for(var elem : encodingMap.keySet()) { //we browse the keys of encodingMap
			if(keys.contains(elem)) { //if the current key had already been added to keys then there is a duplicate
				System.out.println("Not unique key: " + elem);
				erreur++;
			}
			keys.add(elem); //otherwise we add it
		}
		return erreur;
	}
	
	/**
	 * Validates that the name of element in the given encoding section are unique.
	 *
	 * This method checks whether there are any duplicate name of element in the provided encoding section.
	 * If a duplicate name of element is found, an IllegalArgumentException is thrown with a message
	 * indicating the presence of the duplicated element.
	 *
	 * @param encodingMap The encoding section to validate for unique name of element.
	 * @throws IllegalArgumentException If duplicate name of element are found in the encodingMap.
	 */
	private static int validateUniqueValue(Map<String, String > encodingMap) {
		Objects.requireNonNull(encodingMap);
		int erreur = 0;
		Set<String> value = new HashSet<>(); //collection which will store the values ​​of encodingMap
		for(var elem : encodingMap.values()) { //we browse the values ​​of encodingMap
			if(value.contains(elem)) { //if the current value had already been added to value then there is a duplicate
				System.out.println("Not unique value : " + elem);
				erreur++;
			}
			value.add(elem); //otherwise we add it
		}
		return erreur;
	}
	
	/**
	 * Validates that none of the values in the given encoding section are empty.
	 *
	 * This method checks each entry in the provided encoding section and throws an
	 * IllegalArgumentException if it finds any empty values.
	 *
	 * @param encodingMap The encoding section to validate for non-empty values.
	 * @throws IllegalArgumentException If any value in the encoding section is empty.
	 */
	private static int validateEmptyValue(Map<String, String> encodingMap) {
		Objects.requireNonNull(encodingMap);
		int erreur = 0;
		for(var elem : encodingMap.entrySet()) { //we browse the key-value pairs
			if(elem.getValue().isEmpty()) { //element not specified => " "
				System.out.println("Value isnt  specified" + elem.getKey());
				erreur++;
			}
			
		}
		return erreur;
	}
	
	/**
	 * Validates that all keys in the given encoding section can not be integers.
	 *
	 * This method checks each entry in the provided encoding section, attempting to parse
	 * the value as an integer. If parsing succeeds, it indicates that the value is not
	 * a character.
	 *
	 * @param encodingMap The encoding section to validate for integer keys.
	 */
	private static int validateIntegerKey(Map<String, String> encodingMap) {
		Objects.requireNonNull(encodingMap);
		int erreur = 0;
		for(var elem : encodingMap.entrySet()) { //we browse the key-value pairs
			try {
				Integer.parseInt(elem.getValue()); //convert to integer
				System.out.println("Values in encodings section are only letters " + elem.getKey()); //if the conversion succeeds then error
				erreur++;
			} catch(NumberFormatException ignored) {
				//nothing to do because conversion was not successful
			}
		}
		return erreur;
	}
	
	/**
	 * Validates that each value in the encoding map is a single letter.
	 * 
	 * @param encodingMap The map containing key-value pairs for encoding.
	 * 
	 * @throws IllegalArgumentException If any value in the encoding map is not a single letter.
	 *     An error message is printed to the console for each invalid encoding.
	 */
	private static int validateLetterKey(Map<String, String> encodingMap) {
		Objects.requireNonNull(encodingMap);
		int erreur = 0;
		for(var elem : encodingMap.values()) { //browse the values ​​of the encoding section
			if(elem.length() != 1) { //if the size of the value is different from 1 then it is not a character
				System.out.println("error, encoding " + elem + " keys can be only letters not words");
				erreur++;
			}
		}
		return erreur;
	}
	
	/**
	 * Validates the data section with respect to size content, encoding content, and overall structure.
	 * 
	 * @param dataContent The map content to be validated.
	 * @param encodingContent The encoding content to be used for validation.
	 * @param sizeContent The size content to be used for validation.
	 * 
	 * @throws NullPointerException If any of the input parameters (dataContent, encodingContent, sizeContent) is null.
	 * @throws IllegalArgumentException If the dimensions of the data do not match the specified size content,
	 *     if the first line does not conform to the specified encoding content,
	 *     if the body data does not conform to the specified encoding content,
	 *     or if the last line does not conform to the specified encoding content.
	 */
	public static void validatedata(String dataContent, Map<String, String> encodingContent, String sizeContent) {
    	Objects.requireNonNull(dataContent);
    	Objects.requireNonNull(encodingContent);
    	Objects.requireNonNull(sizeContent);
    	int erreur = 0;
    	String[] line = convertChaine(dataContent); //convert string to String[]
    	
    	erreur += validateDimensionData(line, sizeContent); //check the length of the lines and the number of lines
    	erreur += validateFirstLine(line, encodingContent); //check the first line
    	erreur += validateBodyData(line, encodingContent); //check the body of the map
		erreur += validateLastLine(line, encodingContent); //check the last line
		
		if(erreur != 0) {
			throw new IllegalArgumentException("Data section error");
		}
    }
	
	/**
	 * Converts a string representation of data section into an array of trimmed lines.
	 * 
	 * @param dataContent The string representation of data section to be converted.
	 * @return An array of trimmed lines extracted from the input string.
	 * 
	 * @throws NullPointerException If the input parameter (dataContent) is null.
	 */
	public static String[] convertChaine(String dataContent) {
		Objects.requireNonNull(dataContent);
		String[] lines = dataContent.replace("\"", "").trim().split("\n"); //remove quotes at the start and end of the data section and convert String to String[]
		for(int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].trim(); //remove spaces at the star and end for each lines
		}
		return lines;
	}
	
	/**
	 * Validates the dimensions of the data section based on the specified size content.
	 * 
	 * @param line The array of lines representing the data section.
	 * @param sizeContent The size content specifying the expected dimensions in the format "width x height".
	 * 
	 * @throws IllegalArgumentException If the actual number of lines does not match the specified height,
	 *     or if the number of characters in any line does not match the specified width.
	 * @throws NumberFormatException If there is an error parsing the width or height from the size content.
	 */
	private static int validateDimensionData(String[] line, String sizeContent) {
		Objects.requireNonNull(line);
		Objects.requireNonNull(sizeContent);
		String[] sizeParts = getDimenssion(sizeContent); //get dimension values 
		int erreur = 0;
		erreur += validateCountLine(line, Integer.parseInt(sizeParts[1])); //check the number of lines (represents the height)
		for(int i = 0; i < line.length; i++) {
			erreur += validateCountCol(line[i], i + 1, Integer.parseInt(sizeParts[0]));//check line by line the number of character (represents the width)
		}
		return erreur;
	}
	
	/**
	 * Validates the number of lines in the data section.
	 * 
	 * @param line The array of lines representing the data section.
	 * @param ExpectedHeight The expected number of lines (height).
	 * 
	 * @throws IllegalArgumentException If the actual number of lines is less than the expected height, 
	 *     an error message is printed indicating that the map height is too small.
	 *     If the actual number of lines is greater than the expected height, 
	 *     an error message is printed indicating that the map height is too large.
	 */
	private static int validateCountLine(String[] line, int ExpectedHeight) {
		Objects.requireNonNull(line);
		Objects.requireNonNull(ExpectedHeight);
		int erreur = 0;
		int heigth = line.length;
		if(heigth != ExpectedHeight) {
			erreur++;
			if(heigth < ExpectedHeight) {
				System.out.println("ERROR Map Height in data section dont correspond to Height in Size section");
			}else {
				System.out.println("ERROR Map Width in data section dont correspond to WIDTH in Size section");
			}
		}
		return erreur;
	}
	
	/**
	 * Validates the number of characters in a specific line of the data section.
	 * 
	 * @param ligne The string representing a line in the data section.
	 * @param indice The index of the line being validated.
	 * @param ExpectedWidth The expected number of characters in the line (width).
	 * 
	 * @throws IllegalArgumentException If the actual number of characters in the line is less than the expected width, 
	 *     an error message is printed indicating that the line is too short (column count error).
	 *     If the actual number of characters in the line is greater than the expected width, 
	 *     an error message is printed indicating that the line is too long (column count error).
	 */
	private static int validateCountCol(String ligne, int indice, int ExpectedWidth) {
		Objects.requireNonNull(ligne);
		Objects.requireNonNull(indice);
		Objects.requireNonNull(ExpectedWidth);
		int size = ligne.length();
		int erreur = 0;
		if(size != ExpectedWidth) {
			erreur++;
			if(size < ExpectedWidth) {
				System.out.println("ERROR line " + indice + " too short,the number of colums must be equal to the Width in Size section");
			}
			else {
			  System.out.println("ERROR line " + indice + " too long,the number of colums must be equal to the Width in Size section");
			}
		}
		return erreur;
	}
	
	/**
	 * Validates the first line of data section based on the specified encoding content.
	 * 
	 * @param line The array of lines representing the data section.
	 * @param encodingContent The encoding content to be used for validation.
	 * 
	 * @throws IllegalArgumentException If any character in the first line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position of the unrecognized character.
	 */
	private static int validateFirstLine(String[] line, Map<String, String> encodingContent){
		Objects.requireNonNull(line);
		Objects.requireNonNull(encodingContent);
		int sizeLine = line[0].length();
		int erreur = 0;
		for(int i = 0; i < sizeLine; i++) { //browse character by character of the first line
			String elem = line[0].charAt(i) + ""; //covert char to String
			if(!encodingContent.containsValue(elem)) { //check if the current character belongs to the encodings section
				System.out.println("error line 1, column " + (i+1)+" code "+elem+" not recognized based on the encodings");
				erreur++;
			}
		}
		return erreur;
	}
	
	/**
	 * Validates the last line of data section based on the specified encoding content.
	 * 
	 * @param line The array of lines representing the data content.
	 * @param encodingContent The encoding content to be used for validation.
	 * 
	 * @throws IllegalArgumentException If any character in the last line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position of the unrecognized character.
	 */
	private static int validateLastLine(String[] line, Map<String, String> encodingContent){
		Objects.requireNonNull(line);
		Objects.requireNonNull(encodingContent);
		int erreur = 0;
		int indiceLine = line.length;
		int sizeLine = line[indiceLine - 1].length(); //size of the last line
		for(int i = 0; i < sizeLine; i++) { //browse character by character the last line
			String elem = line[indiceLine - 1].charAt(i) + ""; //String to String[]
			if(!encodingContent.containsValue(elem)) { //check if the current character belongs to the encodings section
				System.out.println("error line " + indiceLine + ", column " + (i+1)+" code "+elem+"not recognized based on the encodings");
				erreur++;
			}
		}
		return erreur;
	}
	
	/**
	 * Validates the body of the data section excluding the first and last lines, based on the specified encoding content.
	 * 
	 * @param line The array of lines representing the data section.
	 * @param contentEncoding The encoding content to be used for validation.
	 * 
	 * @throws IllegalArgumentException If the first character of any line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position of the unrecognized character.
	 *     If the last character of any line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position of the unrecognized character.
	 *     If any character in the body of the data content is not recognized according to the encoding content,
	 *     an error message is printed indicating the position of the unrecognized character.
	 */
	private static int validateBodyData(String[] line, Map<String, String> contentEncoding) {
		Objects.requireNonNull(line);
		Objects.requireNonNull(contentEncoding);
		int erreur = 0;
		for(int i = 1; i < line.length - 1; i++) {
			erreur += validateFirstChar(line[i], i+1, contentEncoding); //first character of line
			erreur += validateLastChar(line[i], i+1, contentEncoding); //last character of line
			erreur += validateContentMap(line[i], contentEncoding, i+1);//check the content line by line
		}
		return erreur;
	}
	
	/**
	 * Validates the first character of a specific line in the data section based on the specified encoding content.
	 * 
	 * @param ligne The string representing a line in the data section.
	 * @param indice The index of the line being validated.
	 * @param contentEncoding The encoding content to be used for validation.
	 * 
	 * @throws IllegalArgumentException If the first character of the line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position and the unrecognized character.
	 */
	private static int validateFirstChar(String ligne, int indice, Map<String, String> contentEncoding) {
		Objects.requireNonNull(ligne);
		Objects.requireNonNull(indice);
		Objects.requireNonNull(contentEncoding);
		int erreur = 0;
		String firstCaractere = ligne.charAt(0) + ""; //String to String[]
		if(!contentEncoding.containsValue(firstCaractere)) { //check if the character belongs to the encodings section
			System.out.println("ERROR character at line " + indice + " column: " + firstCaractere + " not recognized");
			erreur++;
		}
		return erreur;
	}
	
	/**
	 * Validates the last character of a specific line in the data content based on the specified encoding section.
	 * 
	 * @param ligne The string representing a line in the data section.
	 * @param indice The index of the line being validated.
	 * @param contentEncoding The encoding content to be used for validation.
	 * 
	 * @throws IllegalArgumentException If the last character of the line is not recognized according to the encoding content,
	 *     an error message is printed indicating the position and the unrecognized character.
	 */
	private static int validateLastChar(String ligne, int indice, Map<String, String> contentEncoding) {
		Objects.requireNonNull(ligne);
		Objects.requireNonNull(indice);
		Objects.requireNonNull(contentEncoding);
		int erreur = 0;
		int lastcol = ligne.length();
		String lastCaractere = ligne.charAt(lastcol - 1) + ""; //String to String[]
		if(!contentEncoding.containsValue(lastCaractere)) { ///check if the character belongs to the encodings section
			System.out.println("ERROR character at line " + indice + " column " + lastcol + " : " + lastCaractere + "not recognized");
			erreur++;
		}
		return erreur;
	}
	
	/**
	 * Validates the content of a specific line in the data section excluding the first and last characters,
	 * based on the specified encoding content.
	 * 
	 * @param ligne The string representing a line in the data content.
	 * @param contentEncoding The encoding content to be used for validation.
	 * @param indice The index of the line being validated.
	 * 
	 * @throws IllegalArgumentException If any character in the body of the line (excluding the first and last characters)
	 *     is not recognized according to the encoding content, an error message is printed indicating the position and
	 *     the unrecognized character.
	 */
	private static int validateContentMap(String ligne, Map<String, String> contentEncoding, int indice) {
		Objects.requireNonNull(ligne);
		Objects.requireNonNull(contentEncoding);
		Objects.requireNonNull(indice);
		int erreur = 0;
		for(int i = 1; i < ligne.length() - 1; i++) { //the first and last character of the line have already been checked
			if(!contentEncoding.containsValue(String.valueOf(ligne.charAt(i))) && ligne.charAt(i) != ' ') { //if it is not the empty character we checked if it belongs to the encoding section
				System.out.println("ERROR element not recognized at line " + indice + " column " + (i+1));
				erreur++;
			}
		}
		return erreur;
	}
	
	
	private static String getKeyFromValue(Map<String, String> map, String value) {
    for (Map.Entry<String, String> entry : map.entrySet()) {
        if (Objects.equals(entry.getValue(), value)) {
            return entry.getKey();
        }
    }
    return null; 
	}


	protected static void getDataMapElements( List<Map<String, String>> listOfElements,String dataContent,Map<String, String> encodingsMap) {
	  String[] dataLines = convertChaine(dataContent);
	  String skin;
	  
	  Map<String,String> tmpMap = new HashMap<>();
	  for(int i = 0;i<dataLines.length;i++) {
	    for(int j =0;j<dataLines[i].length();j++) {
	      //System.out.print(String.valueOf(dataLines[i].charAt(j)));
	      skin =getKeyFromValue(encodingsMap,String.valueOf(dataLines[i].charAt(j)));
	      
	      if(skin == null) {
	        tmpMap.put("skin","VOID");
	        tmpMap.put("position","("+j+","+i+")");
	        tmpMap.put("entityOrElement","element");
	        listOfElements.add(new HashMap<>(tmpMap));
	        tmpMap.clear();
	      }
	      else {
	        tmpMap.put("skin",skin);
          tmpMap.put("position","("+j+","+i+")");
          tmpMap.put("entityOrElement","element");
          
          listOfElements.add(new HashMap<>(tmpMap));
          tmpMap.clear();
	      }
	      
	    }
	  }
	}

}

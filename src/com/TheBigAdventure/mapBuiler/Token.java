package com.TheBigAdventure.mapBuiler;

/**
 * Enumeration representing different token types for lexical analysis.
 *
 * <p>The {@code Token} enumeration defines various token types used in lexical analysis, each
 * associated with a regular expression pattern to match the token in the input text.
 */
public enum Token {
  
  
  
  SECTION_HEADER("\\[\\w+\\]"),
  SIZE("\\(\\s*\\d+\\s*x\\s*\\d+\\s*\\)"),
  DATA_BLOCK("\"\"\"[\\s\\S]*?\"\"\""),
  QUOTE("\"\"\"[^\\\"]+\"\"\""),
  ZONE("\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\)\\s*\\(\\s*\\d+\\s*x\\s*\\d+\\s*\\)"),
  POSITION("\\(\\s*([0-9]+)\\s*,\\s*([0-9]+)\\s*\\)"),
  


  NUMBER("[0-9]+"),
  IDENTIFIER("[A-Za-z]+"),
  
  

  LEFT_PARENS("\\("),
  RIGHT_PARENS("\\)"),
  COMMA(","),
  COLON(":"),
  LEFT_BRACKET("\\["),
  RIGHT_BRACKET("\\]")
  

  



  ;
    private final String regex;

    Token(String regex) {
        this.regex = regex;
    }
    
    /**
     * Gets the regular expression associated with the token.
     *
     * @return The regular expression.
     */
    public String regex() {
        return regex;
    }
}

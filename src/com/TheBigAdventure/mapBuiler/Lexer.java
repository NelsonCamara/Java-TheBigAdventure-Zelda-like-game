package com.TheBigAdventure.mapBuiler;

import java.util.List;

//import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import static java.util.stream.Collectors.joining;

/**
 * Lexer class for tokenizing input text based on a predefined set of patterns.
 */
public class Lexer {
  
    private static final List<Token> TOKENS = List.of(Token.values());
    private static final Pattern PATTERN = Pattern.compile(
        TOKENS.stream()
            .map(token -> "(" + token.regex() + ")")
            .collect(joining("|")));

    private final String text;
    private final Matcher matcher;
    private int lastEnd = 0;
    
    /**
     * Constructs a Lexer object with the specified input text.
     *
     * @param text The input text to tokenize.
     */
    public Lexer(String text) {
        this.text = text;
        this.matcher = PATTERN.matcher(text);
        
    }
    
    /**
     * Retrieves the next token and its content from the input text.
     *
     * @return The next token and its content as a Result object, or null if no more tokens are found.
     */
    public Result nextResult() {
       
        if (!matcher.find(lastEnd)) {
            return null;
        }

        for (Token token : TOKENS) {
            if (matcher.group(token.ordinal() + 1) != null) {
                String content = matcher.group(token.ordinal() + 1);
                lastEnd = matcher.end();
                return new Result(token, content);
            }
        }

        throw new IllegalStateException("No matching token found.");
    }
    
    /**
     * Retrieves the content of the next three tokens from the input text without advancing the tokenization.
     *
     * @return An array containing the content of the next three tokens, or null if fewer than three tokens are found.
     */
    public String[] nextThreeResultsContent() {
      int originalLastEnd = lastEnd;
      int rangeOfI = 2;
      Matcher originalMatcher = PATTERN.matcher(text);
      originalMatcher.useTransparentBounds(true).useAnchoringBounds(false);
      originalMatcher.region(lastEnd, text.length());

      String[] results = new String[2];

      for (int i = 0; i < rangeOfI; i++) {
          Result nextResult = nextResult();
          if (nextResult == null) {
              break; 
          }

          results[i] = nextResult.content().trim();
          
      }

      lastEnd = originalLastEnd;
      matcher.reset();
      matcher.useTransparentBounds(true).useAnchoringBounds(false);
      matcher.region(lastEnd, text.length());

      return results;
  }

}

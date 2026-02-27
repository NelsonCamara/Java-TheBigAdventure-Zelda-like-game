package com.TheBigAdventure.mapBuiler;

import java.util.Objects;


/**
 * Represents the result of a lexical analysis, including the token type and its content.
 *
 * <p>The {@code Result} class is used to encapsulate the outcome of a lexical analysis, providing
 * information about the token type and the associated content.
 *
 * @param token   The type of the token.
 * @param content The content associated with the token.
 */
public record Result(Token token, String content) {
	
    /**
     * Constructs a Result with the specified token type and content.
     *
     * @param token   The type of the token.
     * @param content The content associated with the token.
     */

  public Result {
    Objects.requireNonNull(token);
    Objects.requireNonNull(content);
  }
}


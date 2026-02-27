package com.TheBigAdventure.mapBuiler;

/**
 * Functional interface for updating a Case with a specific object of type T.
 * Implementing classes or lambda expressions are expected to provide custom logic
 * for updating a Case based on the input object.
 *
 * @param <T> The type of object used for updating the Case.
 */
@FunctionalInterface
public interface CaseUpdater<T> {
  Case update(Case gridCase, T object);
}
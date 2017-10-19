package org.ilumbo.twentyfourbrain.operations;

public interface Operation {
	/**
	 * Returns whether this operation is valid given the passed inputs.
	 *
	 * Valid means {@link #perform(int, int)} would be able to return a positive integer or zero sensibly.
	 */
	public boolean determineValid(int firstInput, int secondInput);
	/**
	 * Returns a string which describes this operation on the passed inputs in a way humans can understand (such as
	 * "3 + 5 = 8").
	 */
	public String describeForHumans(int firstInput, int secondInput);
	/**
	 * Performs the operation on the passed inputs ("operands"), returning the result.
	 *
	 * Behaviour is undefined in case {@link #determineValid(int, int)} returned {@code false} (for this input).
	 */
	public int perform(int firstInput, int secondInput);
}
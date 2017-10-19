package org.ilumbo.twentyfourbrain.operations;

public class Minus implements Operation {
	@Override
	public boolean determineValid(int firstInput, int secondInput) {
		return firstInput >= secondInput;
	}
	public String describeForHumans(int firstInput, int secondInput) {
		return new StringBuilder(16)
				.append(firstInput).append(" - ").append(secondInput).append(" = ").append(firstInput - secondInput)
				.toString();
	}
	public int perform(int firstInput, int secondInput) throws IllegalArgumentException {
		return firstInput - secondInput;
	}
}
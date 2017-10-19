package org.ilumbo.twentyfourbrain;

import org.ilumbo.twentyfourbrain.operations.Minus;
import org.ilumbo.twentyfourbrain.operations.Operation;
import org.ilumbo.twentyfourbrain.operations.Plus;
import org.ilumbo.twentyfourbrain.operations.Solidus;
import org.ilumbo.twentyfourbrain.operations.Times;

public class Brain {
	/**
	 * All of the operations.
	 */
	private static final Operation[] operations = new Operation[]{
			new Plus(),
			new Minus(),
			new Times(),
			new Solidus()
	};
	public Brain() {
	}
	/**
	 * Calculates and returns whether the passed route is a solution given the passed start values.
	 */
	public boolean checkSolution(int[] startValues, Route route) {
		final State state = new State(startValues);
		// Perform the steps.
		int output = Integer.MIN_VALUE;
		for (int stepIndex = 0; route.steps.length != stepIndex; stepIndex++) {
			final Step step = route.steps[stepIndex];
			final int firstInput = state.get(step.firstInputPointer);
			final int secondInput = state.get(step.secondInputPointer);
			// If the step cannot be performed because its operation cannot be performed for the input, the solution is no
			// good.
			if (false == step.operation.determineValid(firstInput, secondInput)) {
				return false;
			}
			output = step.operation.perform(firstInput, secondInput);
			if (stepIndex != route.steps.length - 1) {
				state.set(step.outputPointer, output);
			}
			System.out.println(output);
		}
		return 24 == output;
	}
}
package org.ilumbo.twentyfourbrain;

import org.ilumbo.twentyfourbrain.operations.Minus;
import org.ilumbo.twentyfourbrain.operations.Operation;
import org.ilumbo.twentyfourbrain.operations.Plus;
import org.ilumbo.twentyfourbrain.operations.Solidus;
import org.ilumbo.twentyfourbrain.operations.Times;

import java.util.ArrayList;

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
	/**
	 * All of the possible routes.
	 */
	private final Route[] routes;
	public Brain() {
		routes = composeRoutes();
	}
	protected static Route[] composeRoutes() {
		final ArrayList<Route> result = new ArrayList<>(8192);
		final byte[] startPointers = new byte[]{State.START_0, State.START_1, State.START_2, State.START_3};
		// Iterate over every order of start values.
		for (final byte firstPointer : startPointers) {
			for (final byte secondPointer : startPointers) {
				if (secondPointer == firstPointer) {
					continue;
				}
				for (final byte thirdPointer : startPointers) {
					if (thirdPointer == firstPointer || thirdPointer == secondPointer) {
						continue;
					}
					for (final byte lastPointer : startPointers) {
						if (lastPointer == firstPointer || lastPointer == secondPointer || lastPointer == thirdPointer) {
							continue;
						}
						// Iterate over every operation.
						for (final Operation firstOperation : operations) {
							for (final Operation secondOperation : operations) {
								for (final Operation thirdOperation : operations) {
									// Compose the route that look like this:
									//   0  ? 1  → t0
									//   t0 ? 2  → t0
									//   t0 ? 3  → …
									result.add(new Route(new Step[]{
											new Step(firstPointer, secondPointer, firstOperation, State.TEMP_0),
											new Step(State.TEMP_0, thirdPointer, secondOperation, State.TEMP_0),
											new Step(State.TEMP_0, lastPointer, thirdOperation)
									}));
									// Add the variants of the route above which have the inputs on the second and third step swapped.
									result.add(new Route(new Step[]{
											new Step(firstPointer, secondPointer, firstOperation, State.TEMP_0),
											new Step(thirdPointer, State.TEMP_0, secondOperation, State.TEMP_0),
											new Step(State.TEMP_0, lastPointer, thirdOperation)
									}));
									result.add(new Route(new Step[]{
											new Step(firstPointer, secondPointer, firstOperation, State.TEMP_0),
											new Step(State.TEMP_0, thirdPointer, secondOperation, State.TEMP_0),
											new Step(lastPointer, State.TEMP_0, thirdOperation)
									}));
									result.add(new Route(new Step[]{
											new Step(firstPointer, secondPointer, firstOperation, State.TEMP_0),
											new Step(thirdPointer, State.TEMP_0, secondOperation, State.TEMP_0),
											new Step(lastPointer, State.TEMP_0, thirdOperation)
									}));
									// Compose the route that look like this:
									//   0  ? 1  → t0
									//   2  ? 3  → t1
									//   t0 ? t1 → …
									result.add(new Route(new Step[]{
											new Step(firstPointer, secondPointer, firstOperation, State.TEMP_0),
											new Step(thirdPointer, lastPointer, secondOperation, State.TEMP_1),
											new Step(State.TEMP_0, State.TEMP_1, thirdOperation)
									}));
									// (There is no need to try both t0 ? t1 → … and t1 ? t0 → …: there are also routes in which the start
									// values are swapped, which is effectively the same.)
								}
							}
						}
					}
				}
			}
		}
		return result.toArray(new Route[result.size()]);
	}
	/**
	 * Calculates and returns whether the passed route is a solution given the passed start values.
	 */
	public static boolean checkSolution(int[] startValues, Route route) {
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
		}
		return 24 == output;
	}
	/**
	 * Given the passed start values, counts and returns the number of different routes this brain can come up with that
	 * are solutions. Note that as distinct routes can look very similar, the number returned by this method will probably
	 * be higher than you'd expect.
	 */
	public int countSolutions(int[] startValues) {
		int result = 0;
		for (final Route candidate : routes) {
			if (checkSolution(startValues, candidate)) {
				result++;
			}
		}
		return result;
	}
	/**
	 * Finds the first route that is a solution given the passed start values and return a string with human-readable
	 * proof, or {@code null} if there is no such route.
	 */
	public String explainSolution(int[] startValues) {
		final Route route = findSolution(startValues);
		if (null == route) {
			return null;
		}
		final StringBuilder resultBuilder = new StringBuilder(64);
		final State state = new State(startValues);
		// Perform the steps.
		for (int stepIndex = 0; route.steps.length != stepIndex; stepIndex++) {
			final Step step = route.steps[stepIndex];
			final int firstInput = state.get(step.firstInputPointer);
			final int secondInput = state.get(step.secondInputPointer);
			resultBuilder.append(step.operation.describeForHumans(firstInput, secondInput));
			if (stepIndex != route.steps.length - 1) {
				state.set(step.outputPointer, step.operation.perform(firstInput, secondInput));
				resultBuilder.append('\n');
			}
		}
		return resultBuilder.toString();
	}
	/**
	 * Returns the first route that is a solution given the passed start values, or {@code null} if there is no such
	 * route.
	 */
	public Route findSolution(int[] startValues) {
		for (final Route candidate : routes) {
			if (checkSolution(startValues, candidate)) {
				return candidate;
			}
		}
		return null;
	}
}
package org.ilumbo.twentyfourbrain;

public class Cli {
	public static void main(String[] arguments) {
		if (4 != arguments.length) {
			throw new IllegalArgumentException("Please provide exactly four values to start with");
		}
		final int[] startValues = new int[4];
		{
			int argumentIndex = 0;
			try {
				for (; 4 != argumentIndex; argumentIndex++) {
					startValues[argumentIndex] = Integer.parseInt(arguments[argumentIndex], 10);
				}
			} catch (NumberFormatException exception) {
				throw new IllegalArgumentException(
						new StringBuilder(32)
								.append("This starting value does not seem to be an integer: ").append('"').append(arguments[argumentIndex]).append('"')
								.toString()
				);
			}
		}
		final String explanation = new Brain().explainSolution(startValues);
		if (null == explanation) {
			System.out.println("No solution.");
		} else /* if (null != explanation) */ {
			System.out.println(explanation);
		}
	}
}
package org.ilumbo.twentyfourbrain;

/**
 * A route is a potential solution. It is essentially a list of {@link Step}s.
 */
public class Route {
	public final Step[] steps;
	public Route(Step[] steps) {
		this.steps = steps;
	}
}
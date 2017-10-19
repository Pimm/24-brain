package org.ilumbo.twentyfourbrain;

import org.ilumbo.twentyfourbrain.operations.Operation;
import org.ilumbo.twentyfourbrain.operations.Solidus;

/**
 * A step represents one action in a solving route, such as "take number 2 and number 3 and add them, putting the result
 * in temp 1".
 */
public class Step {
	public final byte firstInputPointer;
	public final Operation operation;
	/**
	 * May be {@link Byte#MIN_VALUE}, for the last step in a route.
	 */
	public final byte outputPointer;
	public final byte secondInputPointer;
	public Step(byte firstInputPointer, byte secondInputPointer, Operation operation, byte outputPointer) {
		this.firstInputPointer = firstInputPointer;
		this.secondInputPointer = secondInputPointer;
		this.operation = operation;
		this.outputPointer = outputPointer;
	}
	public Step(byte firstInputPointer, byte secondInputPointer, Operation operation) {
		this(firstInputPointer, secondInputPointer, operation, Byte.MIN_VALUE);
	}
}
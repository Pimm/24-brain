package org.ilumbo.twentyfourbrain;

public class State {
	/**
	 * The bits in the pointer which correlate to the index in {@link #register}.
	 */
	@SuppressWarnings("WeakerAccess")
	protected static final byte INDEX_MASK = 0x0F;
	/**
	 * The placeholder for an actual value.
	 */
	@SuppressWarnings("WeakerAccess")
	protected static final int NO_VALUE = Integer.MIN_VALUE;
	/**
	 * The bit in the pointer which determines whether a register entry is read-only or not.
	 */
	@SuppressWarnings("WeakerAccess")
	protected static final byte READ_ONLY = 0x10;
	/**
	 * The pointer to the register entry of the first starting number.
	 */
	public static final byte START_0 = 0 | READ_ONLY;
	/**
	 * The pointer to the register entry of the second starting number.
	 */
	public static final byte START_1 = 1 | READ_ONLY;
	/**
	 * The pointer to the register entry of the third starting number.
	 */
	public static final byte START_2 = 2 | READ_ONLY;
	/**
	 * The pointer to the register entry of the last starting number.
	 */
	public static final byte START_3 = 3 | READ_ONLY;
	/**
	 * The pointer to the one temporary register entry.
	 */
	public static final byte TEMP_0 = 4;
	/**
	 * The pointer to the other temporary register entry.
	 */
	public static final byte TEMP_1 = 5;
	/**
	 * The current values.
	 */
	@SuppressWarnings("WeakerAccess")
	protected final int[] register;
	public State(int[] startValues) {
		if (4 != startValues.length) {
			throw new IllegalArgumentException("There must be exactly 4 start values");
		}
		register = new int[6];
		register[START_0 & INDEX_MASK] = startValues[0];
		register[START_1 & INDEX_MASK] = startValues[1];
		register[START_2 & INDEX_MASK] = startValues[2];
		register[START_3 & INDEX_MASK] = startValues[3];
		register[TEMP_0 & INDEX_MASK] = NO_VALUE;
		register[TEMP_1 & INDEX_MASK] = NO_VALUE;
	}
	/**
	 * Returns one of the values.
	 */
	public int get(byte pointer) {
		final int value = register[pointer & INDEX_MASK];
		if (NO_VALUE == value) {
			throw new IllegalStateException("This value hasn't been set yet");
		}
		return value;
	}
	/**
	 * Overwrites one of the values.
	 */
	public void set(byte pointer, int value) {
		if (0 != (pointer & READ_ONLY)) {
			throw new IllegalArgumentException("This value is read-only");
		}
		register[pointer & INDEX_MASK] = value;
	}
}
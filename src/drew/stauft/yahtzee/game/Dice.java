package drew.stauft.yahtzee.game;

import java.util.Observable;
import java.util.Random;

/**
 * Represents a dice.
 * @author Drew Stauft
 *
 */
public class Dice extends Observable {
	
	/**
	 * The value.
	 */
	private int value;
	
	/**
	 * Gets the value.
	 * @return The value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Determines whether the dice is locked.
	 */
	private boolean locked;
	
	/**
	 * Determines whether the dice is locked.
	 * @return The locked state.
	 */
	public boolean isLocked() {
		return locked;
	}
	
	/**
	 * Locks the dice.
	 * @param value The locked state.
	 */
	public void setLocked(boolean value) {
		locked = value;
		notifyObservers();
	}
	
	/**
	 * Constructor.
	 */
	public Dice() {
		roll();
	}
	
	/**
	 * Rolls the dice if it is not locked.
	 */
	public void roll() {
		if (!isLocked()) {
			value = new Random().nextInt(6) + 1;
			notifyObservers();
		}
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChanged() {
		return true;
	}
}

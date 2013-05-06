package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Represents an element.
 * @author Drew Stauft
 *
 */
public abstract class Element {	
	
	/**
	 * The result.
	 */
	private Integer result = null;	
	
	/**
	 * The name of the element.
	 */
	private String name;
	
	/**
	 * Gets the result.
	 * @return The result.
	 */
	public Integer getResult() {
		return result;
	}
	
	/**
	 * Determines whether the element has a result.
	 * @return The result state. 
	 */
	public boolean hasResult() {
		return result != null;
	}
	
	/**
	 * Gets the name.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Constructor.
	 * @param name The name of the element.
	 */
	protected Element(String name) {
		this.name = name;
	}
	
	/**
	 * Reset the element.
	 */
	public void unsetDices() {
		result = null;
	}
	
	/**
	 * Apply the given dices.
	 * @param dices The dices.
	 */
	public void setDices(Dice[] dices) {
		result = calculate(dices);
	}
	
	/**
	 * Calculates the result of the given dices respecting the allowed state.
	 * @param dices The dices.
	 * @return The result.
	 */
	public int calculate(Dice[] dices) {
		if (areDicesAllowed(dices)) {
			return calculateResult(dices);
		} else {
			return 0;
		}
	}
	
	/**
	 * Determines whether the dices fit.
	 * @param dices The dices.
	 * @return The fit state.
	 */
	public boolean doDicesFit(Dice[] dices) {
		return areDicesAllowed(dices);
	}
	
	/**
	 * Determines whether the dices can be applied to the element.
	 * @param dices The dices.
	 * @return The fit state.
	 */
	protected abstract boolean areDicesAllowed(Dice[] dices);
	
	/**
	 * Calculates the result of the given dices.
	 * @param dices The dices.
	 * @return The result.
	 */
	protected abstract int calculateResult(Dice[] dices);	
}

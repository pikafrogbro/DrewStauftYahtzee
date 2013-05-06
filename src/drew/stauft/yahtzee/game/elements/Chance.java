package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element where all dices are allowed.
 * @author Drew Stauft
 *
 */
public class Chance extends Element {
	
	/**
	 * Constructor.
	 * @param name The name.
	 */
	public Chance(String name){
		super(name);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean areDicesAllowed(Dice[] dices) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateResult(Dice[] dices) {
		int sum = 0;

		for (Dice dice : dices) {
			sum += dice.getValue();
		}
		return sum;
	}
}

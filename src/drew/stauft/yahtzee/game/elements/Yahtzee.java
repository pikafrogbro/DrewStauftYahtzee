package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element where all dices are equal.
 * @author Drew Stauft
 *
 */
public class Yahtzee extends OneKind {

	/**
	 * Constructor.
	 * @param name The name.
	 */
	public Yahtzee(String name){
		super(name, 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateResult(Dice[] dices) {
		return 50;
	}
}

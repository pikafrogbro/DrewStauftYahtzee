package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * {@link Straight} with 5 dices in a row.
 * @author Drew Stauft
 *
 */
public class LargeStraight extends Straight {
	
	/**
	 * Constructor.
	 * @param name The name.
	 */
	public LargeStraight(String name){
		super(name, 5);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateResult(Dice[] dices) {
		return 40;
	}
}

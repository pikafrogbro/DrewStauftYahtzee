package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * {@link Straight} with 4 dices in a row.
 * @author Drew Stauft
 *
 */
public class SmallStraight extends Straight {
	
	/**
	 * Constructor.
	 * @param name The name.
	 */
	public SmallStraight(String name){
		super(name, 4);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateResult(Dice[] dices) {
		return 30;
	}
}

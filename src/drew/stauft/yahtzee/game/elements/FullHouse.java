package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element where 3 and 2 dices have to be equal.
 * @author Drew Stauft
 *
 */
public class FullHouse extends Element {
	
	/**
	 * Constructor.
	 * @param name The name.
	 */
	public FullHouse(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean areDicesAllowed(Dice[] dices) {
		int[] countByNumber = new int[6];
		
		for (Dice dice : dices) {
			countByNumber[dice.getValue() - 1]++;
		}
		
		boolean twoHits = false;
		boolean threeHits = false;
		
		for (int i : countByNumber) {
			if (i == 2) {
				twoHits = true;
			} else if(i == 3) {
				threeHits = true;
			}
		}
		
		return twoHits && threeHits;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int calculateResult(Dice[] dices) {
		return 25;
	}
}

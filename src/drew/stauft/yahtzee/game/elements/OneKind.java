package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element where a certain number of dices has to be equal.
 * @author Drew Stauft
 *
 */
public class OneKind extends Element {
	
	/**
	 * The count.
	 */
	private int count;
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param count The count.
	 */
	public OneKind(String name, int count){
		super(name);
		this.count = count;
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
		
		for (int i : countByNumber) {
			if (i >= count) {
				return true;
			}
		}
		
		return false;
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

package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element where dices have to be equal to a certain number.
 * @author Drew Stauft
 *
 */
public class Number extends Element {
	
	/**
	 * The number.
	 */
	private int number;
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param number The number.
	 */
	public Number(String name, int number){
		super(name);
		this.number = number;
	}
	
	/**
	 * {@inheritDoc}
	 */
	 @Override
	public boolean doDicesFit(Dice[] dices) {
		return calculate(dices) >= number * 3;
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
			if (dice.getValue() == number) {
				sum += dice.getValue();
			}
		}
		
		return sum;
	}
}

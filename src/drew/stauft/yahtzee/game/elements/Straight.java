package drew.stauft.yahtzee.game.elements;

import drew.stauft.yahtzee.game.Dice;

/**
 * Element with a certain number of dices in a row.
 * @author Drew Stauft
 *
 */
public abstract class Straight extends Element {
	
	private int count;
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param count The count.
	 */
	public Straight(String name, int count){
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
		
		int hitsInARow = 0;
		int maxHitsInARow = 0;
		
		for (int i : countByNumber) {
			if (i > 0) {
				hitsInARow++;
			} else {
				hitsInARow = 0;
			}
			
			if (hitsInARow > maxHitsInARow) {
				maxHitsInARow = hitsInARow;
			}				
		}
		
		return maxHitsInARow >= count;
	}
}

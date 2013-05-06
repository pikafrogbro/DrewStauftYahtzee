package drew.stauft.yahtzee.game;

import android.content.res.Resources;
import drew.stauft.android.R;
import drew.stauft.yahtzee.game.elements.Element;
import drew.stauft.yahtzee.game.elements.Number;

/**
 * The lower section of the game.
 * This section has a bonus if its sum is greater than 62.
 * @author Drew Stauft
 *
 */
public class LowerSection extends Section {

	/**
	 * Constructor
	 * @param resources The resources.
	 */
	public LowerSection(Resources resources) {
		add(new Number(resources.getText(R.string.one).toString(), 1));
		add(new Number(resources.getText(R.string.two).toString(), 2));
		add(new Number(resources.getText(R.string.three).toString(), 3));
		add(new Number(resources.getText(R.string.four).toString(), 4));
		add(new Number(resources.getText(R.string.five).toString(), 5));
		add(new Number(resources.getText(R.string.six).toString(), 6));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBonus() {
		int sum = 0;
		for (Element element : getElements()) {
			if (element.hasResult()) {
				sum += element.getResult();
			}
		}
		
		if (sum >= 63) {
			return 35;
		}

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasBonus() {
		return true;
	}
}
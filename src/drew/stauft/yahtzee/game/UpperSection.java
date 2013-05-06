package drew.stauft.yahtzee.game;

import android.content.res.Resources;
import drew.stauft.android.R;
import drew.stauft.yahtzee.game.elements.*;

/**
 * The upper section of the game.
 * This section has no bonus.
 * @author Drew Stauft
 *
 */
public class UpperSection extends Section {

	/**
	 * Constructor.
	 * @param resources The resources.
	 */
	public UpperSection(Resources resources) {
		add(new OneKind(resources.getText(R.string.three_of_a_kind).toString(), 3));
		add(new OneKind(resources.getText(R.string.four_of_a_kind).toString(), 4));
		add(new FullHouse(resources.getText(R.string.full_house).toString()));
		add(new SmallStraight(resources.getText(R.string.small_straight).toString()));
		add(new LargeStraight(resources.getText(R.string.large_straight).toString()));
		add(new Yahtzee(resources.getText(R.string.yahtzee).toString()));
		add(new Chance(resources.getText(R.string.chance).toString()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBonus() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasBonus() {
		return false;
	}
}

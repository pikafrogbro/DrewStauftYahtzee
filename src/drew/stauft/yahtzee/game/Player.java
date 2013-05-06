package drew.stauft.yahtzee.game;

import android.content.res.Resources;

/**
 * Represents a player.
 * @author Drew Stauft
 *
 */
public class Player {
	
	/**
	 * The name.
	 */
	private String name;

	/**
	 * The {@link LowerSection}.
	 */
	private Section lowerSection;
	
	/**
	 * The {@link UpperSection}.
	 */
	private Section upperSection;
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param resources The resources.
	 */
	public Player(String name, Resources resources) {
		this.name = name;
		lowerSection = new LowerSection(resources);
		upperSection = new UpperSection(resources);
	}
	
	/**
	 * Gets the name.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the {@link LowerSection}.
	 * @return The {@link LowerSection}.
	 */
	public Section getLowerSection() {
		return lowerSection;
	}
	
	/**
	 * Gets the {@link UpperSection}.
	 * @return The {@link UpperSection}.
	 */
	public Section getUpperSection() {
		return upperSection;
	}
	
	/**
	 * Get the current sum of the player.
	 * @return The current sum of the player.
	 */
	public int getResult() {
		return lowerSection.getResult() + upperSection.getResult();
	}
}

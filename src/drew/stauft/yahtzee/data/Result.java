package drew.stauft.yahtzee.data;

/**
 * Represents a game result / highscore.
 * @author Drew Stauft
 *
 */
public class Result implements Comparable<Result> {
	
	/**
	 * The name of the player.
	 */
	private String name;
	
	/**
	 * The score of the player.
	 */
	private int score;
	
	/**
	 * Gets the name of the player.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the score of the player.
	 * @return The score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param score The score.
	 */
	public Result(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Result another) {
		if (getScore() > another.getScore()) {
			return -1;
		} else if (getScore() < another.getScore()) {
			return 1;
		}
		return 0;
	}
}

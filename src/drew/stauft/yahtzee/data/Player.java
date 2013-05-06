package drew.stauft.yahtzee.data;

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
	 * Determines whether the player is active.
	 */
	private boolean active;
	
	/**
	 * Gets the name.
	 * @return The name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 * @param name The name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Determines whether the player is active.
	 * @return The active state.
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets the active state.
	 * @param active The active state.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Constructor.
	 * @param name The name.
	 * @param active The active state.
	 */
	public Player(String name, boolean active) {
		this.name = name;
		this.active = active;
	}
}

package drew.stauft.yahtzee.game;

import java.util.List;

import drew.stauft.yahtzee.game.elements.Element;

/**
 * The context containing all states required for the game.
 * @author Drew Stauft
 *
 */
public class GameContext {
	
	/**
	 * The players.
	 */
	public List<Player> players;
	
	/**
	 * The marked/selected element.
	 */
	public Element markedElement;
	
	/**
	 * The dices.
	 */
	public Dice[] dices = new Dice[] { new Dice(), new Dice(), new Dice(), new Dice(), new Dice() };	
	
	/**
	 * The number of dice rolls of the current player (1-3)
	 */
	public int rollCount = 1;
	
	/**
	 * The current step/round in the game (1-13).
	 */
	public int currentStep = 1;
	
	/**
	 * The index of the current player.
	 */
	public int currentPlayerIndex = 0;
	
	/**
	 * Constructor.
	 * @param players The players.
	 */
	public GameContext(List<Player> players) {
		this.players = players;
	}
	
	/**
	 * Gets the current player.
	 * @return The current player.
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
	
	/**
	 * Gets the number of players.
	 * @return The number of players.
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}
}

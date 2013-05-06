package drew.stauft.yahtzee;

import java.util.List;

import drew.stauft.yahtzee.data.Result;
import drew.stauft.yahtzee.game.GameContext;

public final class Globals {
		
	public final static String SHOW_HIGHSCORES = "HIGHSCORES";
		
	private static boolean soundEnabled;
	
	public static boolean isSoundEnabled() {
		return soundEnabled;
	}	
	
	public static void setSoundEnabled(boolean soundEnabled) {
		Globals.soundEnabled = soundEnabled;
	}
	
	/**
	 * The game context of the current game.
	 * Program Activity -> Game Activity
	 */
	private static GameContext gameContext;
	
	public static GameContext getGameContext() {
		return gameContext;
	}

	public static void setGameContext(GameContext gameContext) {
		Globals.gameContext = gameContext;
	}

	// Pass scores of current game to Results activity
	
	/**
	 * The list of highscores.
	 * Game Activity -> Results Activity
	 */
	private static List<Result> results;

	/**
	 * 
	 * @return
	 */
	public static List<Result> getResults() {
		return results;
	}
	
	public static void setResults(List<Result> results) {
		Globals.results = results;
	}
}

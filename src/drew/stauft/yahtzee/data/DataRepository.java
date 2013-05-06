package drew.stauft.yahtzee.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Repository encapsulating a SQLite database.
 * Used to store player names and highscores.
 * @author Drew Stauft
 *
 */
public class DataRepository {
	
	/**
	 * The name of the players table.
	 */
	private static final String PLAYER_TABLE_NAME = "player";
    
	/**
	 * The name of the name column.
	 */
	private static final String PLAYER_COLUMN_NAME = "name";
    
	/**
	 * The name of the active column.
	 */
	private static final String PLAYER_COLUMN_ACTIVE = "active";
	
	/**
	 * The name of the highscores table.
	 */
	private static final String HIGHSCORES_TABLE_NAME = "highscores";
    
	/**
	 * The name of the name column.
	 */
	private static final String HIGHSCORES_COLUMN_NAME = "name";
    
	/**
	 * The name of the score column.
	 */
	private static final String HIGHSCORES_COLUMN_SCORE = "score";
	
	/**
	 * The {@link DataOpenHelper}.
	 */
	private DataOpenHelper dataOpenHelper;
	
	/**
	 * Constructor.
	 * @param context The context.
	 */
	public DataRepository(Context context){
    	dataOpenHelper = new DataOpenHelper(context);
    }
	
	/**
	 * Get the saved players.
	 * @return The players.
	 */
	public List<Player> getPlayers() {
		SQLiteDatabase database = dataOpenHelper.getReadableDatabase();
		Cursor cursor = database.query(PLAYER_TABLE_NAME, null, null, null, null, null, null);
		List<Player> players = new ArrayList<Player>();
		
		try {
			if (!cursor.moveToFirst()) {
				players.add(new Player("Alice", true));
				players.add(new Player("Bob", true));
				return players;
			}
			
			do {
				String name = cursor.getString(cursor.getColumnIndex(PLAYER_COLUMN_NAME));			
				boolean active = cursor.getInt(cursor.getColumnIndex(PLAYER_COLUMN_ACTIVE)) == 1;			
				players.add(new Player(name, active));
		 	} while(cursor.moveToNext());
			
			return players;
		} finally {
			cursor.close();
			database.close();
		}		
	}
	
	/**
	 * Save the players. Existing players are deleted.
	 * @param players The players.
	 */
	public void savePlayers(List<Player> players) {
		SQLiteDatabase database = dataOpenHelper.getWritableDatabase();
		database.delete(PLAYER_TABLE_NAME, null, null);
		try {
			for (Player player : players) {
				ContentValues values = new ContentValues();
				values.put(PLAYER_COLUMN_NAME, player.getName());
				values.put(PLAYER_COLUMN_ACTIVE, player.isActive());
				database.insert(PLAYER_TABLE_NAME, null, values);
			}		
		} finally {
			database.close();
		}	
	}
	
	/**
	 * Get the highscores.
	 * @return The highscores.
	 */
	public List<Result> getHighscores() {
		SQLiteDatabase database = dataOpenHelper.getReadableDatabase();
		Cursor cursor = database.query(HIGHSCORES_TABLE_NAME, null, null, null, null, null, HIGHSCORES_COLUMN_SCORE + " DESC", "10");

		List<Result> highscores = new ArrayList<Result>();
		
		try {
			if (!cursor.moveToFirst()) {
				return highscores;
			}
			
			do {
				String name = cursor.getString(cursor.getColumnIndex(HIGHSCORES_COLUMN_NAME));
				int score = cursor.getInt(cursor.getColumnIndex(HIGHSCORES_COLUMN_SCORE));
				
				highscores.add(new Result(name, score));
		 	} while(cursor.moveToNext());
			
			return highscores;
		} finally {
			cursor.close();
			database.close();			
		}		
	}
	
	/**
	 * Saves the highscores.
	 * Deletes all highscores that are not in TOP 10 highscores.
	 * @param highscores The highscores.
	 */
	public void addHighscores(List<Result> highscores) {
		SQLiteDatabase database = dataOpenHelper.getWritableDatabase();

		try {
			for (Result result : highscores) {
				ContentValues values = new ContentValues();
				values.put(HIGHSCORES_COLUMN_NAME, result.getName());
				values.put(HIGHSCORES_COLUMN_SCORE, result.getScore());
				database.insert(HIGHSCORES_TABLE_NAME, null, values);

				database.delete(HIGHSCORES_TABLE_NAME, "((SELECT COUNT(*) FROM " 
					+ HIGHSCORES_TABLE_NAME + ") > 10 AND " + HIGHSCORES_COLUMN_SCORE + " < (SELECT " 
					+ HIGHSCORES_COLUMN_SCORE + " FROM " + HIGHSCORES_TABLE_NAME 
					+ " ORDER BY " + HIGHSCORES_COLUMN_SCORE + " DESC LIMIT 1 OFFSET 9))", null);
			}		
		} finally {
			database.close();
		}
	}
	
	/**
	 * {@link SQLiteOpenHelper} to create the database.
	 *
	 */
	private static class DataOpenHelper extends SQLiteOpenHelper {

		/** 
		 * The version of the database.
		 */
		private static final int DATABASE_VERSION = 3;
		
		/**
		 * The name of the database.
		 */
		private static final String DATABASE_NAME = "data";
		
		/**
		 * The SQL to create the players table.
		 */
	    private static final String PLAYER_TABLE_CREATE = "CREATE TABLE " + PLAYER_TABLE_NAME 
	    	+ " (" +PLAYER_COLUMN_NAME + " TEXT, " 
	    	+ PLAYER_COLUMN_ACTIVE + " INTEGER);";
	    
	    /**
	     * The SQL to create the highscores table.
	     */
	    private static final String HIGHSCORES_TABLE_CREATE = "CREATE TABLE " + HIGHSCORES_TABLE_NAME 
	    	+ " (" + HIGHSCORES_COLUMN_NAME + " TEXT, " 
	    	+ HIGHSCORES_COLUMN_SCORE + " INTEGER);";      
	    
	    /**
	     * Constructor.
	     * @param context The context.
	     */
	    public DataOpenHelper(Context context){
	    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    /**
		 * {@inheritDoc}
		 */
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(PLAYER_TABLE_CREATE);
	        db.execSQL(HIGHSCORES_TABLE_CREATE);
	    }

	    /**
		 * {@inheritDoc}
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {			
		}
	}
}

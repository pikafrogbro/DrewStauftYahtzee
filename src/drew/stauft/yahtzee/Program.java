package drew.stauft.yahtzee;

import java.util.ArrayList;
import java.util.List;

import drew.stauft.yahtzee.data.DataRepository;
import drew.stauft.yahtzee.data.Player;
import drew.stauft.yahtzee.game.GameContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import drew.stauft.android.R;

/**
 * The activity where players can be created and the game can be started.
 * @author Drew Stauft
 *
 */
public class Program extends Activity {

	/**
	 * Executed when the checkbox related to a player is activated.
	 *
	 */
	private class PlayerCheckBoxListener implements OnClickListener {
		/**
		 * The id of the textbox containing the player's name.
		 */
		private int playerEditTextId;
		
		/**
		 * Constructor.
		 * @param playerEditTextId The id of the textbox containing the player's name.
		 */
		public PlayerCheckBoxListener(int playerEditTextId) {
			this.playerEditTextId = playerEditTextId;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onClick(View v) {
			findViewById(playerEditTextId).setEnabled(((CheckBox)v).isChecked());
	    	enableNewGameButton();
		}
	}
	
	/**
	 * Executed when the text of a textbox containing a player's name is changed.
	 */
	private TextWatcher textWatcher = new TextWatcher() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void afterTextChanged(Editable s) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {		
			enableNewGameButton();
		} 	    
    }; 
	    
    /**
     * Executed when sound is activated/deactivated.
     */
    private OnClickListener toggleSoundListener = new OnClickListener() {
		
    	/**
    	 * {@inheritDoc}
    	 */
		@Override
	    public void onClick(View v) {
			toggleSound(v);
	    }
	};
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ((CheckBox)findViewById(R.id.player1CheckBox)).setOnClickListener(new PlayerCheckBoxListener(R.id.player1NameEditText));
        ((CheckBox)findViewById(R.id.player2CheckBox)).setOnClickListener(new PlayerCheckBoxListener(R.id.player2NameEditText));
        ((CheckBox)findViewById(R.id.player3CheckBox)).setOnClickListener(new PlayerCheckBoxListener(R.id.player3NameEditText));
        ((CheckBox)findViewById(R.id.player4CheckBox)).setOnClickListener(new PlayerCheckBoxListener(R.id.player4NameEditText));
        
		((EditText)findViewById(R.id.player1NameEditText)).addTextChangedListener(textWatcher);
		((EditText)findViewById(R.id.player2NameEditText)).addTextChangedListener(textWatcher);
		((EditText)findViewById(R.id.player3NameEditText)).addTextChangedListener(textWatcher);
		((EditText)findViewById(R.id.player4NameEditText)).addTextChangedListener(textWatcher);
        
		((CheckBox)findViewById(R.id.toggleSoundCheckBox)).setChecked(Globals.isSoundEnabled());
		((TextView)findViewById(R.id.toggleTextView)).setOnClickListener(toggleSoundListener);
        
        showResumeButton();
        
        loadPlayers();
        enableNewGameButton();
    }
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    protected void onResume() {    	    	
    	((CheckBox)findViewById(R.id.toggleSoundCheckBox)).setChecked(Globals.isSoundEnabled());
    	showResumeButton();
    	
    	super.onRestart();
    }    
    
    /**
     * Executed when sound is activated/deactivated.
     */
    public void toggleSound(View view) {
		Globals.setSoundEnabled(!Globals.isSoundEnabled());
		((CheckBox)findViewById(R.id.toggleSoundCheckBox)).setChecked(Globals.isSoundEnabled());
    }
    
    /**
     * Starts a new game.
     * @param view The view.
     */
	public void newGame(View view) {
		savePlayers();
		
		List<drew.stauft.yahtzee.game.Player> players = new ArrayList<drew.stauft.yahtzee.game.Player>();
		for (Player player : getPlayers()) {
			if (player.isActive()) {
				players.add(new drew.stauft.yahtzee.game.Player(player.getName(), getResources()));
			}
		}
		
		GameContext gameContext = new GameContext(players);
		Globals.setGameContext(gameContext);
		
		startActivity(new Intent(Program.this, Game.class));
	}
    
	/**
	 * Resumes the current game.
	 * @param view The view.
	 */
	public void resumeGame(View view) {
		startActivity(new Intent(Program.this, Game.class));
	}

	/**
	 * Shows the highscores.
	 * @param view The view.
	 */
	public void showHighscores(View view) {
		Intent i = new Intent(Program.this, Results.class);
		i.putExtra(Globals.SHOW_HIGHSCORES, true);
		startActivity(i);
	}
	
	/**
	 * Load the saved players.
	 */
    private void loadPlayers() {
    	DataRepository repository = new DataRepository(this);
    	List<Player> players = repository.getPlayers();
    	
    	Player player1 = players.size() > 0 ? players.get(0) : null;
    	Player player2 = players.size() > 1 ? players.get(1) : null;
    	Player player3 = players.size() > 2 ? players.get(2) : null;
    	Player player4 = players.size() > 3 ? players.get(3) : null;
    	
    	initName(player1, R.id.player1CheckBox, R.id.player1NameEditText);
    	initName(player2, R.id.player2CheckBox, R.id.player2NameEditText);
    	initName(player3, R.id.player3CheckBox, R.id.player3NameEditText);
    	initName(player4, R.id.player4CheckBox, R.id.player4NameEditText);
    } 
    
    /**
     * Initializes the player's view elements.
     * @param player The player.
     * @param playerCheckBoxId The id of the player's checkbox.
     * @param playerEditTextId The id of the player's textbox.
     */
    private void initName(Player player, int playerCheckBoxId, int playerEditTextId) {
    	if (player != null) {
			((CheckBox)findViewById(playerCheckBoxId)).setChecked(player.isActive());
			((EditText)findViewById(playerEditTextId)).setText(player.getName());
			findViewById(playerEditTextId).setEnabled(player.isActive());
    	} else {
    		((CheckBox)findViewById(playerCheckBoxId)).setChecked(false);
			findViewById(playerEditTextId).setEnabled(false);
    	}
    } 
    
    /**
     * Saved the current player.
     */
	private void savePlayers() {		
		DataRepository repository = new DataRepository(this);
    	repository.savePlayers(getPlayers());		
	}
    
	/**
	 * Activates/Deactivates the 'New Game' button.
	 */
    private void enableNewGameButton() {
    	boolean activePlayerAvailable = false;
    	
    	for (Player player : getPlayers()) {
			if (player.isActive()) {
				activePlayerAvailable = true;
				break;
			}
		}
        findViewById(R.id.newGameButton).setEnabled(activePlayerAvailable);
    }

    /**
     * Shows/Hides the 'Resume' button.
     */
    private void showResumeButton() {
    	if (Globals.getGameContext() == null) {
        	findViewById(R.id.resumeButton).setVisibility(View.GONE);
        } else {
        	findViewById(R.id.resumeButton).setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * Get the players.
     * @return The players.
     */
    private List<Player> getPlayers() {
    	List<Player> players = new ArrayList<Player>();
    	
    	if (((EditText)findViewById(R.id.player1NameEditText)).getText().length() > 0) {
        	String name = ((EditText)findViewById(R.id.player1NameEditText)).getText().toString();
        	boolean active = ((CheckBox)findViewById(R.id.player1CheckBox)).isChecked();
    		players.add(new Player(name, active));
        }
    	
    	if (((EditText)findViewById(R.id.player2NameEditText)).getText().length() > 0) {
        	String name = ((EditText)findViewById(R.id.player2NameEditText)).getText().toString();
        	boolean active = ((CheckBox)findViewById(R.id.player2CheckBox)).isChecked();
    		players.add(new Player(name, active));
        }
    	
    	if (((EditText)findViewById(R.id.player3NameEditText)).getText().length() > 0) {
        	String name = ((EditText)findViewById(R.id.player3NameEditText)).getText().toString();
        	boolean active = ((CheckBox)findViewById(R.id.player3CheckBox)).isChecked();
    		players.add(new Player(name, active));
        }
    	
    	if (((EditText)findViewById(R.id.player4NameEditText)).getText().length() > 0) {
        	String name = ((EditText)findViewById(R.id.player4NameEditText)).getText().toString();
        	boolean active = ((CheckBox)findViewById(R.id.player4CheckBox)).isChecked();
    		players.add(new Player(name, active));
        }
    	
    	return players;    	
    }
}

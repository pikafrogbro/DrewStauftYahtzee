package drew.stauft.yahtzee;

import java.util.ArrayList;
import java.util.List;

import drew.stauft.android.ShakeDetector;
import drew.stauft.android.ShakeEventListener;
import drew.stauft.yahtzee.data.DataRepository;
import drew.stauft.yahtzee.data.Result;
import drew.stauft.yahtzee.game.Dice;
import drew.stauft.yahtzee.game.GameContext;
import drew.stauft.yahtzee.game.Player;
import drew.stauft.yahtzee.game.elements.Element;
import drew.stauft.yahtzee.ui.DiceImageButton;
import drew.stauft.yahtzee.ui.ElementButton;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import drew.stauft.android.R;

/**
 * The activity where the game is played.
 * Each player can roll the dices three times, the dices can be locked to exclude them from rolling.
 * As soon as the player has marked an {@link Element} it's the next player's turn.
 * @author Drew Stauft
 *
 */
public class Game extends Activity {
	
	/**
	 * {@link ShakeDetector} to roll dices when device is shook.
	 */
	private ShakeDetector shakeDetector = new ShakeDetector();
	
	/**
	 * Mediaplayer to play sound.
	 */
	MediaPlayer rollingDicesMediaPlayer;
	
	/**
	 * The buttons that allow to select an element.
	 */
	private List<ElementButton> elementButtons = new ArrayList<ElementButton>();
	
	/**
	 * Get the {@link GameContext}.
	 * @return The {@link GameContext}.
	 */
	private GameContext getGameContext() {
		return Globals.getGameContext();
	}
		
	/**
	 * Executed when an element is selected.
	 */
	private OnClickListener markListener = new OnClickListener() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
	    public void onClick(View v) {	
			markElement((ElementButton)v);
	    }
	};
		
	/**
	 * Executed when device is shook.
	 */
	private ShakeEventListener shakeEventListener = new ShakeEventListener() {
		
		/**
		 * {@inheritDoc}
		 */
		@Override
	    public void onShake() {
			rollDices(null);
	    }
	};
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
              
        LinearLayout dicesLinearLayout = ((LinearLayout)findViewById(R.id.dicesLinearLayout));
        for (Dice dice : getGameContext().dices) {
        	DiceImageButton diceImageButton = new DiceImageButton(getApplicationContext(), dice);
			dicesLinearLayout.addView(diceImageButton);			
		}  
        
        updateElements();
        update();
    }
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    protected void onResume() {
    	super.onResume();
    	update();
    }
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    protected void onPause() {
    	shakeDetector.stop();
    	super.onPause();
    }
    
    /**
     * Rolls the dices.
     * @param view The view.
     */
    public void rollDices(View view) {
    	getGameContext().rollCount++;
    	
    	update();
    	
    	undoMarking();
    	
		for (Dice dice : getGameContext().dices) {
	    	dice.roll();		
		}
		
		if (Globals.isSoundEnabled()) {
			try {
				if (rollingDicesMediaPlayer == null) {
					rollingDicesMediaPlayer = MediaPlayer.create(this, R.raw.rollingdices);
				} else {
					rollingDicesMediaPlayer.stop();
					rollingDicesMediaPlayer.prepare();
				}

				rollingDicesMediaPlayer.start();
			} catch(Exception ex) { }
		}
		
		for (ElementButton elementButton : elementButtons) {
			elementButton.update(getGameContext().dices);			
		}
    }
    
    /**
     * Shows the next player.
     * @param view The view.
     */
    public void nextPlayer(View view) {
		getGameContext().markedElement.setDices(getGameContext().dices);
		getGameContext().markedElement = null;
		
    	if (++getGameContext().currentPlayerIndex == getGameContext().getNumberOfPlayers()) {
    		getGameContext().currentPlayerIndex = 0;
    		getGameContext().currentStep++;
    	}
    	
    	if (getGameContext().currentStep > 13) {
    		showScores();
    	} else {    	
    		getGameContext().rollCount = 1;
    		
        	for (Dice dice : getGameContext().dices) {
    			dice.setLocked(false);	
    		}
        	
            updateElements();
            update();
    	}
    }
    
    /**
     * Executed when an element is marked.
     * @param markedElementButton
     */
    private void markElement(ElementButton markedElementButton) {
    	undoMarking();
		
    	Element element = markedElementButton.getElement();
    	element.setDices(getGameContext().dices);
		getGameContext().markedElement = element;
		
		for (ElementButton elementButton : elementButtons) {
			if (markedElementButton != elementButton) {
				elementButton.update(getGameContext().dices);
			}
		}
		
		update();
    }

    /**
     * Remove the marking from the marked element.
     */
    private void undoMarking() {
    	if (getGameContext().markedElement != null) {
    		getGameContext().markedElement.unsetDices();
		}
    }
    
    /**
     * Shows the results at the end of the game.
     */
    private void showScores() {
    	List<Result> results = new ArrayList<Result>();
		
		for (Player player : getGameContext().players) {
			results.add(new Result(player.getName(), player.getResult()));
		}
		
		DataRepository repository = new DataRepository(this);
		repository.addHighscores(results);
		
		Globals.setResults(results);
		Globals.setGameContext(null);
		
		startActivity(new Intent(this, Results.class));
		finish();
    }

    /**
     * Update the UI, except the elements.
     */
    private void update() {
    	((TextView)findViewById(R.id.playerName)).setText(getGameContext().getCurrentPlayer().getName());
    	
    	int dicesVisibility = getGameContext().rollCount == 1 ? View.INVISIBLE : View.VISIBLE;
    	findViewById(R.id.dicesLinearLayout).setVisibility(dicesVisibility);
    	
    	String current = String.format("%s: %s/13", getResources().getText(R.string.currentStep), Integer.toString(getGameContext().currentStep));
    	((TextView)findViewById(R.id.currentStepTextView)).setText(current);
    	
		int sum = getGameContext().getCurrentPlayer().getResult();
		String result = String.format("%s: %s", getResources().getText(R.string.result), Integer.toString(sum));
		((TextView)findViewById(R.id.resultTextView)).setText(result);

		if (getGameContext().rollCount <= 3) {
			findViewById(R.id.rollButton).setEnabled(true);
			((Button)findViewById(R.id.rollButton)).setText(String.format("%s %s/3", getResources().getText(R.string.roll), getGameContext().rollCount));
			shakeDetector.start(this, shakeEventListener);
		} else {
			findViewById(R.id.rollButton).setEnabled(false);
			((Button)findViewById(R.id.rollButton)).setText(R.string.roll);
			shakeDetector.stop();
		}
		
		findViewById(R.id.nextPlayerButton).setEnabled(getGameContext().markedElement != null);
    }
    
    /**
     * Update the elements.
     */
    private void updateElements() {
    	elementButtons.clear();
    	TableLayout elementsTableLayout = (TableLayout)findViewById(R.id.elementsTableLayout);
    	elementsTableLayout.removeAllViews();
    	elementsTableLayout.setColumnStretchable(0, true);
    	
    	for (Element element : getGameContext().getCurrentPlayer().getLowerSection().getElements()) {
			elementsTableLayout.addView(createElementView(element));
		}
    	
    	for (Element element : getGameContext().getCurrentPlayer().getUpperSection().getElements()) {
			elementsTableLayout.addView(createElementView(element));
		}
    	
    	((ScrollView)findViewById(R.id.elementsScrollView)).scrollTo(0, 0);
    }
    
    /**
     * Creates a view for the given {@link Element}.
     * @param element The {@link Element}.
     * @return The created view.
     */
    private View createElementView(Element element) {    	
    	TextView textView = new TextView(getApplicationContext());
    	textView.setText(element.getName());
    	
    	ElementButton elementButton = new ElementButton(getApplicationContext(), element);    	
    	elementButton.setOnClickListener(markListener);
    	
    	if (getGameContext().markedElement == element) {
    		elementButton.mark();
    	}
    	
    	if (getGameContext().rollCount > 1) {
    		elementButton.update(getGameContext().dices);
    	}
    	
    	elementButtons.add(elementButton);
    	
    	TableRow row = new TableRow(getApplicationContext());
    	row.setPadding(2, 2, 2, 2);
    	row.addView(textView);
    	row.addView(elementButton);  	
    	
		return row;
	}
}
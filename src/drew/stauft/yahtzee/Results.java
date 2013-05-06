package drew.stauft.yahtzee;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import drew.stauft.yahtzee.data.DataRepository;
import drew.stauft.yahtzee.data.Result;

import drew.stauft.android.R;

/**
 * Shows the results of a game and the overall highscores.
 * @author Drew Stauft
 *
 */
public class Results extends Activity {

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        if (getIntent().getBooleanExtra(Globals.SHOW_HIGHSCORES, false)) {
        	((TextView)findViewById(R.id.resultsTitle)).setText(R.string.high_scores);
        	
        	DataRepository repository = new DataRepository(this);
        	List<Result> highScores = repository.getHighscores();
        	showResults(highScores);
        } else {
        	showResults(Globals.getResults());
        }   
    }
    
    /**
     * Closes the view.
     * @param view The view.
     */
    public void back(View view) {
		finish();
    }
    
    /**
     * Fills the results table.
     * @param results The results to show.
     */
	private void showResults(List<Result> results) {		
		Collections.sort(results);
		
		TableLayout resultsTableLayout = (TableLayout)findViewById(R.id.resultsTableLayout);
		resultsTableLayout.setColumnStretchable(1, true);
		
		for (int i = 1; i <= results.size(); i++) {
			TableRow row = new TableRow(getApplicationContext());
	    	row.setPadding(2, 2, 2, 2);
	    	
	    	TextView textView = new TextView(getApplicationContext());
	    	textView.setText(String.format("%s. ", i));
	    	textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
	    	textView.setGravity(Gravity.RIGHT);
	    	row.addView(textView);
	    	
			textView = new TextView(getApplicationContext());
	    	textView.setText(results.get(i - 1).getName());
	    	textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
	    	row.addView(textView);
	    	
	    	textView = new TextView(getApplicationContext());
	    	textView.setText(Integer.toString(results.get(i - 1).getScore()) + " ");
	    	textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
	    	textView.setGravity(Gravity.RIGHT);
	    	row.addView(textView);

	    	resultsTableLayout.addView(row);
		}
	}	
}
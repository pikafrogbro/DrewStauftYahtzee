package drew.stauft.yahtzee.ui;

import java.util.Observable;
import java.util.Observer;

import drew.stauft.android.R;
import drew.stauft.yahtzee.game.Dice;
import android.content.Context;
import android.widget.ImageButton;

/**
 * An ImageButton representing a {@link Dice}.
 * @author Drew Stauft
 *
 */
public class DiceImageButton extends ImageButton implements Observer  {

	/**
	 * The {@link Dice}.
	 */
	private Dice dice;	
	
	/**
	 * Constructor.
	 * @param context The context.
	 * @param dice {@link Dice}.
	 */
	public DiceImageButton(Context context, Dice dice) {
		super(context);
		
		this.dice = dice;
		
		updateImage();
		
		dice.addObserver(this);
	}
	
	/**
	 * Updates the image depending on the state of the {@link Dice}.
	 */
	private void updateImage() {
		if (dice.isLocked())
		{
			switch (dice.getValue())
			{
				case 1:
					setBackgroundResource(R.drawable.dice1fixed);
					break;
				case 2:
					setBackgroundResource(R.drawable.dice2fixed);
					break;
				case 3:
					setBackgroundResource(R.drawable.dice3fixed);
					break;
				case 4:
					setBackgroundResource(R.drawable.dice4fixed);
					break;
				case 5:
					setBackgroundResource(R.drawable.dice5fixed);
					break;
				case 6:
					setBackgroundResource(R.drawable.dice6fixed);
					break;
			}
		} else {
			switch (dice.getValue())
			{
				case 1:
					setBackgroundResource(R.drawable.dice1);
					break;
				case 2:
					setBackgroundResource(R.drawable.dice2);
					break;
				case 3:
					setBackgroundResource(R.drawable.dice3);
					break;
				case 4:
					setBackgroundResource(R.drawable.dice4);
					break;
				case 5:
					setBackgroundResource(R.drawable.dice5);
					break;
				case 6:
					setBackgroundResource(R.drawable.dice6);
					break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performClick() {
		dice.setLocked(!dice.isLocked());
		
		return super.performClick();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable observable, Object data) {
		updateImage();
	}
}
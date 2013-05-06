package drew.stauft.yahtzee.ui;

import drew.stauft.yahtzee.game.Dice;
import drew.stauft.yahtzee.game.elements.Element;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * A Button representing an {@link Element}.
 * @author Drew Stauft
 *
 */
public class ElementButton extends Button {

	/**
	 * The {@link Element}.
	 */
	private Element element;	
	
	/**
	 * Gets the {@link Element}.
	 * @return The {@link Element}.
	 */
	public Element getElement() {
		return element;
	}
	
	/**
	 * Constructor.
	 * @param context The context.
	 * @param element The {@link Element}.
	 */
	public ElementButton(Context context, Element element) {
		super(context);
		
		this.element = element;
		
		if (element.hasResult()) {
			setText( element.getResult().toString());
			setBackgroundColor(Color.BLACK);
			setTextColor(Color.WHITE);
		} else {
			setText("");
			setBackgroundColor(Color.GRAY);
		}
		
		setEnabled(false);
		
		setWidth(100);
	}
	
	@Override
	public boolean performClick() {
		mark();
		
		return super.performClick();
	}
	
	/**
	 * Marks the {@link Element}.
	 */
	public void mark() {
		setBackgroundColor(Color.BLUE);
		setTextColor(Color.WHITE);
	}
	
	/**
	 * Updates the view depending on the current dices.
	 * @param dices The dices.
	 */
	public void update(Dice[] dices) {
		if (element.hasResult()) {
			return;
		}

		setEnabled(true);
		setTextColor(Color.BLACK);			
		
		setText(Integer.toString(element.calculate(dices)));
		
		if (element.doDicesFit(dices)) {
			setBackgroundColor(Color.GREEN);
		} else {
			setBackgroundColor(Color.YELLOW);
		}
	}
}
package drew.stauft.yahtzee.game;


import java.util.ArrayList;
import java.util.List;

import drew.stauft.yahtzee.game.elements.Element;

/**
 * Represents a section.
 * The game consists of two sections.
 * Each section consists of several elements.
 * @author Drew Stauft
 *
 */
public abstract class Section {
	
	/**
	 * The elements.
	 */
	private List<Element> elements = new ArrayList<Element>();
	
	/**
	 * Adds the given element.
	 * @param element The element.
	 */
	protected void add(Element element) {
		elements.add(element);
	}
	
	/**
	 * Gets the elements.
	 * @return The elements.
	 */
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 * Gets the sum of the section.
	 * @return The sum.
	 */
	public int getResult(){
		int sum = 0;
		for (Element element : getElements()) {
			if (element.hasResult()) {
				sum += element.getResult();
			}
		}
		
		sum += getBonus();
		
		return sum;
	}
	
	/**
	 * Gets the bonus of the section.
	 * @return The bonus.
	 */
	public abstract int getBonus();
	
	/**
	 * Determines whether the section has a bonus.
	 * @return The bonus state.
	 */
	public abstract boolean hasBonus();
}

package drew.stauft.android;

import drew.stauft.android.ShakeDetector;

/**
 * Used for receiving notifications from the {@link ShakeDetector} when shakes are detected. 
 * @author Drew Stauft
 *
 */
public interface ShakeEventListener {
	
	/**
	 * Executed when a shake is detected.
	 */
	public void onShake();
}
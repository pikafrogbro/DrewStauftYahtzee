package drew.stauft.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;

/**
 * Detects when the device is shook.
 *
 */
public class ShakeDetector implements SensorEventListener {

	/**
	 * The {@link ShakeEventListener} which is notified when a shake is detected.
	 */
	private ShakeEventListener shakeEventListener;
	
	/**
	 * The SensorManager.
	 */
    private SensorManager sensorMgr;
    
    /**
     * The time of the last received sensor event.
     */
    private long lastUpdate = -1;
    
    /**
     * The time of the last published event.
     */
    private long lastEvent = -1;
    
    /**
     * The current position.
     */
    private float x, y, z;
    
    /**
     * The last position.
     */
    private float last_x, last_y, last_z;
    
    /**
     * The minimum speed.
     */
    private static final int SHAKE_THRESHOLD = 400;

    /**
     * Starts the shake detection.
     * @param context The context.
     * @param shakeEventListener The {@link ShakeEventListener}.
     */
    public void start(Context context, ShakeEventListener shakeEventListener) {
    	this.shakeEventListener = shakeEventListener;
    	sensorMgr = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);

		List<Sensor> sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
		
		if (sensors != null && sensors.size() > 0) {
			boolean sensorActive = sensorMgr.registerListener(this,
					sensors.get(0),
					SensorManager.SENSOR_DELAY_GAME);
			
			if (!sensorActive) {
				stop();
			}
		}
    }
    
    /**
     * Stops the shake detection.
     */
    public void stop() {
    	if (sensorMgr != null) {
    	    sensorMgr.unregisterListener(this);
    	    sensorMgr = null;
        }
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
	    long curTime = SystemClock.uptimeMillis();
	    // Ignore events that have occurred too early to get better speed approximation.
	    if ((curTime - lastUpdate) > 200) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;
						
			x = sensorEvent.values[SensorManager.DATA_X];
			y = sensorEvent.values[SensorManager.DATA_Y];
			z = sensorEvent.values[SensorManager.DATA_Z];
	
			float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
			
			if (speed > SHAKE_THRESHOLD) {
				// Only notify if some time has passed since the last event
				if (curTime - lastEvent > 1800){					
					lastEvent = curTime;
					shakeEventListener.onShake();
				}
			}
			
			last_x = x;
			last_y = y;
			last_z = z;
    	}
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
}